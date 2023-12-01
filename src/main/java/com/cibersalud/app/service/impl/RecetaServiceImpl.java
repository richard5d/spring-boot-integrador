package com.cibersalud.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.Receta;
import com.cibersalud.app.repo.IGenericRepo;
import com.cibersalud.app.repo.IRecetaRepo;
import com.cibersalud.app.service.IPacienteService;
import com.cibersalud.app.service.IRecetaService;

@Service
public class RecetaServiceImpl extends CRUDImpl<Receta,Integer> implements IRecetaService{

	@Autowired
	private IRecetaRepo repo;
	
	@Override
	protected IGenericRepo<Receta, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public List<Receta> recetasDelMedico(Integer id) {
		// TODO Auto-generated method stub
		return repo.recetasDelMedico(id);
	}

	@Override
	public List<Receta> recetasDelPaciente(Integer id) {
		// TODO Auto-generated method stub
		return repo.recetasDelPaciente(id);
	}

	@Override
	public Boolean existe(int id) {
		// TODO Auto-generated method stub
		return repo.existsById(id);
	}
	
	

}
