package com.incidencias.incidencias_api.dto;

import com.incidencias.incidencias_api.model.Incidencia;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record UsuarioDTO(
      Long id, String nombre, String email, String rol
) {
}
