package com.incidencias.incidencias_api.exception;

public class IncidenciaNotFoundException extends RuntimeException {
    public IncidenciaNotFoundException(Long id) {
        super("Incidencia con id : " + id + " no encontrada.");
    }
}
