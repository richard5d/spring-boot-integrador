package com.cibersalud.app.service;

import java.util.List;

import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.PacienteMedicamento;

public interface IPacienteMedicamentoService extends ICRUD<PacienteMedicamento,Integer>{
	List<PacienteMedicamento> listaDeMedicamentoPorPaciente(Paciente paciente);
}
