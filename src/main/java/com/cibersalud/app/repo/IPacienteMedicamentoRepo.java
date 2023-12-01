package com.cibersalud.app.repo;

import java.util.List;

import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.PacienteMedicamento;

public interface IPacienteMedicamentoRepo extends IGenericRepo<PacienteMedicamento,Integer>{
	
	List<PacienteMedicamento> findByPaciente(Paciente paciente);

}
