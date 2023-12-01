package com.cibersalud.app.repo;

import org.springframework.data.jpa.repository.Query;

import com.cibersalud.app.entity.Usuario;

public interface IUsuarioRepo extends IGenericRepo<Usuario,Integer>{
	
	//@Query("select u from Usuario u where u.email=?1")
	public Usuario findByEmail(String email);

}
