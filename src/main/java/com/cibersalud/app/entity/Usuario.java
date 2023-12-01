package com.cibersalud.app.entity;

import java.sql.Blob;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message="No Debe estar vacio el Email")
	@Email(message="Email no valido !!!")
	private String email;
	
	@NotEmpty(message="No Debe estar vacio el Password")
	private String password;
	
	@NotEmpty(message="No Debe estar vacio el Nombre")
	private String nombre;
	
	@NotEmpty(message="No Debe estar vacio el Apellido")
	private String apellido;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private LocalDate fecha_nacimiento;
	
	private String genero;
	private String direccion;
	private String telefono;
	
	@Column(name="rol",length=1)
	private String rol;
	
	@Lob
    private Blob image;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private LocalDate fecha_registro;
	
}
