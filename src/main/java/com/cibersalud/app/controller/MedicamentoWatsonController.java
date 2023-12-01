package com.cibersalud.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibersalud.app.entity.CitaWatson;
import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.MedicamentoWatson;
import com.cibersalud.app.service.ICitaWatsonService;
import com.cibersalud.app.service.IMedicamentoService;
import com.cibersalud.app.service.IMedicamentoWatsonService;

@RestController
@RequestMapping("/wapson/medicamento")
public class MedicamentoWatsonController {
	
	@Autowired
	private IMedicamentoWatsonService service;
	
	@Autowired
	private IMedicamentoService servicem;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody MedicamentoWatson medicamento){
		medicamento.setEstado("PENDIENTE");
		MedicamentoWatson cw=this.service.registrar(medicamento);
		return new ResponseEntity<MedicamentoWatson>(cw,HttpStatus.CREATED);
	}
}
