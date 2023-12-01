package com.cibersalud.app.entity;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="medicamento")
public class Medicamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty
	private String nombre;
	
	
	@NotEmpty
	private String descripcion;
	
	@NotEmpty
	private String instrucciones;
	
	@NotNull(message="no debe estar vacio el precio")
	@DecimalMin(value = "0",message="debe ser cero minimo")
	@DecimalMax(value = "100",message="debe ser maximo hasta 100")
	private Double precio;
	
	@Column(length=2)
	private String eliminado;
	
	@Lob
	@JsonIgnore
    private Blob image;

}
