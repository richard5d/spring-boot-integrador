package com.cibersalud.app.service;

import java.util.List;

import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.Usuario;

public interface IMedicamentoService extends ICRUD<Medicamento,Integer>{
	
	
	public List<Medicamento> listaNoEliminado();
	
	public Boolean existe(int id);

}
