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

import com.promedia.cl.promedia.Model.Horario;
import com.promedia.cl.promedia.Service.HorarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/horarios")
@Tag(name = "Horarios", description = "Opciones disponibles de horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping
    @Operation(summary = "Obtener todos los horarios", description = "Obten la lista de todos los horarios disponibles")
    public ResponseEntity<List<Horario>> listar() {
        List<Horario> Horarios = horarioService.findAll();
        if (Horarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Horarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar horario por id", description = "Busca un horario de toda la lista")
    public ResponseEntity<Horario> buscar(@PathVariable Long id) {
        try {
            Horario horario = horarioService.findById(id);
            return ResponseEntity.ok(horario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea un horario", description = "Crea y agrega a la lista un horario")
    public ResponseEntity<Horario> guardar(@RequestBody Horario horario) {
        Horario horarioNuevo = horarioService.save(horario);
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de horario", description = "Cambia los datos de una id de horario")
    public ResponseEntity<Horario> actualizar(@PathVariable Long id, @RequestBody Horario horario) {
        try {
            horarioService.save(horario);
            return ResponseEntity.ok(horario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente datos de un horario", description = "Actualiza los datos del horario ingresada")
    public ResponseEntity<Horario> patchHorario(@PathVariable Long id, @RequestBody Horario partialHorario) {
        try {
            Horario updatedReporte = horarioService.patchHorario(id, partialHorario);
            return ResponseEntity.ok(updatedReporte);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un horario por id", description = "Elimina por id el horario")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            horarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
