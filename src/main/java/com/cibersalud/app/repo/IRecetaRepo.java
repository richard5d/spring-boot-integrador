package com.cibersalud.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cibersalud.app.entity.Receta;
import com.cibersalud.app.entity.Usuario;

public interface IRecetaRepo extends IGenericRepo<Receta,Integer>{
	
	@Query("select r from Receta r where r.historialmedico.medico.id=?1")
	List<Receta> recetasDelMedico(Integer id);
	
	@Query("select r from Receta r where r.historialmedico.paciente.id=?1")
	List<Receta> recetasDelPaciente(Integer id);

}
