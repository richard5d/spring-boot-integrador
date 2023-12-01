package com.cibersalud.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.PacienteMedicamento;
import com.cibersalud.app.repo.IGenericRepo;
import com.cibersalud.app.repo.IPacienteMedicamentoRepo;
import com.cibersalud.app.service.IPacienteMedicamentoService;


@Service
public class PacienteMedicamentoServiceImpl extends CRUDImpl<PacienteMedicamento,Integer> implements IPacienteMedicamentoService{

	@Autowired
	private IPacienteMedicamentoRepo repo;

	@Override
	protected IGenericRepo<PacienteMedicamento, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public List<PacienteMedicamento> listaDeMedicamentoPorPaciente(Paciente paciente) {
		// TODO Auto-generated method stub
		return repo.findByPaciente(paciente);
	}
	
	

	
}
