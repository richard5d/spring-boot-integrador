package com.cibersalud.app.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibersalud.app.entity.Historial;
import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.PacienteMedicamento;
import com.cibersalud.app.entity.Paciente;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.service.IMedicamentoService;
import com.cibersalud.app.service.IPacienteMedicamentoService;
import com.cibersalud.app.service.IPacienteService;
import com.cibersalud.app.service.IUsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pacientemedicamento")
public class PacienteMedicamentoController {
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private IPacienteMedicamentoService pacienteMedicamentoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IMedicamentoService medicamentoService;
	
	@GetMapping("/lista")
	public String lista(Authentication auth,Model model) {
		Usuario usuario=this.userAuthentication(auth);
		Paciente paciente=this.pacienteService.findByUser(usuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("medicamentos",pacienteMedicamentoService.listaDeMedicamentoPorPaciente(paciente));
		return "paciente/medicamentos";
	}
	
	
	@GetMapping("/create/{id}")
	public String create(@PathVariable Integer id,Authentication auth){
		Medicamento medicamento=null;
		try {
			medicamento=this.medicamentoService.listarPorId(id);
		}catch(NoSuchElementException e) {
			return "redirect:/pacientemedicamento/lista";
		}
		
		Usuario usuario=this.userAuthentication(auth);
		
		Paciente paciente=this.pacienteService.findByUser(usuario);
		
		PacienteMedicamento pm=new PacienteMedicamento();
		
		pm.setPaciente(paciente);
		pm.setMedicamento(medicamento);
		this.pacienteMedicamentoService.registrar(pm);
		
		return "redirect:/pacientemedicamento/lista";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		this.pacienteMedicamentoService.eliminar(id);	
		return "redirect:/pacientemedicamento/lista";
	}
	
	public Usuario userAuthentication(Authentication  auth) {
		String email=auth.getName();	
		Usuario u=this.usuarioService.iniciarSesion(email);
		return u;
	}

}
