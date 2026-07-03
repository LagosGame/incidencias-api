package com.incidencias.incidencias_api.service;

import com.incidencias.incidencias_api.dto.IncidenciaDTO;
import com.incidencias.incidencias_api.dto.IncidenciaRequestDTO;
import com.incidencias.incidencias_api.exception.IncidenciaNotFoundException;
import com.incidencias.incidencias_api.exception.UsuarioNotFoundException;
import com.incidencias.incidencias_api.model.Incidencia;
import com.incidencias.incidencias_api.repository.IncidenciaRepository;
import com.incidencias.incidencias_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IncidenciaServiceImpl implements IncidenciaService{

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;
    @Override
    public List<IncidenciaDTO> obtenerTodas() {
        return incidenciaRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }
    @Override
    @Cacheable(value = "incidencias",key = "#id")
    public IncidenciaDTO obtenerPorId(Long id) {
        return toDTO(incidenciaRepository.findById(id)
                .orElseThrow(()-> new IncidenciaNotFoundException(id)));

    }
    @Override
    @Cacheable(value = "incidencias-distrito",key = "#distrito")
    public List<IncidenciaDTO> obtenerPorDistrito(String distrito) {
        return incidenciaRepository.findByDistrito(distrito).stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public IncidenciaDTO crear(IncidenciaRequestDTO requestDTO) {
        var usuario = usuarioRepository.findById(requestDTO.usuarioId())
                .orElseThrow(()-> new UsuarioNotFoundException(requestDTO.usuarioId()));
        var inci = Incidencia.builder()
                .titulo(requestDTO.titulo())
                .descripcion(requestDTO.descripcion())
                .distrito(requestDTO.distrito())
                .estado("PENDIENTE")
                .fechaCreacion(LocalDateTime.now())
                .usuario(usuario)
                .build();
        return toDTO(incidenciaRepository.save(inci));
    }

    @Override
    public IncidenciaDTO actualizar(Long id,IncidenciaRequestDTO requestDTO) {
        var existente = incidenciaRepository.findById(id)
                .orElseThrow(()-> new IncidenciaNotFoundException(id));
        existente.setTitulo(requestDTO.titulo());
        existente.setDescripcion(requestDTO.descripcion());
        existente.setDistrito(requestDTO.distrito());
        return toDTO(incidenciaRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        if (!incidenciaRepository.existsById(id)){
            throw new IncidenciaNotFoundException(id);
        }
        incidenciaRepository.deleteById(id);
    }

    @Override
    public IncidenciaDTO cambiarEstado(Long id, String nuevoEstado){
        var existente = incidenciaRepository.findById(id)
                .orElseThrow(()-> new IncidenciaNotFoundException(id));
        existente.cambiarEstado(nuevoEstado);
        return toDTO(incidenciaRepository.save(existente));
    }

    public IncidenciaDTO toDTO(Incidencia incidencia){
        return new IncidenciaDTO(
                incidencia.getId(),
                incidencia.getTitulo(),
                incidencia.getDescripcion(),
                incidencia.getEstado(),
                incidencia.getDistrito(),
                incidencia.getFechaCreacion(),
                incidencia.getUsuario().getNombre()
        );
    }
}
