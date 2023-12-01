package com.cibersalud.app.service.impl;

import java.util.List;

import com.cibersalud.app.repo.IGenericRepo;
import com.cibersalud.app.service.ICRUD;

public abstract class CRUDImpl<T,ID> implements ICRUD<T,ID> {
	
	protected abstract IGenericRepo<T,ID> getRepo();

	@Override
	public T registrar(T t) {
		// TODO Auto-generated method stub
		return getRepo().save(t);
	}

	@Override
	public T modificar(T t) {
		// TODO Auto-generated method stub
		return getRepo().save(t);
	}

	@Override
	public List<T> listar() {
		// TODO Auto-generated method stub
		return getRepo().findAll();
	}

	@Override
	public T listarPorId(ID id) {
		// TODO Auto-generated method stub
		return getRepo().findById(id).get();
	}

	@Override
	public void eliminar(ID id) {
		getRepo().deleteById(id);
	}

}
