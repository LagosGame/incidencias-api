package com.incidencias.incidencias_api;

import com.incidencias.incidencias_api.exception.IncidenciaNotFoundException;
import com.incidencias.incidencias_api.model.Incidencia;
import com.incidencias.incidencias_api.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;

public class IncidenciaTest {

    private Usuario usuario;
    private Incidencia incidencia;
    @BeforeEach
    void setUp(){
        usuario = Usuario.builder()
                .id(1L)
                .nombre("Marcos")
                .email("marcos@gmail.com")
                .rol("Gerente")
                .build();
        incidencia = Incidencia.builder()
                .id(1L)
                .titulo("No hay pollo")
                .descripcion("Nop hay pollo en la nevera")
                .fechaCreacion(LocalDateTime.now())
                .distrito("Norte")
                .estado("PENDIENTE")
                .build();
    }

    @Test
    void pendiente_puedePasarA_enRevision(){
        incidencia.cambiarEstado("EN_REVISION");
        assertThat(incidencia.getEstado()).isEqualTo("EN_REVISION");

    }
    @Test
    void pendiente_noPuedePasarDirectamenteA_resuelta(){
        assertThatThrownBy(() -> incidencia.cambiarEstado("RESUELTA"))
                .isInstanceOf(IllegalStateException.class);
    }
    @Test
    void enProgreso_puedePasarA_rechazada(){
        incidencia.setEstado("EN_PROGRESO");
        incidencia.cambiarEstado("RECHAZADA");
        assertThat(incidencia.getEstado()).isEqualTo("RECHAZADA");

    }
}
