package com.cibersalud.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibersalud.app.entity.Cita;
import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.repo.ICitaRepo;
import com.cibersalud.app.repo.IGenericRepo;
import com.cibersalud.app.service.ICitaService;
@Service
public class CitaServiceImpl extends CRUDImpl<Cita,Integer> implements ICitaService{

	@Autowired
	private ICitaRepo repo;
	
	@Override
	protected IGenericRepo<Cita, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public List<Cita> findByPaciente(Paciente paciente) {
		// TODO Auto-generated method stub
		return repo.findByPaciente(paciente);
	}

	@Override
	public Boolean existe(int id) {
		// TODO Auto-generated method stub
		return repo.existsById(id);
	}
	

}
