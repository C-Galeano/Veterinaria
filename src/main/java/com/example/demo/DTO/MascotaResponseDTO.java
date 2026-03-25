package com.example.veterinaria.DTO;

import lombok.*;

import java.time.LocalDate;

// DTO para devolver los datos de una mascota al cliente
// incluye el id y el campo activo que son datos de respuesta
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaResponseDTO {

    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private Double peso;
    private Boolean activo;
}