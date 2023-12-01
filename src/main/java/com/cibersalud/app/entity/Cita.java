package com.cibersalud.app.entity;


import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="cita")
public class Cita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="paciente")
	private Paciente paciente;
	
	@ManyToOne
	@NotNull(message="No debe estar vacio el medico")
	@JoinColumn(name="medico")
	private Medico medico;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@NotNull(message="No debe estar vacio la fecha")
	private LocalDate fecha;
	
	@NotNull(message="No debe estar vacio la hora")
	private LocalTime hora;
	
	@NotEmpty(message="No debe estar vacio el motivo")
	private String motivo;
	
	private String estado;

}
