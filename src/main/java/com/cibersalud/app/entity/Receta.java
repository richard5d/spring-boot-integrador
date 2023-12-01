package com.cibersalud.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="recetamedica")
public class Receta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="historialmedico")
	private Historial historialmedico;
	
	@ManyToOne
	@JoinColumn(name="medicamento")
	private Medicamento medicamento;
	
	@Min(value = 1,message="debe ser un numero entero minimo 1")
	@Max(value = 100,message="debe ser un numero entero maximo 100")
	@NotNull(message="No debe estar vacio la cantidad")
	private Integer cantidad;
	
	@NotEmpty(message="No debe estar vacio las instrucciones")
	private String instrucciones;
}
