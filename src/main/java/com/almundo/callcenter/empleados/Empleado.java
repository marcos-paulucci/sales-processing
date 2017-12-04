package com.almundo.callcenter.empleados;

import com.almundo.callcenter.llamadas.Llamada;
import com.almundo.callcenter.utils.Utilities;

import java.util.logging.Logger;

import static java.lang.Thread.sleep;

/**
 * Created by Marcos on 12/1/2017.
 */
public abstract class Empleado {
	private boolean free;
	private String name;
	public abstract void atenderLlamada(Llamada llamada);
	protected static final Logger LOGGER = Logger.getLogger( Empleado.class.getName() );

	/**
	 * Metodo default para atender llamada. Obtiene un tiempo aleatorio para simular la llamada, y tras su finalizacion
	 * indica que la llamada fue atendida, y pone al empleado devuelta como disponible
	 * @param llamada Llamada a atender por el empleado
	 */
	protected void atenderLlamadaDefault(Llamada llamada) {
		int sleepTime = Utilities.getRandomCallTime();
		llamada.setDuracion(String.valueOf(sleepTime));
		try {
			sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOGGER.info("Error handling call");
		}
		llamada.setEmpleadoAtendio(this.getName());
		this.setFree(true);
		LOGGER.info("Llamada " + llamada.getName() + " atendida por " + this.getName());
	}

	public Empleado(){
		this.free = true;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
