package com.cibersalud.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibersalud.app.entity.CitaWatson;
import com.cibersalud.app.service.ICitaWatsonService;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/wapson/cita")
public class CitaWatsonController {
	
	@Autowired
	private ICitaWatsonService service;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody CitaWatson citaWatson){
		citaWatson.setEstado("Pendiente");
		if(citaWatson.getNombre().equals("") || citaWatson.getApellido().equals("") || citaWatson.getMotivo().equals("")) {
			Map mensaje=new HashMap();
			mensaje.put("mensaje","Ingrese todos los datos");
			return new ResponseEntity<Map<String,String>>(mensaje,HttpStatus.FOUND);
		}
		CitaWatson cw=this.service.registrar(citaWatson);
		return new ResponseEntity<CitaWatson>(cw,HttpStatus.CREATED);
	}
	
	@GetMapping("/lista")
	public ResponseEntity<List<CitaWatson>> listar() {
		List<CitaWatson> lista=this.service.listar();
		return new ResponseEntity<List<CitaWatson>>(lista,HttpStatus.OK);
	}
	
}
