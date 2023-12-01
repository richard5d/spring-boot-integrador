package com.cibersalud.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.MedicamentoWatson;
import com.cibersalud.app.repo.IGenericRepo;
import com.cibersalud.app.repo.IMedicamentoWatsonRepo;
import com.cibersalud.app.service.IMedicamentoService;
import com.cibersalud.app.service.IMedicamentoWatsonService;
@Service
public class MedicamentoWatsonServiceImpl extends CRUDImpl<MedicamentoWatson,Integer> implements IMedicamentoWatsonService{

	@Autowired
	private IMedicamentoWatsonRepo repo;
	
	@Override
	protected IGenericRepo<MedicamentoWatson, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public Boolean existe(int id) {
		// TODO Auto-generated method stub
		return repo.existsById(id);
	}

}
