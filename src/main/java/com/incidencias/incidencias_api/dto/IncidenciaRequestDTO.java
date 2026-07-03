package com.incidencias.incidencias_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record IncidenciaRequestDTO(
        @NotBlank (message = "El titulo no puede estar vacío")
        @Size(min = 3,max = 100,message = "El titulo no puede ser menor que 3 caracteres o mayor de 100 caracteres")
        String titulo,
        @NotBlank (message = "La descripción no puede estar vacía")
        @Size(min = 3,max = 1000,message = "La descripción no puede ser menor que 3 caracteres o mayor de 100 caracteres")
        String descripcion,
        @NotBlank (message = "El distrito no puede estar vacío")
        @Size(min = 3,max = 100,message = "El distrito no puede ser menor que 3 caracteres o mayor de 100 caracteres")
        String distrito,
        @NotNull(message = "La categoria es obligatoria")
        @Positive(message = "El id debe ser mayor que 0")
        Long usuarioId
) {
}
