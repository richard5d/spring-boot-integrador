package com.cibersalud.app.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((auth) -> {
                try {
                    auth.requestMatchers("/css/**","/js/**","/imagenes/**","/medico/display/**",
                    		"/medicamento/display/**","/","/medicos","/medicamentos",
                    		"/somos","/usuario/registrar","/wapson/**")
                        .permitAll()
                        .requestMatchers("/usuario/lista","/medicamento/create","/medicamento/update/**",
                        		"/medicamento/delete/**","/medicamento/lista",
                        		"/medico/create","/medico/update/**","/medico/delete/**","/medico/lista",
                        		"/medicamento/watson/lista","/medicamento/watson/medicamentos","/swagger-ui/index.html").hasAnyAuthority("A")
                        .requestMatchers("/cita/create","/cita/update/**","/cita/delete/**","/cita/lista","/paciente/historial",
                        		"/paciente/recetas","/pacientemedicamento/lista").hasAnyAuthority("P")
                        .requestMatchers("/historial/pacientes","/historial/update/**","/receta/create","/receta/lista").hasAnyAuthority("M")
                        .anyRequest().authenticated();                                           
                } catch (Exception e) {
                        e.printStackTrace();
                }
            }).csrf().disable().cors().disable().formLogin(form->form.loginPage("/login")
		             .permitAll()
		             .successForwardUrl("/loginSuccess")).logout(form->form.permitAll()); 
        return http.build();            
    }
	
	/*
	 * @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((auth) -> {
                try {
                    auth.requestMatchers("/css/**","/js/**","/imagenes/**","/medico/display/**",
                    		"/medicamento/display/**","/inicio","/medicos","/medicamentos",
                    		"/noticias","/usuario/registrar","/wapson/**")
                        .permitAll()
                        .anyRequest().authenticated();                                           
                } catch (Exception e) {
                        e.printStackTrace();
                }
            }).csrf().disable().cors().configurationSource(new CorsConfigurationSource() {

				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration cfg=new CorsConfiguration();
					cfg.setAllowedOrigins(Arrays.asList(
							
							));
					cfg.setAllowedMethods(Collections.singletonList("*"));
					cfg.setAllowCredentials(false);
					cfg.setAllowedHeaders(Collections.singletonList("*"));
					cfg.setExposedHeaders(Arrays.asList("Authorization"));
					cfg.setMaxAge(3600L);
					
					return cfg;
				}
            	
            	
            }).and().formLogin(form->form.loginPage("/login")
		             .permitAll()
		             .successForwardUrl("/loginSuccess")).logout(form->form.permitAll()); 
        return http.build();            
    }
	 * 
	 * 
	 * 
	 * 
	 
	 * */
	
}


/*
 
 .csrf().disable().cors().configurationSource(new CorsConfigurationSource() {

				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration cfg=new CorsConfiguration();
					cfg.setAllowedOrigins(Arrays.asList(
							"http://localhost:3000",
							"http://localhost:4200",
							"http://localhost:8080"
							));
					cfg.setAllowedMethods(Collections.singletonList("*"));
					cfg.setAllowCredentials(false);
					cfg.setAllowedHeaders(Collections.singletonList("*"));
					cfg.setExposedHeaders(Arrays.asList("Authorization"));
					cfg.setMaxAge(3600L);
					
					return cfg;
				}
            	
            	
            }).and()
 
 
 
  */
