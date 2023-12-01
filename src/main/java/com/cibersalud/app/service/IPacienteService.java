package com.cibersalud.app.service;

import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.Usuario;


public interface IPacienteService extends ICRUD<Paciente,Integer>{
	public Paciente findByUser(Usuario user);
}
