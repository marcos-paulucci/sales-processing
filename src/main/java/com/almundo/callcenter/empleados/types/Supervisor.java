package com.almundo.callcenter.empleados.types;

import com.almundo.callcenter.empleados.Empleado;
import com.almundo.callcenter.llamadas.Llamada;

/**
 * Created by Marcos on 12/1/2017.
 */
public class Supervisor extends Empleado {
	public Supervisor(){}
	public Supervisor(String name){super();this.setName(name);}



	@Override
	public void atenderLlamada(Llamada llamada) {
		//Custom Supervisor actions
		this.atenderLlamadaDefault(llamada);
	}
}
