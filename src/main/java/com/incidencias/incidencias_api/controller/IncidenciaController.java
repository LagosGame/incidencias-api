package com.incidencias.incidencias_api.controller;

import com.incidencias.incidencias_api.dto.IncidenciaDTO;
import com.incidencias.incidencias_api.dto.IncidenciaRequestDTO;
import com.incidencias.incidencias_api.model.Incidencia;
import com.incidencias.incidencias_api.service.IncidenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/incidencias")
@RequiredArgsConstructor
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    @GetMapping
    public ResponseEntity<List<IncidenciaDTO>> obtenerTodos(){
        return ResponseEntity.ok(incidenciaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidenciaDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(incidenciaService.obtenerPorId(id));
    }
    @GetMapping("/distrito/{distrito}")
    public ResponseEntity<List<IncidenciaDTO>> obtenerPorDistrito(@PathVariable String distrito){
        return ResponseEntity.ok(incidenciaService.obtenerPorDistrito(distrito));
    }
    @PostMapping
    public ResponseEntity<IncidenciaDTO> crear(@Valid @RequestBody IncidenciaRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(incidenciaService.crear(requestDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<IncidenciaDTO> actualizar(@Valid @RequestBody IncidenciaRequestDTO requestDTO,@PathVariable Long id){
        return ResponseEntity.ok(incidenciaService.actualizar(id,requestDTO));
    }
    @PatchMapping("/{id}/estado")
    public ResponseEntity<IncidenciaDTO> cambiarEstado(@PathVariable Long id, @RequestBody CambiarEstadoRequest request) {
        return ResponseEntity.ok(incidenciaService.cambiarEstado(id, request.nuevoEstado()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        incidenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public record CambiarEstadoRequest(String nuevoEstado) {}

}
