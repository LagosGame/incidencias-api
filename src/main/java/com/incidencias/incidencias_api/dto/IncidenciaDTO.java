package com.incidencias.incidencias_api.dto;

import java.time.LocalDateTime;

public record IncidenciaDTO(
        Long id,
        String titulo,
        String descripcion,
        String estado,
        String distrito,
        LocalDateTime fechaCreacion,
        String usuarioNombre

) {
}
