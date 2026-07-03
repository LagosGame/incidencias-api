package com.incidencias.incidencias_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank(message = "El nombre no puede estar vacío.")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,
        @NotBlank(message = "El email no puede estar vacío.")
        @Size(min = 3, max = 100, message = "El email debe tener entre 3 y 100 caracteres")
        String email,
        @NotBlank(message = "El rol no puede estar vacío.")
        @Size(min = 3, max = 100, message = "El rol debe tener entre 3 y 100 caracteres")
        String rol

) {
}
