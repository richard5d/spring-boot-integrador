package com.cibersalud.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibersalud.app.entity.Cita;
import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.service.ICitaService;
import com.cibersalud.app.service.ICitaWatsonService;
import com.cibersalud.app.service.IMedicamentoService;
import com.cibersalud.app.service.IMedicoService;
import com.cibersalud.app.service.IPacienteService;
import com.cibersalud.app.service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cita")
public class CitaController {
	
	@Autowired
	private ICitaService citaService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private IMedicoService medicoService;
	
	@GetMapping("/lista")
	public String lista(Model model,Authentication  auth) {
		Usuario u=this.userAuthentication(auth);
		Paciente paciente=this.pacienteService.findByUser(u);
		model.addAttribute("usuario",u);
		model.addAttribute("citas",citaService.findByPaciente(paciente));
		return "cita/citaLista";
	}
	
	@RequestMapping("/create/inicio/{id}")
	public String createInicio(@PathVariable Integer id,HttpServletRequest request,Model model,Authentication  auth) {
		
		Medico medicoInicio=null;
		try {
			medicoInicio=medicoService.listarPorId(id);
		}catch(NoSuchElementException e) {
			System.out.println("error en create cita inicio con path variable id");
			return "redirect:/cita/lista";
		}
		
		Usuario u=this.userAuthentication(auth);
		Paciente paciente=pacienteService.findByUser(u);
		
		Cita cita=new Cita();
		cita.setMedico(medicoInicio);
		cita.setFecha(LocalDate.now());
		cita.setHora(LocalTime.now());
		cita.setPaciente(paciente);
		cita.setMotivo("Desconocido");
		cita.setEstado("PENDIENTE");
		this.citaService.registrar(cita);
		return "redirect:/cita/lista";
	}
	
	@RequestMapping("/create")
	public String create(@Valid Cita c,BindingResult result,HttpServletRequest request,Model model,Authentication  auth) {	
		Usuario u=this.userAuthentication(auth);	
		
		model.addAttribute("medicos", this.medicoService.listar());
		model.addAttribute("ruta","create");
		model.addAttribute("usuario",u);
		model.addAttribute("titulo","Registrar Cita Medica");
		if(request.getMethod().equals("POST")) {
			
			if(result.hasErrors()) {
				return "cita/citaFormulario";
			}
			c.setPaciente(pacienteService.findByUser(u));
			c.setEstado("PENDIENTE");	
			this.citaService.registrar(c);
			return "redirect:/cita/lista";
		}
		model.addAttribute("cita", new Cita());
		return "cita/citaFormulario";
	}
	
	@RequestMapping("/update/{id}")
	public String update(HttpServletRequest request,@Valid Cita c,BindingResult result,Model model,Authentication  auth) {
		
		if(c.getId() == null) return "redirect:/cita/lista";
		if(!this.citaService.existe(c.getId())) return "redirect:/cita/lista";
		
		Cita citaUpdate=this.citaService.listarPorId(c.getId());
		
		Usuario u=this.userAuthentication(auth);
		if(request.getMethod().equals("POST")) {
			citaUpdate.setFecha(c.getFecha());
			citaUpdate.setHora(c.getHora());
			citaUpdate.setMotivo(c.getMotivo());
			citaUpdate.setMedico(c.getMedico());
			this.citaService.modificar(citaUpdate);
			return "redirect:/cita/lista";
		}
		
		model.addAttribute("medicos", this.medicoService.listar());
		model.addAttribute("cita", citaUpdate);
		model.addAttribute("ruta","update/"+citaUpdate.getId());
		model.addAttribute("usuario",u);
		model.addAttribute("titulo","Editar Cita Medica");
		return "cita/citaFormulario";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@Valid Cita c,BindingResult result) {	
		if(c.getId() == null) return "redirect:/cita/lista";
		if(!this.citaService.existe(c.getId())) return "redirect:/cita/lista";
		this.citaService.eliminar(c.getId());	
		return "redirect:/cita/lista";
	}
	
	public Usuario userAuthentication(Authentication  auth) {
		String email=auth.getName();	
		Usuario u=this.usuarioService.iniciarSesion(email);
		return u;
	}
}
