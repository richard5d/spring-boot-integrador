package com.cibersalud.app.service;

import com.cibersalud.app.entity.Historial;
import com.cibersalud.app.entity.Paciente;

public interface IHistorialService extends ICRUD<Historial,Integer>{
	
	public Historial findByPaciente(Paciente paciente);
	public Boolean existe(int id);

}
