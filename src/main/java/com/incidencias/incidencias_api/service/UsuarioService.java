package com.incidencias.incidencias_api.service;

import com.incidencias.incidencias_api.dto.IncidenciaDTO;
import com.incidencias.incidencias_api.dto.IncidenciaRequestDTO;
import com.incidencias.incidencias_api.dto.UsuarioDTO;
import com.incidencias.incidencias_api.dto.UsuarioRequestDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> obtenerTodos();
    UsuarioDTO obtenerPorId(Long id);
    UsuarioDTO crear(UsuarioRequestDTO requestDTO);
    void eliminar(Long id);
}
