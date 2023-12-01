package com.cibersalud.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.repo.IGenericRepo;
import com.cibersalud.app.repo.IPacienteRepo;
import com.cibersalud.app.service.IPacienteService;
import com.cibersalud.app.service.IUsuarioService;

@Service
public class PacienteServiceImpl extends CRUDImpl<Paciente,Integer> implements IPacienteService{

	@Autowired
	private IPacienteRepo repo;

	@Override
	protected IGenericRepo<Paciente, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public Paciente findByUser(Usuario user) {
		// TODO Auto-generated method stub
		return repo.findByUser(user);
	}
	
	

}
