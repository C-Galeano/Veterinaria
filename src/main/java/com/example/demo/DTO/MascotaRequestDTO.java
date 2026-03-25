package com.example.veterinaria.DTO;

import lombok.*;

import java.time.LocalDate;

// DTO para recibir los datos cuando se crea o actualiza una mascota
// no incluye el id porque lo genera la BD
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaRequestDTO {

    // datos que el cliente debe enviar
    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private Double peso;
}