package com.cibersalud.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibersalud.app.entity.Historial;
import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.repo.IGenericRepo;
import com.cibersalud.app.repo.IHistorialRepo;
import com.cibersalud.app.service.IHistorialService;
import com.cibersalud.app.service.IUsuarioService;

@Service
public class HistorialServiceImpl extends CRUDImpl<Historial,Integer> implements IHistorialService{

	@Autowired
	private IHistorialRepo repo;
	
	@Override
	protected IGenericRepo<Historial, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public Historial findByPaciente(Paciente paciente) {
		// TODO Auto-generated method stub
		return repo.findByPaciente(paciente);
	}

	@Override
	public Boolean existe(int id) {
		// TODO Auto-generated method stub
		return repo.existsById(id);
	}

}
