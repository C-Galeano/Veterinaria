package com.example.veterinaria.SERVICE;

import com.example.veterinaria.DTO.MascotaRequestDTO;
import com.example.veterinaria.DTO.MascotaResponseDTO;
import com.example.demo.Model.Mascota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MascotaServiceImpl implements MascotaService {

    @PersistenceContext
    private EntityManager em;

    // convierte RequestDTO → Entidad (para guardar en BD)
    private Mascota toEntity(MascotaRequestDTO dto) {
        Mascota m = new Mascota();
        m.setNombre(dto.getNombre());
        m.setEspecie(dto.getEspecie());
        m.setRaza(dto.getRaza());
        m.setFechaNacimiento(dto.getFechaNacimiento());
        m.setPeso(dto.getPeso());
        m.setActivo(true);
        return m;
    }

    // convierte Entidad → ResponseDTO (para devolver al cliente)
    private MascotaResponseDTO toDTO(Mascota m) {
        return new MascotaResponseDTO(
                m.getId(),
                m.getNombre(),
                m.getEspecie(),
                m.getRaza(),
                m.getFechaNacimiento(),
                m.getPeso(),
                m.getActivo()
        );
    }

    @Override
    @Transactional
    public MascotaResponseDTO crear(MascotaRequestDTO dto) {
        // SQL: INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, peso, activo)
        //      VALUES (?, ?, ?, ?, ?, true)
        Mascota mascota = toEntity(dto);
        em.persist(mascota);
        return toDTO(mascota);
    }

    @Override
    public List<MascotaResponseDTO> obtenerTodas() {
        // SQL: SELECT * FROM mascotas
        return em.createQuery("SELECT m FROM Mascota m", Mascota.class)
                .getResultList()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MascotaResponseDTO> obtenerActivas() {
        // SQL: SELECT * FROM mascotas WHERE activo = true
        return em.createQuery(
                        "SELECT m FROM Mascota m WHERE m.activo = true", Mascota.class)
                .getResultList()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MascotaResponseDTO obtenerPorId(Long id) {
        // SQL: SELECT * FROM mascotas WHERE id = ?
        Mascota mascota = em.find(Mascota.class, id);
        if (mascota == null) {
            throw new RuntimeException("No se encontró la mascota con id: " + id);
        }
        return toDTO(mascota);
    }

    @Override
    public List<MascotaResponseDTO> obtenerPorEspecie(String especie) {
        // SQL: SELECT * FROM mascotas WHERE especie = ?
        return em.createQuery(
                        "SELECT m FROM Mascota m WHERE m.especie = :especie", Mascota.class)
                .setParameter("especie", especie)
                .getResultList()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MascotaResponseDTO> buscarPorNombre(String nombre) {
        // SQL: SELECT * FROM mascotas WHERE LOWER(nombre) LIKE LOWER('%?%')
        return em.createQuery(
                        "SELECT m FROM Mascota m WHERE LOWER(m.nombre) LIKE LOWER(:nombre)", Mascota.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MascotaResponseDTO actualizar(Long id, MascotaRequestDTO dto) {
        // SQL: SELECT * FROM mascotas WHERE id = ?
        Mascota existente = em.find(Mascota.class, id);
        if (existente == null) {
            throw new RuntimeException("No se encontró la mascota con id: " + id);
        }

        // actualizamos solo los campos que vienen en el DTO
        existente.setNombre(dto.getNombre());
        existente.setEspecie(dto.getEspecie());
        existente.setRaza(dto.getRaza());
        existente.setFechaNacimiento(dto.getFechaNacimiento());
        existente.setPeso(dto.getPeso());

        // SQL: UPDATE mascotas SET nombre=?, especie=?, raza=?, fecha_nacimiento=?, peso=? WHERE id=?
        return toDTO(em.merge(existente));
    }

    @Override
    @Transactional
    public void desactivar(Long id) {
        // borrado lógico — no elimina el registro, solo lo marca como inactivo
        // SQL: UPDATE mascotas SET activo = false WHERE id = ?
        Mascota mascota = em.find(Mascota.class, id);
        if (mascota == null) {
            throw new RuntimeException("No se encontró la mascota con id: " + id);
        }
        mascota.setActivo(false);
        em.merge(mascota);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        // borrado físico — elimina el registro de la BD
        // SQL: DELETE FROM mascotas WHERE id = ?
        Mascota mascota = em.find(Mascota.class, id);
        if (mascota == null) {
            throw new RuntimeException("No se encontró la mascota con id: " + id);
        }
        em.remove(mascota);
    }
}