package com.cibersalud.app.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.rowset.serial.SerialException;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.service.IMedicoService;
import com.cibersalud.app.service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/medico")
public class MedicoController {
	
	@Autowired
	private IMedicoService medicoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	
	@GetMapping("/lista")
	public String lista(Model model,Authentication  auth) {
		Usuario u=this.userAuthentication(auth);
		model.addAttribute("usuario",u);
		model.addAttribute("medicos",this.medicoService.listaNoEliminado());
		return "medico/medicoLista";
	}
	
	@RequestMapping("/create")
	public String create(HttpServletRequest request,@Valid Medico m,BindingResult result,Model model,Authentication  auth
			,BCryptPasswordEncoder encoder,			
			@RequestParam(name="imagen",required=false) MultipartFile file) throws IOException, SerialException, SQLException {	
		Usuario u=this.userAuthentication(auth);
		model.addAttribute("ruta","create");
		model.addAttribute("usuario",u);
		model.addAttribute("titulo","Registrar Medico");
		if(request.getMethod().equals("POST")) {
			Usuario repeat=this.usuarioService.iniciarSesion(m.getUser().getEmail());
			if(repeat !=null) {
				model.addAttribute("error","Email en uso");
			}else {				
				if(result.hasErrors()) {					
					return "medico/medicoFormulario";
				}
				Usuario usuarioMedico=m.getUser();
				if(file.getSize()>0) {
					
					String formato = file.getOriginalFilename();
					int indice = formato.indexOf(".");
					String nombreImagen = formato.substring(indice+1);
					
					if((nombreImagen.equals("jpg") || nombreImagen.equals("png") || nombreImagen.equals("webp")
							 || nombreImagen.equals("jpeg") ||  nombreImagen.equals("avif")) && file.getSize() < 1048576) {
						
						try {
							byte[] bytes = file.getBytes();
					        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);				
							usuarioMedico.setImage(blob);
						}catch(FileSizeLimitExceededException e) {							
							model.addAttribute("error","SUCEDIO UN ERROR");
							return "medico/medicoFormulario";
						}
					}else {
						
						model.addAttribute("error","SELECCIONE UNA IMAGEN");
						return "medico/medicoFormulario";
					}	
																				
				}
								
				usuarioMedico.setPassword(encoder.encode(usuarioMedico.getPassword()));
				usuarioMedico.setRol("M");
			
				usuarioMedico.setFecha_registro(LocalDate.now());
				this.usuarioService.registrar(usuarioMedico);
				m.setEliminado("no");
				this.medicoService.registrar(m);
				return "redirect:/medico/lista";
			}	
		}
		model.addAttribute("medico",new Medico());
		return "medico/medicoFormulario";
	}
	
	
	@RequestMapping("/update/{id}")
	public String update(HttpServletRequest request,@Valid Medico m,BindingResult result,Model model,Authentication  auth
			,BCryptPasswordEncoder encoder,			
			@RequestParam(name="imagen",required=false) MultipartFile file) throws IOException, SerialException, SQLException {	
		
		if(m.getId() == null) return "redirect:/medico/lista";
		if(!medicoService.existe(m.getId())) return "redirect:/medico/lista";
		
		Usuario u=this.userAuthentication(auth);
		Medico mediUpdate=this.medicoService.listarPorId(m.getId());
		model.addAttribute("ruta","update/"+m.getId());
		model.addAttribute("titulo","Editar Medico");		
		model.addAttribute("usuario",u);

		if(request.getMethod().equals("POST")) {
															
			if(result.hasErrors()) {				
				return "medico/medicoFormulario";
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
				        mediUpdate.getUser().setImage(blob);
					}catch(FileSizeLimitExceededException e) {	
						model.addAttribute("error","SUCEDIO UN ERROR");
						return "medico/medicoFormulario";
					}
				}else {
					model.addAttribute("error","SELECCIONE UNA IMAGEN");
					return "medico/medicoFormulario";
				}		
			}
			
			mediUpdate.getUser().setNombre(m.getUser().getNombre());
			mediUpdate.getUser().setApellido(m.getUser().getApellido());
			mediUpdate.getUser().setEmail(m.getUser().getEmail());
			mediUpdate.setEspecialidad(m.getEspecialidad());
			this.medicoService.modificar(mediUpdate);
			return "redirect:/medico/lista";		
		}
		model.addAttribute("medico",mediUpdate);
		return "medico/medicoFormulario";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(Medico m) {	
		
		if(m.getId() == null) return "redirect:/medico/lista";
		if(!medicoService.existe(m.getId())) return "redirect:/medico/lista";
		
		Medico medi=this.medicoService.listarPorId(m.getId());
		medi.setEliminado("si");
		this.medicoService.modificar(medi);
		return "redirect:/medico/lista";
	}
	
	public Usuario userAuthentication(Authentication  auth) {
		String email=auth.getName();	
		Usuario u=this.usuarioService.iniciarSesion(email);
		return u;
	}
	
	@GetMapping("/display/{id}")
    public ResponseEntity<byte[]> displayImage(Medico medico) throws IOException, SQLException
    {
    	Medico mi=this.medicoService.listarPorId(medico.getId());
    	
    	Usuario usuarioImage=mi.getUser();
        
        byte [] imageBytes = null;
        if(usuarioImage.getImage() != null) {
        	imageBytes = usuarioImage.getImage().getBytes(1,(int) usuarioImage.getImage().length());
        } 
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

}
