package com.cibersalud.app.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.service.IHistorialService;
import com.cibersalud.app.service.IPacienteService;
import com.cibersalud.app.service.IUsuarioService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import com.cibersalud.app.entity.Historial;
import com.cibersalud.app.entity.Paciente;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private IHistorialService historialService;
	
	@GetMapping("/lista")
	public String lista(Model model,Authentication  auth) {
		Usuario u=this.userAuthentication(auth);
		model.addAttribute("usuario",u);
		model.addAttribute("usuarios",this.usuarioService.listar());
		return "login/usuarios";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@Valid Usuario usuario,BindingResult result,Model model,HttpServletRequest request,BCryptPasswordEncoder encoder) {	
		
		if(request.getMethod().equals("POST")) {		
			if(result.hasErrors()) {
				return "registrarUsuario";	
			}
			
			Usuario repeat=this.usuarioService.iniciarSesion(usuario.getEmail());
			if(repeat !=null) {
				model.addAttribute("error","Email en uso");
			}else {	
				usuario.setRol("P");
				usuario.setPassword(encoder.encode(usuario.getPassword()));
				usuario.setFecha_registro(LocalDate.now());
				Paciente p=new Paciente();
				p.setUser(this.usuarioService.registrar(usuario));
				Historial h=new Historial();
				h.setFecha(LocalDate.now());
				h.setHora(LocalTime.now());
				h.setDiagnostico("NO HAY DATOS");
				h.setTratamiento("NO HAY DATOS");
				h.setPaciente(this.pacienteService.registrar(p));
			    this.historialService.registrar(h);
			    model.addAttribute("usuario", new Usuario());
			    model.addAttribute("mensaje","Se creo exitosamente la cuenta");
			    return "registrarUsuario";
			}	
		}
		model.addAttribute("usuario", new Usuario());
		return "registrarUsuario";	
	}
	
	@RequestMapping("/perfil")
	public String Perfil(@Valid Usuario usuario,BindingResult result,Model model,Authentication  auth,HttpServletRequest request) {
		Usuario u=this.userAuthentication(auth);
		if(request.getMethod().equals("POST")) {
			
			if(result.hasErrors()) {
				return "login/perfil";	
			}
			
			u.setNombre(usuario.getNombre());
			u.setApellido(usuario.getApellido());
			u.setFecha_nacimiento(usuario.getFecha_nacimiento());
			u.setDireccion(usuario.getDireccion());
			u.setTelefono(usuario.getTelefono());
			u.setGenero(usuario.getGenero());
			this.usuarioService.modificar(u);
			model.addAttribute("mensaje","Se edito correctamente");
		}
		model.addAttribute("usuario", u);
		return "login/perfil";
	}
	
	@GetMapping("/display")
    public ResponseEntity<byte[]> displayImage(Authentication  auth) throws IOException, SQLException
    {
        Usuario u=this.userAuthentication(auth);
        byte [] imageBytes = null;
        if(u.getImage() != null) {
        	imageBytes = u.getImage().getBytes(1,(int) u.getImage().length());
        } 
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
	
	@RequestMapping("/imagen")
	public String imagen(Model model,Authentication  auth,HttpServletRequest request,			
			@RequestParam(name="image",required=false) MultipartFile file) throws IOException, SerialException, SQLException {
		
		Usuario u=this.userAuthentication(auth);
		
		if(request.getMethod().equals("POST")) {
			
			String formato = file.getOriginalFilename();
			int indice = formato.indexOf(".");
			String nombreImagen = formato.substring(indice+1);
			
			if((nombreImagen.equals("jpg") || nombreImagen.equals("png") || nombreImagen.equals("webp")
					 || nombreImagen.equals("jpeg") ||  nombreImagen.equals("avif")) && file.getSize() < 1048576) {
				
				
				try {
					byte[] bytes = file.getBytes();
			        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
			        u.setImage(blob);
					this.usuarioService.modificar(u);
					model.addAttribute("mensaje","Se edito correctamente");
				}catch(FileSizeLimitExceededException e) {
					
				}
			}else {
				
				model.addAttribute("error","SELECCIONE UNA IMAGEN");
			}
			
		}
		model.addAttribute("usuario",u);
		return "login/usuarioimagen";
	}
	
	public Usuario userAuthentication(Authentication  auth) {
		String email=auth.getName();	
		Usuario u=this.usuarioService.iniciarSesion(email);
		return u;
	}
	
	
	
}
