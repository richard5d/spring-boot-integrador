package com.cibersalud.app.repo;

import com.cibersalud.app.entity.Historial;
import com.cibersalud.app.entity.Paciente;

public interface IHistorialRepo extends IGenericRepo<Historial,Integer>{
	
	public Historial findByPaciente(Paciente paciente);

}
