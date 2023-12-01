package com.cibersalud.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.repo.IGenericRepo;
import com.cibersalud.app.repo.IMedicoRepo;
import com.cibersalud.app.service.IMedicoService;
import com.cibersalud.app.service.IUsuarioService;

@Service
public class MedicoServiceImpl extends CRUDImpl<Medico,Integer> implements IMedicoService{

	@Autowired
	private IMedicoRepo repo;
	
	@Override
	protected IGenericRepo<Medico, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public Medico findByUser(Usuario user) {
		// TODO Auto-generated method stub
		return repo.findByUser(user);
	}

	@Override
	public List<Medico> listaNoEliminado() {
		// TODO Auto-generated method stub
		return repo.listaNoEliminado();
	}

	@Override
	public Boolean existe(int id) {
		// TODO Auto-generated method stub
		return repo.existsById(id);
	}

	

	

	

}
