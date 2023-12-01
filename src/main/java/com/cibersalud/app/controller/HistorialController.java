package com.cibersalud.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibersalud.app.entity.Historial;
import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.service.ICitaWatsonService;
import com.cibersalud.app.service.IHistorialService;
import com.cibersalud.app.service.IMedicamentoService;
import com.cibersalud.app.service.IMedicoService;
import com.cibersalud.app.service.IPacienteService;
import com.cibersalud.app.service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/historial")
public class HistorialController {

	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IHistorialService historialService;
	
	@Autowired
	private IMedicoService medicoService;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@GetMapping("/pacientes")
	public String lista(Model model,Authentication  auth) {
		Usuario u=this.userAuthentication(auth);
		model.addAttribute("usuario",u);
		model.addAttribute("pacientes",this.pacienteService.listar());
		return "historial/pacienteLista";
	}
	
	@RequestMapping("/update/{id}")
	public String update(HttpServletRequest request,@Valid Historial h,BindingResult result,Model model,Authentication  auth) {	
		
		if(h.getId() == null) return "redirect:/historial/pacientes";
		if(!historialService.existe(h.getId())) return "redirect:/historial/pacientes";
		
		
		Usuario u=this.userAuthentication(auth);	
		Paciente p=this.pacienteService.listarPorId(h.getId());
		Historial hUpdate=this.historialService.findByPaciente(p);
		model.addAttribute("ruta","update/"+p.getId());
		model.addAttribute("usuario",u);
		model.addAttribute("titulo","Editar Historial Medico");
		if(request.getMethod().equals("POST")) {
			
			if(result.hasErrors()) {
				return "historial/historialFormulario";
			}else {
				Medico m=this.medicoService.findByUser(u);
				hUpdate.setMedico(m);
				hUpdate.setDiagnostico(h.getDiagnostico());
				hUpdate.setTratamiento(h.getTratamiento());
				hUpdate.setFecha(h.getFecha());
				hUpdate.setHora(h.getHora());
				
				this.historialService.modificar(hUpdate);
				return "redirect:/historial/pacientes";
			}
			
			
		}
		model.addAttribute("historial", hUpdate);
		return "historial/historialFormulario";
	}
	
	
	public Usuario userAuthentication(Authentication  auth) {
		String email=auth.getName();	
		Usuario u=this.usuarioService.iniciarSesion(email);
		return u;
	}
	
}
