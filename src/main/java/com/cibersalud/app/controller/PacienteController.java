package com.cibersalud.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibersalud.app.entity.Historial;
import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.Receta;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.service.IHistorialService;
import com.cibersalud.app.service.IMedicamentoService;
import com.cibersalud.app.service.IPacienteService;
import com.cibersalud.app.service.IRecetaService;
import com.cibersalud.app.service.IUsuarioService;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private IRecetaService recetaService;
	
	@Autowired
	private IHistorialService historialService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/historial")
	public String historial(Model model,Authentication  auth) {
		Usuario u=this.userAuthentication(auth);
		Paciente p=this.pacienteService.findByUser(u);
		Historial h=this.historialService.findByPaciente(p);
		model.addAttribute("historial",h);
		model.addAttribute("usuario",u);
		return "paciente/historial";
	}
	
	
	
	
	
	@GetMapping("/recetas")
	public String recetas(Model model,Authentication  auth) {
		Usuario u=this.userAuthentication(auth);
		Paciente p=this.pacienteService.findByUser(u);
		List<Receta> recetas=this.recetaService.recetasDelPaciente(p.getId());
		
		model.addAttribute("recetas",recetas);
		model.addAttribute("usuario",u);
		return "paciente/recetas";
	}
	
	public Usuario userAuthentication(Authentication  auth) {
		String email=auth.getName();	
		Usuario u=this.usuarioService.iniciarSesion(email);
		return u;
	}

}
