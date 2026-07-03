package com.incidencias.incidencias_api.repository;

import com.incidencias.incidencias_api.model.Incidencia;
import com.incidencias.incidencias_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia,Long> {
    List<Incidencia> findByEstado(String estado);
    List<Incidencia> findByDistrito(String distrito);
    List<Incidencia> findByUsuario(Usuario usuario);
}
