package com.cibersalud.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibersalud.app.entity.Historial;
import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Receta;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.service.ICitaWatsonService;
import com.cibersalud.app.service.IHistorialService;
import com.cibersalud.app.service.IMedicamentoService;
import com.cibersalud.app.service.IMedicoService;
import com.cibersalud.app.service.IRecetaService;
import com.cibersalud.app.service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/receta")
public class RecetaController {
	
	@Autowired
	private IHistorialService historialService;
	
	@Autowired
	private IMedicoService medicoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IRecetaService recetaService;
	
	@Autowired
	private IMedicamentoService medicamentoService;
	
	@GetMapping("/lista")
	public String lista(Model model,Authentication  auth) {
		Usuario u=this.userAuthentication(auth);
		Medico m=this.medicoService.findByUser(u);
		model.addAttribute("usuario",u);
		model.addAttribute("recetas", recetaService.recetasDelMedico(m.getId()));
		return "receta/recetaLista";
	}
	
	@RequestMapping("/create")
	public String create(HttpServletRequest request,@Valid Receta r,BindingResult result,Model model,Authentication  auth) {	
		Usuario u=this.userAuthentication(auth);	
		model.addAttribute("titulo","Registrar Receta Medica");
		model.addAttribute("historias",this.historialService.listar());
		model.addAttribute("medicamentos", this.medicamentoService.listar());
		model.addAttribute("ruta","create");
		model.addAttribute("usuario",u);
		if(request.getMethod().equals("POST")) {			
			if(result.hasErrors()) {				
				return "receta/recetaFormulario";
			}
			Historial h=r.getHistorialmedico();
			h.setMedico(this.medicoService.findByUser(u));
			this.recetaService.registrar(r);
			return "redirect:/receta/lista";
		}
		
		model.addAttribute("receta",new Receta());
		
		return "receta/recetaFormulario";
	}
	
	@RequestMapping("/update/{id}")
	public String update(HttpServletRequest request,@Valid Receta r,BindingResult result,Model model,Authentication  auth) {
		
		if(r.getId() == null) return "redirect:/receta/lista";
		if(!recetaService.existe(r.getId())) return "redirect:/receta/lista";
		
		Receta reUpdate=this.recetaService.listarPorId(r.getId());
		
		
		
		Usuario u=this.userAuthentication(auth);
		model.addAttribute("historias",this.historialService.listar());
		model.addAttribute("medicamentos", this.medicamentoService.listar());
		model.addAttribute("ruta","update/"+r.getId());
		model.addAttribute("titulo","Editar Receta Medica");
		model.addAttribute("usuario",u);
		if(request.getMethod().equals("POST")) {
			if(result.hasErrors()) {
				return "receta/recetaFormulario";
			}
			
			
			reUpdate.setHistorialmedico(r.getHistorialmedico());
			reUpdate.setMedicamento(r.getMedicamento());
			reUpdate.setCantidad(r.getCantidad());
			reUpdate.setInstrucciones(r.getInstrucciones());
			this.recetaService.modificar(reUpdate);
			return "redirect:/receta/lista";
		}
		
		model.addAttribute("receta", reUpdate);
		
		return "receta/recetaFormulario";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@Valid Receta r,BindingResult result) {	
		
		if(r.getId() == null) return "redirect:/receta/lista";
		if(!recetaService.existe(r.getId())) return "redirect:/receta/lista";
		
		this.recetaService.eliminar(r.getId());
		return "redirect:/receta/lista";
	}
	
	public Usuario userAuthentication(Authentication  auth) {
		String email=auth.getName();	
		Usuario u=this.usuarioService.iniciarSesion(email);
		return u;
	}
	
	

}
