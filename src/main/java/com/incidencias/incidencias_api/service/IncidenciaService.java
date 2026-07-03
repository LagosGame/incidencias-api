package com.incidencias.incidencias_api.service;

import com.incidencias.incidencias_api.dto.IncidenciaDTO;
import com.incidencias.incidencias_api.dto.IncidenciaRequestDTO;
import com.incidencias.incidencias_api.model.Incidencia;

import java.util.List;
import java.util.Optional;

public interface IncidenciaService {
    List<IncidenciaDTO> obtenerTodas();
    IncidenciaDTO obtenerPorId(Long id);
    IncidenciaDTO crear(IncidenciaRequestDTO incidencia);
    IncidenciaDTO actualizar(Long id,IncidenciaRequestDTO incidencia);
    void eliminar(Long id);
    IncidenciaDTO cambiarEstado(Long id, String nuevoEstado);
    List<IncidenciaDTO> obtenerPorDistrito(String distrito);
}
