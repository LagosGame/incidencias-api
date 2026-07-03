package com.incidencias.incidencias_api.service;

import com.incidencias.incidencias_api.dto.UsuarioDTO;
import com.incidencias.incidencias_api.dto.UsuarioRequestDTO;
import com.incidencias.incidencias_api.exception.UsuarioNotFoundException;
import com.incidencias.incidencias_api.model.Usuario;
import com.incidencias.incidencias_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;
    @Override
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Cacheable(value = "usuarios",key = "#id")
    public UsuarioDTO obtenerPorId(Long id) {
        return toDTO(usuarioRepository.findById(id)
                .orElseThrow(()-> new UsuarioNotFoundException(id)));
    }

    @Override
    public UsuarioDTO crear(UsuarioRequestDTO requestDTO) {
        var usuario = Usuario.builder()
                .nombre(requestDTO.nombre())
                .email(requestDTO.email())
                .rol(requestDTO.rol())
                .build();
        return toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)){
            throw new UsuarioNotFoundException(id);
        }
        usuarioRepository.deleteById(id);
    }
    public UsuarioDTO toDTO(Usuario usuario){
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }
}
