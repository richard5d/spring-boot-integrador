package com.cibersalud.app.service;

import com.cibersalud.app.entity.CitaWatson;
import com.cibersalud.app.entity.Usuario;

public interface ICitaWatsonService extends ICRUD<CitaWatson,Integer>{
	
	public Boolean existe(int id);

}
