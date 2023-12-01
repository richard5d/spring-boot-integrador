package com.cibersalud.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Usuario;

public interface IMedicoRepo extends IGenericRepo<Medico,Integer>{

	//@Query("select m from Medico m where m.user.id=?1")
	public Medico findByUser(Usuario user);
	
	@Query("select m from Medico m where m.eliminado='no'")
    public List<Medico> listaNoEliminado();
	
	
}
