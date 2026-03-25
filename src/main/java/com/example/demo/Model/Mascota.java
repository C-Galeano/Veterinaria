package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// entidad que mapea la tabla mascotas en MySQL
@Entity
@Table(name = "mascotas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mascota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// nombre de la mascota — no puede ser nulo
	@Column(nullable = false, length = 80)
	private String nombre;

	// especie: perro, gato, conejo, etc.
	@Column(nullable = false, length = 50)
	private String especie;

	// raza de la mascota
	@Column(length = 80)
	private String raza;

	// fecha de nacimiento para calcular la edad
	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;

	// peso en kilogramos
	@Column
	private Double peso;

	// si la mascota está activa en el sistema
	@Column(nullable = false)
	private Boolean activo = true;
}