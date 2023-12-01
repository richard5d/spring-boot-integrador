package com.cibersalud.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Usuario;

public interface IMedicamentoRepo extends IGenericRepo<Medicamento,Integer>{
	
	@Query("select m from Medicamento m where m.eliminado='no'")
    public List<Medicamento> listaNoEliminado();

}
