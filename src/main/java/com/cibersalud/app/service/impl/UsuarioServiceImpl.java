package com.cibersalud.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.repo.IGenericRepo;
import com.cibersalud.app.repo.IMedicoRepo;
import com.cibersalud.app.repo.IUsuarioRepo;
import com.cibersalud.app.service.IUsuarioService;

@Service
public class UsuarioServiceImpl extends CRUDImpl<Usuario,Integer> implements IUsuarioService{

	@Autowired
	IUsuarioRepo repo;
	
	@Autowired
	IMedicoRepo medirepo;

	@Override
	public Usuario iniciarSesion(String email) {
		// TODO Auto-generated method stub
		Usuario usu=repo.findByEmail(email);
		Medico m=medirepo.findByUser(usu);
		if(m !=null) {
			if(m.getEliminado().equals("si")) {
				return null;
			}
		}
		return repo.findByEmail(email);
	}



	@Override
	protected IGenericRepo<Usuario, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	
	
	

}
