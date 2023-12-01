package com.cibersalud.app.service;

import java.util.List;

import com.cibersalud.app.entity.Cita;
import com.cibersalud.app.entity.Historial;
import com.cibersalud.app.entity.Paciente;

public interface ICitaService extends ICRUD<Cita,Integer>{

	
	List<Cita> findByPaciente(Paciente paciente);
	
	public Boolean existe(int id);
}
