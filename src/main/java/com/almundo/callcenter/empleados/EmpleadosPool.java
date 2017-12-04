package com.almundo.callcenter.empleados;

import com.almundo.callcenter.empleados.types.Director;
import com.almundo.callcenter.empleados.types.Operador;
import com.almundo.callcenter.empleados.types.Supervisor;
import com.almundo.callcenter.utils.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Created by Marcos on 12/1/2017.
 */
public class EmpleadosPool {
	private List<Operador> operadores;
	private List<Supervisor> supervisores;
	private List<Director> directores;
	private static final Logger LOGGER = Logger.getLogger( EmpleadosPool.class.getName() );

	/**
	 * Constructor del pool de empleados, que contiene la lista de operadores, supervisores y directores
	 * @param cfg Configuracion
	 */
	public EmpleadosPool(Configuration cfg){
		if (cfg == null)
			cfg = new Configuration();
		this.operadores = new ArrayList<>();
		this.supervisores = new ArrayList<>();
		this.directores = new ArrayList<>();
		IntStream.range(0, cfg.getOperadores())
						.forEach(i -> this.operadores.add(new Operador("Operador " + String.valueOf(i))));
		IntStream.range(0, cfg.getSupervisores())
						.forEach(i -> this.supervisores.add(new Supervisor("Supervisor " + String.valueOf(i))));
		IntStream.range(0, cfg.getDirectores())
						.forEach(i -> this.directores.add(new Director("Director " + String.valueOf(i))));
	}

	public List<Operador> getOperadores() {
		return operadores;
	}

	public List<Supervisor> getSupervisores() {
		return supervisores;
	}

	public List<Director> getDirectores() {
		return directores;
	}

	/**
	 * Obtiene un operador disponible, si no existe devuelve null
	 */
	private Operador getAvailOperador(){
		Optional<Operador> freeOperator =
						this.operadores.stream().filter(empl ->  empl.isFree()).findAny();
		if (freeOperator.isPresent()){
			return freeOperator.get();
		}
		return null;
	}

	/**
	 * Obtiene un supervisor disponible, si no existe devuelve null
	 */
	private Supervisor getAvailSupervisor(){
		Optional<Supervisor> freeSupervisor =
						this.supervisores.stream().filter(empl -> empl.isFree()).findAny();
		if (freeSupervisor.isPresent()){
			return freeSupervisor.get();
		}
		return null;
	}

	/**
	 * Obtiene un director disponible, si no existe devuelve null
	 */
	private Director getAvailDirector(){
		Optional<Director> freeDirector =
						this.directores.stream().filter(empl -> empl.isFree()).findAny();
		if (freeDirector.isPresent()){
			return freeDirector.get();
		}
		return null;
	}

	/**
	 * Obtiene un empleado disponible, busca primero operadores, luego supervisores y por ultimo directores. Si existe, lo pone como
	 * ocupado y lo devuelve. Devuelve null si todos estan ocupados
	 */
	public synchronized Empleado getAvailableEmployee(){

		Empleado availableEmpl = this.getAvailOperador();
		if (availableEmpl == null) {
			availableEmpl = this.getAvailSupervisor();
		}
		if (availableEmpl == null) {
			availableEmpl = this.getAvailDirector();
		}
		if (availableEmpl != null) {
			availableEmpl.setFree(false);
		}
		return availableEmpl;
	}


}
