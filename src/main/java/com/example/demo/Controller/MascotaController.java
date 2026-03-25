package com.example.veterinaria.CONTROLLER;

import com.example.veterinaria.DTO.MascotaRequestDTO;
import com.example.veterinaria.DTO.MascotaResponseDTO;
import com.example.veterinaria.SERVICE.MascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // crear una mascota — recibe RequestDTO, devuelve ResponseDTO
    // SQL: INSERT INTO mascotas (...) VALUES (...)
    @PostMapping
    public ResponseEntity<MascotaResponseDTO> crear(@RequestBody MascotaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mascotaService.crear(dto));
    }

    // traer todas las mascotas
    // SQL: SELECT * FROM mascotas
    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(mascotaService.obtenerTodas());
    }

    // traer solo las mascotas activas
    // SQL: SELECT * FROM mascotas WHERE activo = true
    @GetMapping("/activas")
    public ResponseEntity<List<MascotaResponseDTO>> obtenerActivas() {
        return ResponseEntity.ok(mascotaService.obtenerActivas());
    }

    // buscar mascota por id
    // SQL: SELECT * FROM mascotas WHERE id = ?
    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mascotaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // buscar por especie
    // SQL: SELECT * FROM mascotas WHERE especie = ?
    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<MascotaResponseDTO>> obtenerPorEspecie(@PathVariable String especie) {
        return ResponseEntity.ok(mascotaService.obtenerPorEspecie(especie));
    }

    // buscar por nombre
    // SQL: SELECT * FROM mascotas WHERE LOWER(nombre) LIKE LOWER('%?%')
    @GetMapping("/buscar")
    public ResponseEntity<List<MascotaResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(mascotaService.buscarPorNombre(nombre));
    }

    // actualizar una mascota — recibe RequestDTO, devuelve ResponseDTO
    // SQL: UPDATE mascotas SET ... WHERE id = ?
    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> actualizar(@PathVariable Long id,
                                                         @RequestBody MascotaRequestDTO dto) {
        try {
            return ResponseEntity.ok(mascotaService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // desactivar mascota — borrado lógico, no la elimina de la BD
    // SQL: UPDATE mascotas SET activo = false WHERE id = ?
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        try {
            mascotaService.desactivar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // eliminar mascota — borrado físico, la borra de la BD
    // SQL: DELETE FROM mascotas WHERE id = ?
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            mascotaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}