package com.almundo.callcenter;

import com.almundo.callcenter.dispatcher.CallsDispatcher;
import com.almundo.callcenter.empleados.EmpleadosPool;
import com.almundo.callcenter.llamadas.Llamada;
import com.almundo.callcenter.utils.Configuration;

import java.util.List;
import java.util.logging.Logger;


/**
 * Created by Marcos on 12/1/2017.
 */
public class CallCenter {
	private static final Logger LOGGER = Logger.getLogger( CallCenter.class.getName() );
	private CallsDispatcher callsDispatcher;
	private EmpleadosPool empleados;
	public CallsDispatcher getCallsDispatcher() {
		return callsDispatcher;
	}

	public void setCallsDispatcher(CallsDispatcher callsDispatcher) {
		this.callsDispatcher = callsDispatcher;
	}


	public List<Llamada> getLlamadasRecibidas() {
		return llamadasRecibidas;
	}

	public void setLlamadasRecibidas(List<Llamada> llamadasRecibidas) {
		this.llamadasRecibidas = llamadasRecibidas;
	}

	private List<Llamada> llamadasRecibidas;

	/**
	 * Constructor de Callcenter con config del archivo de configuracion.
	 */
	public CallCenter(){
		Configuration cfg = new Configuration();
		this.empleados = new EmpleadosPool( cfg);
		this.callsDispatcher = new CallsDispatcher(cfg, this.empleados);
	}

	/**
	 * Constructor de Callcenter usando parametros custom.
	 * @param threads numero de threads a manejar para atender llamadas
	 * @param op numero de operadores para atender llamadas
	 * @param sup numero de supervisores a manejar para atender llamadas
	 * @param dir numero de directores a manejar para atender llamadas
	 */
	public CallCenter(int threads, int op, int sup, int dir){
		Configuration cfg = new Configuration(threads, op, sup, dir);
		this.empleados = new EmpleadosPool( cfg);
		this.callsDispatcher = new CallsDispatcher(cfg, this.empleados);

	}

	/**
	 * Recibe y envia a procesar una lista de llamadas.
	 * @param llamadas lista de llamadas a atender
	 */
	public void handleCalls(List<Llamada> llamadas){
		this.setLlamadasRecibidas(llamadas);
		this.callsDispatcher.dispatchCalls(llamadas);
	}
}
