package com.almundo.callcenter.dispatcher;

import com.almundo.callcenter.empleados.Empleado;
import com.almundo.callcenter.empleados.EmpleadosPool;
import com.almundo.callcenter.llamadas.Llamada;
import com.almundo.callcenter.utils.Configuration;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Created by Marcos on 12/1/2017.
 */
public class CallsDispatcher {
	private static final Logger LOGGER = Logger.getLogger( CallsDispatcher.class.getName() );
	private ExecutorService executor;
	private BlockingQueue pendingCalls;
	private EmpleadosPool empleados;
	public EmpleadosPool getEmpleados() {
		return empleados;
	}

	public void setEmpleados(EmpleadosPool empleados) {
		this.empleados = empleados;
	}



	public List<Llamada> getLlamadasRecibidas() {
		return llamadasRecibidas;
	}

	public void setLlamadasRecibidas(List<Llamada> llamadasRecibidas) {
		this.llamadasRecibidas = llamadasRecibidas;
	}

	private List<Llamada> llamadasRecibidas;

	/**
	 * Constructor de CallsDispatcher que atendera las llamadas.
	 * @param cfg configuracion
	 */
	public CallsDispatcher(Configuration cfg, EmpleadosPool empleados){
		this.setEmpleados(empleados);
		this.executor = Executors.newFixedThreadPool(cfg.getThreads(),new ThreadFactory() {
			public Thread newThread(Runnable r) {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(false);
				return t;
			}
		});

		this.pendingCalls = new LinkedBlockingDeque();
	}


	/**
	 * Inicia el procesamiento de una lista de llamadas.
	 * @param llamadas lista de llamadas a procesar
	 */
	public void dispatchCalls(List<Llamada> llamadas){
		this.setLlamadasRecibidas(llamadas);
		llamadas.forEach(llamada -> this.handleSingleCall(llamada));
	}

	/**
	 * Recibe y procesa la llamada si hay empleado disponible, si no, la encola en la lista de pendientes.
	 * @param llamada Llamada a procesar
	 */
	private void handleSingleCall(Llamada llamada){
		Empleado emplDisponible = empleados.getAvailableEmployee();
		if (emplDisponible == null){
			llamada.newAttemp();
			LOGGER.info("Llamada " + llamada.getName() +" imposible de atender, no hay empleados disponibles");
			this.pendingCalls.add(llamada);
			return;
		}
		this.dispatchCall(emplDisponible, llamada);
	}

	/**
	 * Manda al empleado asignado a atender la llamada
	 * @param empl Empleado a atender la llamada
	 * @param llamada Llamada a procesar
	 */
	public void dispatchCall(Empleado empl, Llamada llamada){
		Runnable callThread = () -> {
			empl.atenderLlamada(llamada);
			this.notifyFreeEmployee();
		};
		this.executor.submit(callThread);
	}

	/**
	 * Desencola una llamada de la lista de llamadas pendientes para mandar a procesar;
	 * Si no hay llamadas pendientes, manda a terminar el proceso
	 */
	public void notifyFreeEmployee(){
		if (this.pendingCalls.isEmpty()){
			try {
				LOGGER.info("Shuting down executor...");
				this.executor.shutdown();
				executor.awaitTermination(5, TimeUnit.SECONDS);
			} catch (Exception e) {
				LOGGER.info("tasks interrupted");
			} finally {
				if (!executor.isTerminated()) {
					LOGGER.info("cancel non-finished tasks");
				}
				executor.shutdownNow();
				LOGGER.info("shutdown finished");
			}

		}
		Llamada call = (Llamada)this.pendingCalls.remove();
		this.handleSingleCall(call);
	}



	public BlockingQueue getPendingCalls() {
		return pendingCalls;
	}

	public void setPendingCalls(BlockingQueue pendingCalls) {
		this.pendingCalls = pendingCalls;
	}
}
