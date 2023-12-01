package com.cibersalud.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.repo.IGenericRepo;
import com.cibersalud.app.repo.IMedicamentoRepo;
import com.cibersalud.app.service.IMedicamentoService;
import com.cibersalud.app.service.IUsuarioService;

@Service
public class MedicamentoServiceImpl extends CRUDImpl<Medicamento,Integer> implements IMedicamentoService{

	@Autowired
	private IMedicamentoRepo repo;
	@Override
	protected IGenericRepo<Medicamento, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}
	@Override
	public List<Medicamento> listaNoEliminado() {
		// TODO Auto-generated method stub
		return repo.listaNoEliminado();
	}
	@Override
	public Boolean existe(int id) {
		// TODO Auto-generated method stub
		return repo.existsById(id);
	}

}
