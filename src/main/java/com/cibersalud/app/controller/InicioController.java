package com.cibersalud.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.service.IMedicamentoService;
import com.cibersalud.app.service.IMedicoService;
import com.cibersalud.app.service.IUsuarioService;

@Controller
public class InicioController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IMedicoService medicoService;
	
	@Autowired
	private IMedicamentoService medicamentoService;
	
	@GetMapping("/")
	public String inicio(Authentication  auth,Model model) {
		
		return "inicio";
	}
	
	@GetMapping("/somos")
	public String noticias(Authentication  auth,Model model) {
		
		return "noticias";
	}
	
	@GetMapping("/medicos")
	public String medicos(Authentication  auth,Model model) {
		model.addAttribute("medicos", this.medicoService.listaNoEliminado());
		return "inicio/medicos";
	}
	
	@GetMapping("/medicamentos")
	public String medicamentos(Authentication  auth,Model model) {
		model.addAttribute("medicamentos", this.medicamentoService.listaNoEliminado());
		return "inicio/medicamentos";
	}
	
	public Usuario userAuthentication(Authentication  auth) {
		if(auth !=null) {
			String email=auth.getName();	
			Usuario u=this.usuarioService.iniciarSesion(email);
			return u;	
		}
		return null;
	}
}
