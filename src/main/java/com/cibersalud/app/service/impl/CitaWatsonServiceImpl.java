package com.cibersalud.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibersalud.app.entity.CitaWatson;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.repo.ICitaWatsonRepo;
import com.cibersalud.app.repo.IGenericRepo;
import com.cibersalud.app.repo.IUsuarioRepo;
import com.cibersalud.app.service.ICitaWatsonService;
import com.cibersalud.app.service.IUsuarioService;

@Service
public class CitaWatsonServiceImpl extends CRUDImpl<CitaWatson,Integer> implements ICitaWatsonService{

	@Autowired
	ICitaWatsonRepo repo;
	
	@Override
	protected IGenericRepo<CitaWatson, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public Boolean existe(int id) {
		// TODO Auto-generated method stub
		return repo.existsById(id);
	}

}
