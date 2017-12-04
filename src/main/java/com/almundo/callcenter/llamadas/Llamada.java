package com.almundo.callcenter.llamadas;

/**
 * Created by Marcos on 12/1/2017.
 */
public class Llamada {
	public Llamada(){this.failedAttemps = 0;}
	public Llamada(String name){this.failedAttemps = 0; this.name = name;}
	private String name;
	private int failedAttemps;
	private String empleadoAtendio;
	private String duracion;

	public void newAttemp(){
		this.failedAttemps++;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getEmpleadoAtendio() {
		return empleadoAtendio;
	}

	public void setEmpleadoAtendio(String empleadoAtendio) {
		this.empleadoAtendio = empleadoAtendio;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public int getFailedAttemps() {
		return failedAttemps;
	}

	public void setFailedAttemps(int failedAttemps) {
		this.failedAttemps = failedAttemps;
	}
}
