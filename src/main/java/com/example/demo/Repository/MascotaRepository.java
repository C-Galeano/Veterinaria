package com.example.veterinaria.REPOSITORY;

import com.example.veterinaria.MODEL.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// repositorio de Mascota — Spring genera las implementaciones automáticamente
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    // busca mascotas por especie
    // SQL: SELECT * FROM mascotas WHERE especie = ?
    List<Mascota> findByEspecie(String especie);

    // busca mascotas por nombre sin importar mayúsculas
    // SQL: SELECT * FROM mascotas WHERE LOWER(nombre) LIKE LOWER('%?%')
    List<Mascota> findByNombreContainingIgnoreCase(String nombre);

    // trae solo las mascotas activas
    // SQL: SELECT * FROM mascotas WHERE activo = true
    List<Mascota> findByActivoTrue();

    // verifica si ya existe una mascota con ese nombre y especie
    // SQL: SELECT COUNT(*) FROM mascotas WHERE nombre = ? AND especie = ?
    boolean existsByNombreAndEspecie(String nombre, String especie);
}