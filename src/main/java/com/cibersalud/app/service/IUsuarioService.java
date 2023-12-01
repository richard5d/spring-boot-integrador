package com.cibersalud.app.service;

import com.cibersalud.app.entity.Usuario;

public interface IUsuarioService extends ICRUD<Usuario,Integer>{
	
	
	public Usuario iniciarSesion(String email);
	
	//public Usuario findById(Integer id);

}
