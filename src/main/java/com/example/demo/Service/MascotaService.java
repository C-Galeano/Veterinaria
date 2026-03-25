package com.example.veterinaria.SERVICE;

import com.example.veterinaria.DTO.MascotaRequestDTO;
import com.example.veterinaria.DTO.MascotaResponseDTO;

import java.util.List;

// interfaz del servicio de Mascota
// trabaja con DTOs — no expone la entidad directamente
public interface MascotaService {

    // CREATE → INSERT INTO mascotas (...) VALUES (...)
    MascotaResponseDTO crear(MascotaRequestDTO dto);

    // READ ALL → SELECT * FROM mascotas
    List<MascotaResponseDTO> obtenerTodas();

    // READ ACTIVAS → SELECT * FROM mascotas WHERE activo = true
    List<MascotaResponseDTO> obtenerActivas();

    // READ BY ID → SELECT * FROM mascotas WHERE id = ?
    MascotaResponseDTO obtenerPorId(Long id);

    // READ BY ESPECIE → SELECT * FROM mascotas WHERE especie = ?
    List<MascotaResponseDTO> obtenerPorEspecie(String especie);

    // SEARCH → SELECT * FROM mascotas WHERE LOWER(nombre) LIKE LOWER('%?%')
    List<MascotaResponseDTO> buscarPorNombre(String nombre);

    // UPDATE → UPDATE mascotas SET ... WHERE id = ?
    MascotaResponseDTO actualizar(Long id, MascotaRequestDTO dto);

    // DELETE LOGICO → UPDATE mascotas SET activo = false WHERE id = ?
    void desactivar(Long id);

    // DELETE FISICO → DELETE FROM mascotas WHERE id = ?
    void eliminar(Long id);
}