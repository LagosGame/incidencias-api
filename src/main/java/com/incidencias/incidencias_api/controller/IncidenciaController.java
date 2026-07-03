package com.incidencias.incidencias_api.controller;

import com.incidencias.incidencias_api.dto.IncidenciaDTO;
import com.incidencias.incidencias_api.dto.IncidenciaRequestDTO;
import com.incidencias.incidencias_api.model.Incidencia;
import com.incidencias.incidencias_api.service.IncidenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Incidencias", description = "Gestión de incidencias municipales")
@RestController
@RequestMapping("/incidencias")
@RequiredArgsConstructor
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    @Operation(summary = "Obtener todas las incidencias")
    @GetMapping
    public ResponseEntity<List<IncidenciaDTO>> obtenerTodos(){
        return ResponseEntity.ok(incidenciaService.obtenerTodas());
    }
    @Operation(summary = "Obtener incidencia por id")
    @GetMapping("/{id}")
    public ResponseEntity<IncidenciaDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(incidenciaService.obtenerPorId(id));
    }
    @Operation(summary = "Obtener incidencias por distrito")
    @GetMapping("/distrito/{distrito}")
    public ResponseEntity<List<IncidenciaDTO>> obtenerPorDistrito(@PathVariable String distrito){
        return ResponseEntity.ok(incidenciaService.obtenerPorDistrito(distrito));
    }
    @Operation(summary = "Crear una nueva incidencia", description = "El estado se asigna automáticamente como PENDIENTE")
    @PostMapping
    public ResponseEntity<IncidenciaDTO> crear(@Valid @RequestBody IncidenciaRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(incidenciaService.crear(requestDTO));
    }
    @Operation(summary = "Actualizar una incidencia")
    @PutMapping("/{id}")
    public ResponseEntity<IncidenciaDTO> actualizar(@Valid @RequestBody IncidenciaRequestDTO requestDTO,@PathVariable Long id){
        return ResponseEntity.ok(incidenciaService.actualizar(id,requestDTO));
    }
    @Operation(summary = "Cambiar el estado de una incidencia", description = "Transiciones permitidas: PENDIENTE→EN_REVISION→EN_PROGRESO→RESUELTA/RECHAZADA")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<IncidenciaDTO> cambiarEstado(@PathVariable Long id, @RequestBody CambiarEstadoRequest request) {
        return ResponseEntity.ok(incidenciaService.cambiarEstado(id, request.nuevoEstado()));
    }
    @Operation(summary = "Eliminar una incidencia")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        incidenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public record CambiarEstadoRequest(String nuevoEstado) {}

}
