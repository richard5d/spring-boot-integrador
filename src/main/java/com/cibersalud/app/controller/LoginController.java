package com.cibersalud.app.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.service.IUsuarioService;

@Controller
public class LoginController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error",required=false) String error,
			               @RequestParam(value="logout",required=false) String logout,
			               Model model,
			               Principal principal) {
		if(principal != null) return "redirect:/";
		if(logout != null) model.addAttribute("success","Ha cerrado sesion con exito!");
		if(error != null) model.addAttribute("error","Credenciales Incorrectos");				
		return "login";	
	}
		
	@RequestMapping("/loginSuccess")
	public String loginsuccess() {
		return "redirect:/auth";
	}
	
	@RequestMapping("/auth")
	public String indexLogin(Authentication  auth,Model model) {	
		Usuario u=this.userAuthentication(auth);
		model.addAttribute("usuario",u);
		return "login/perfil";
	}
	
	public Usuario userAuthentication(Authentication  auth) {		
		String email=auth.getName();	
		Usuario u=this.usuarioService.iniciarSesion(email);
		return u;
	}
}
