package com.promedia.cl.promedia.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promedia.cl.promedia.Model.Asistencia;
import com.promedia.cl.promedia.Service.AsistenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/asistencias")
@Tag(name = "Asistencia", description = "Opciones disponibles de asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping
    @Operation(summary = "Obtener todas las asistencias", description = "Obten la lista de todas las asistencias con sus respectivos ids")
    public ResponseEntity<List<Asistencia>> listar() {
        List<Asistencia> asistencias = asistenciaService.findAll();
        if (asistencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar asistencia por id", description = "Busca una asistencia en especifica por id")
    public ResponseEntity<Asistencia> buscar(@PathVariable Long id) {
        try {
            Asistencia asistencia = asistenciaService.findById(id);
            return ResponseEntity.ok(asistencia);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear asistencia", description = "Crea y agrega a la lista de asistencias")
    public ResponseEntity<Asistencia> guardar(@RequestBody Asistencia asistencia) {
        Asistencia asistenciaNueva = asistenciaService.save(asistencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(asistenciaNueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar asistencia por id", description = "Cambia los datos de una id de asistencia")
    public ResponseEntity<Asistencia> actualizar(@PathVariable Long id, @RequestBody Asistencia asistencia) {
        try {
            asistenciaService.save(asistencia);
            return ResponseEntity.ok(asistencia);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente datos de una asistencia", description = "Actualiza los datos de la asistencia ingresada")
    public ResponseEntity<Asistencia> patchAsistencia(@PathVariable Long id, @RequestBody Asistencia partialAsistencia) {
        try {
            Asistencia updatedAsistencia = asistenciaService.patchAsistencia(id, partialAsistencia);
            return ResponseEntity.ok(updatedAsistencia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una asistencia por id", description = "Elimina por id la asistencia")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            asistenciaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
