package com.cibersalud.app.service;

import java.util.List;

import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Usuario;

public interface IMedicoService extends ICRUD<Medico,Integer>{
	
	public Medico findByUser(Usuario user);
	
	public List<Medico> listaNoEliminado();
	
	public Boolean existe(int id);

}
