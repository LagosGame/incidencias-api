package com.incidencias.incidencias_api.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="incidencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String distrito;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public void cambiarEstado(String nuevoEstado) {
        if (this.estado.equals("PENDIENTE") && !nuevoEstado.equals("EN_REVISION")) {
            throw new IllegalStateException("Transición no permitida");
        }
        if (this.estado.equals("EN_REVISION") && !nuevoEstado.equals("EN_PROGRESO")) {
            throw new IllegalStateException("Transición no permitida");
        }
        if (this.estado.equals("EN_PROGRESO") && (!nuevoEstado.equals("RESUELTA") && !nuevoEstado.equals("RECHAZADA")) ) {
            throw new IllegalStateException("Transición no permitida");
        }
        this.estado = nuevoEstado;
    }
}
