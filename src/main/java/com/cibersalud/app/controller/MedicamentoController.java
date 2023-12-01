package com.cibersalud.app.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cibersalud.app.entity.CitaWatson;

import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.MedicamentoWatson;
import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.service.ICitaWatsonService;

import com.cibersalud.app.service.IMedicamentoService;
import com.cibersalud.app.service.IMedicamentoWatsonService;
import com.cibersalud.app.service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/medicamento")
public class MedicamentoController {

	@Autowired
	private IMedicamentoService medicamentoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ICitaWatsonService watsonService;
	
	@Autowired
	private IMedicamentoWatsonService watsonMedicamentoService;
	
	@GetMapping("/lista")
	public String lista(Model model,Authentication  auth) {
		Usuario u=this.userAuthentication(auth);
		model.addAttribute("usuario",u);
		model.addAttribute("medicamentos",this.medicamentoService.listaNoEliminado());
		return "medicamento/medicamentoLista";
	}
	
	
	
	@RequestMapping("/create")
	public String create(HttpServletRequest request,@Valid Medicamento m,BindingResult result
			,Model model,Authentication  auth,			
			@RequestParam(name="imagen",required=false) MultipartFile file) throws IOException, SerialException, SQLException {	
		Usuario u=this.userAuthentication(auth);
		model.addAttribute("ruta","create");
		model.addAttribute("usuario",u);
		model.addAttribute("titulo","Registrar Medicamento");
		if(request.getMethod().equals("POST")) {
			
			if(result.hasErrors()) {
				return "medicamento/medicamentoFormulario";
			}
			if(file.getSize() > 0) {
				
				String formato = file.getOriginalFilename();
				int indice = formato.indexOf(".");
				String nombreImagen = formato.substring(indice+1);
				
				
				if((nombreImagen.equals("jpg") || nombreImagen.equals("png") || nombreImagen.equals("webp")
						 || nombreImagen.equals("jpeg") ||  nombreImagen.equals("avif")) && file.getSize() < 1048576) {
					
					
					try {
						byte[] bytes = file.getBytes();
				        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
				        m.setImage(blob);
					}catch(FileSizeLimitExceededException e) {
						
					}
				}else {
					
					model.addAttribute("error","SELECCIONE UNA IMAGEN");
					return "medicamento/medicamentoFormulario";
				}	
			}
			m.setEliminado("no");
			this.medicamentoService.registrar(m);
			return "redirect:/medicamento/lista";
		}
		
		model.addAttribute("medicamento", new Medicamento());
		return "medicamento/medicamentoFormulario";
	}
	
	@RequestMapping("/update/{id}")
	public String update(HttpServletRequest request,@Valid Medicamento m,BindingResult result,Model model,Authentication  auth,			
			@RequestParam(name="imagen",required=false) MultipartFile file) throws IOException, SerialException, SQLException {
		
		if(m.getId() == null) return "redirect:/medicamento/lista";
		if(!medicamentoService.existe(m.getId())) return "redirect:/medicamento/lista";
		
		Medicamento mediUpdate=this.medicamentoService.listarPorId(m.getId());
		Usuario u=this.userAuthentication(auth);
		model.addAttribute("ruta","update/"+m.getId());
		model.addAttribute("titulo","Editar Medicamento");
		model.addAttribute("usuario",u);
		if(request.getMethod().equals("POST")) {
			
			if(result.hasErrors()) {
				
				return "medicamento/medicamentoFormulario";
			}
			if(file.getSize()>0) {
				
				String formato = file.getOriginalFilename();
				int indice = formato.indexOf(".");
				String nombreImagen = formato.substring(indice+1);
				
				
				if((nombreImagen.equals("jpg") || nombreImagen.equals("png") || nombreImagen.equals("webp")
						 || nombreImagen.equals("jpeg") ||  nombreImagen.equals("avif")) && file.getSize() < 1048576) {
					
					
					try {
						byte[] bytes = file.getBytes();
				        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
				        mediUpdate.setImage(blob);
					}catch(FileSizeLimitExceededException e) {
						
					}
				}else {
					
					model.addAttribute("error","SELECCIONE UNA IMAGEN");
					return "medicamento/medicamentoFormulario";
				}	
			}
			
			mediUpdate.setNombre(m.getNombre());
			mediUpdate.setDescripcion(m.getDescripcion());
			mediUpdate.setInstrucciones(m.getInstrucciones());
			mediUpdate.setPrecio(m.getPrecio());
			this.medicamentoService.modificar(mediUpdate);
			return "redirect:/medicamento/lista";
		}
		model.addAttribute("medicamento",mediUpdate);
		return "medicamento/medicamentoFormulario";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(Medicamento m) {	
		
		if(m.getId() == null) return "redirect:/medicamento/lista";
		if(!medicamentoService.existe(m.getId())) return "redirect:/medicamento/lista";
		
		Medicamento medi=this.medicamentoService.listarPorId(m.getId());
		medi.setEliminado("si");
		this.medicamentoService.modificar(medi);
		return "redirect:/medicamento/lista";
	}
	
	public Usuario userAuthentication(Authentication  auth) {
		String email=auth.getName();	
		Usuario u=this.usuarioService.iniciarSesion(email);
		return u;
	}
	
	
	@GetMapping("/display/{id}")
    public ResponseEntity<byte[]> displayImage(Medicamento medicamento) throws IOException, SQLException
    {
    	Medicamento mi=this.medicamentoService.listarPorId(medicamento.getId());   
        byte [] imageBytes = null;
        if(mi.getImage() != null) {
        	imageBytes = mi.getImage().getBytes(1,(int) mi.getImage().length());
        } 
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
	
	@RequestMapping("/watson/lista")
	public String citaWatson(Model model,Authentication  auth) {
		Usuario u=this.userAuthentication(auth);
		model.addAttribute("usuario",u);
		model.addAttribute("citaswatson",this.watsonService.listar());
		return "citawatson/citawatsonLista";
	}
	
	@RequestMapping("/watson/delete/{id}")
	public String watsonDelete(@Valid CitaWatson c,BindingResult result) {
		if(c.getId() == null) return "redirect:/medicamento/watson/lista";
		if(!this.watsonService.existe(c.getId())) return "redirect:/medicamento/watson/lista";
		this.watsonService.eliminar(c.getId());
		return "redirect:/medicamento/watson/lista";
	}
	
	@RequestMapping("/watson/medicamentos")
	public String medicamentoWatson(Model model,Authentication  auth) {
		Usuario u=this.userAuthentication(auth);
		model.addAttribute("usuario",u);
		model.addAttribute("medicamentoswatson",this.watsonMedicamentoService.listar());
		return "citawatson/medicamentowatsonLista";
	}
	
	@RequestMapping("/watson/m/delete/{id}")
	public String watsonmediDelete(@Valid MedicamentoWatson m,BindingResult result) {
		if(m.getId() == null) return "redirect:/medicamento/watson/medicamentos";
		if(!this.watsonMedicamentoService.existe(m.getId())) return "redirect:/medicamento/watson/medicamentos";
		this.watsonMedicamentoService.eliminar(m.getId());
		return "redirect:/medicamento/watson/medicamentos";
	}
	
	
	

}
