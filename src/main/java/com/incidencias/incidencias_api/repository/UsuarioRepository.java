package com.incidencias.incidencias_api.repository;

import com.incidencias.incidencias_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
