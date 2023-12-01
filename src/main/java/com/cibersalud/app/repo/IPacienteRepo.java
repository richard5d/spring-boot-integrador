package com.cibersalud.app.repo;

import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.Usuario;

public interface IPacienteRepo extends IGenericRepo<Paciente,Integer>{
	public Paciente findByUser(Usuario user);
}
