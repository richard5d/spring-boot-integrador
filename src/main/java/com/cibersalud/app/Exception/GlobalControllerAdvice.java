package com.cibersalud.app.Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import com.cibersalud.app.entity.Medicamento;
import com.cibersalud.app.entity.Medico;
import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.service.IMedicamentoService;
import com.cibersalud.app.service.IMedicoService;
import com.cibersalud.app.service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice{
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IMedicamentoService medicamentoService;
	
	@Autowired
	private IMedicoService medicoService;
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleFileSizeLimitExceeded(HttpServletRequest req, Exception ex,Authentication  auth) {
		Usuario u=this.userAuthentication(auth);
        
        String[] ssss=req.getServletPath().split("/");
        
        ModelAndView modelAndView = new ModelAndView();
        if(req.getServletPath().equals("/usuario/imagen")) {
        	modelAndView.addObject("error","SOBREPASO EL TAMAÑO MAXIMO 900kb,INGRESE UNA IMAGEN MENOS PESADA");
        	modelAndView.setViewName("login/usuarioimagen");
        }else if(req.getServletPath().equals("/medicamento/create")) {
        	modelAndView.addObject("ruta","create");
        	modelAndView.addObject("error","SOBREPASO EL TAMAÑO MAXIMO 900kb,INGRESE UNA IMAGEN MENOS PESADA");
        	modelAndView.addObject("titulo","Registrar Medicamento");
        	modelAndView.addObject("medicamento", new Medicamento());
        	modelAndView.setViewName("medicamento/medicamentoFormulario");
        }else if("medicamento/update".equals(ssss[1]+"/"+ssss[2])) {
        	modelAndView.addObject("error","SOBREPASO EL TAMAÑO MAXIMO 900kb,INGRESE UNA IMAGEN MENOS PESADA");
        	Medicamento mediUpdate=this.medicamentoService.listarPorId(Integer.parseInt(ssss[3]));
        	modelAndView.addObject("ruta","update/"+ssss[3]);
        	modelAndView.addObject("titulo","Editar Medicamento");
        	modelAndView.addObject("medicamento",mediUpdate);
        	modelAndView.setViewName("medicamento/medicamentoFormulario");
        }else if(req.getServletPath().equals("/medico/create")) {
        	modelAndView.addObject("ruta","create");
        	modelAndView.addObject("error","SOBREPASO EL TAMAÑO MAXIMO 900kb,INGRESE UNA IMAGEN MENOS PESADA");
        	modelAndView.addObject("titulo","Registrar Medico");
        	Usuario usuario = new Usuario();
        	Medico medico=new Medico();
        	medico.setUser(usuario);
        	modelAndView.addObject("medico", medico);
        	modelAndView.setViewName("medico/medicoFormulario");
        }else if("medico/update".equals(ssss[1]+"/"+ssss[2])) {
        	modelAndView.addObject("error","SOBREPASO EL TAMAÑO MAXIMO 900kb,INGRESE UNA IMAGEN MENOS PESADA");
        	Medico mediUpdate=this.medicoService.listarPorId(Integer.parseInt(ssss[3]));
        	modelAndView.addObject("ruta","update/"+ssss[3]);
        	modelAndView.addObject("titulo","Editar Medico");
        	modelAndView.addObject("medico",mediUpdate);
        	modelAndView.setViewName("medico/medicoFormulario");
        }else {
        	modelAndView.setViewName("inicio");
        }
        
        modelAndView.addObject("usuario",u);
        return  modelAndView;
    }
	
	public Usuario userAuthentication(Authentication  auth) {
		String email=auth.getName();	
		Usuario u=this.usuarioService.iniciarSesion(email);
		return u;
	}
	
}
