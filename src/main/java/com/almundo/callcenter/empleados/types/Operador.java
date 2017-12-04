package com.almundo.callcenter.empleados.types;

import com.almundo.callcenter.empleados.Empleado;
import com.almundo.callcenter.llamadas.Llamada;
import com.almundo.callcenter.utils.Utilities;

import static java.lang.Thread.sleep;

/**
 * Created by Marcos on 12/1/2017.
 */
public class Operador extends Empleado {
	public Operador(){}
	public Operador(String name){super();this.setName(name);}


	@Override
	public void atenderLlamada(Llamada llamada) {
		//Custom operador actions
		this.atenderLlamadaDefault(llamada);
	}
}
