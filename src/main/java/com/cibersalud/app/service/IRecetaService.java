package com.cibersalud.app.service;

import java.util.List;

import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.Receta;

public interface IRecetaService extends ICRUD<Receta,Integer>{
	List<Receta> recetasDelMedico(Integer id);
	List<Receta> recetasDelPaciente(Integer id);
	public Boolean existe(int id);
}
