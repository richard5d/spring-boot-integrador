package com.cibersalud.app.repo;

import java.util.List;

import com.cibersalud.app.entity.Cita;
import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.Usuario;

public interface ICitaRepo extends IGenericRepo<Cita,Integer>{
	
	List<Cita> findByPaciente(Paciente paciente);

}
