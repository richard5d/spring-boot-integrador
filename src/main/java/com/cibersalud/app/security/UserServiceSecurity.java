package com.cibersalud.app.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cibersalud.app.entity.Usuario;
import com.cibersalud.app.repo.IUsuarioRepo;
import com.cibersalud.app.service.IUsuarioService;

@Service
public class UserServiceSecurity implements UserDetailsService{
	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDetails obj=null;
		
		Usuario usuarioLogin=usuarioService.iniciarSesion(email);
		
		if (usuarioLogin == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }else {
        	Set<GrantedAuthority> rol=new HashSet<GrantedAuthority>();
    		rol.add(new SimpleGrantedAuthority(usuarioLogin.getRol()));
    		obj=new User(email,usuarioLogin.getPassword(),rol);    		
    		return obj;
        }				
	}		
	
	
}
