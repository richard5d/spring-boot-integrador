package com.cibersalud.app.service;


import com.cibersalud.app.entity.MedicamentoWatson;

public interface IMedicamentoWatsonService extends ICRUD<MedicamentoWatson,Integer>{
	
	public Boolean existe(int id);

}
