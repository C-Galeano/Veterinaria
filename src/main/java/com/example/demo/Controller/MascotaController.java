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

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> crear(@RequestBody MascotaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mascotaService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(mascotaService.obtenerTodas());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<MascotaResponseDTO>> obtenerActivas() {
        return ResponseEntity.ok(mascotaService.obtenerActivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mascotaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<MascotaResponseDTO>> obtenerPorEspecie(@PathVariable String especie) {
        return ResponseEntity.ok(mascotaService.obtenerPorEspecie(especie));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MascotaResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(mascotaService.buscarPorNombre(nombre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> actualizar(@PathVariable Long id,
                                                         @RequestBody MascotaRequestDTO dto) {
        try {
            return ResponseEntity.ok(mascotaService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        try {
            mascotaService.desactivar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

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