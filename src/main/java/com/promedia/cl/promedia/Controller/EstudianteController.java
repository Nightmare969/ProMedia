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

import com.promedia.cl.promedia.Model.Estudiante;
import com.promedia.cl.promedia.Service.EstudianteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/estudiantes")
@Tag(name = "Estudiantes", description = "Opciones disponibles de estudiante")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    @Operation(summary = "Obtener todos los estudiantes", description = "Obten la lista de todos los estudiantes")
    public ResponseEntity<List<Estudiante>> listar() {
        List<Estudiante> estudiantes = estudianteService.findAll();
        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar estudiante por id", description = "Busca un estudiante de toda la lista")
    public ResponseEntity<Estudiante> buscar(@PathVariable Long id) {
        try {
            Estudiante estudiante = estudianteService.findById(id);
            return ResponseEntity.ok(estudiante);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea datos de un estudiante", description = "Crea y agrega a la lista un estudiante")
    public ResponseEntity<Estudiante> guardar(@RequestBody Estudiante estudiante) {
        Estudiante estudianteNuevo = estudianteService.save(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de estudiante", description = "Cambia los datos de una id de estudiante")
    public ResponseEntity<Estudiante> actualizar(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        try {
            estudianteService.save(estudiante);
            return ResponseEntity.ok(estudiante);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente datos de un estudiante", description = "Actualiza los datos del estudiante ingresada")
    public ResponseEntity<Estudiante> patchEstudiante(@PathVariable Long id, @RequestBody Estudiante partialEstudiante) {
        try {
            Estudiante updatedCurso = estudianteService.patchEstudiante(id, partialEstudiante);
            return ResponseEntity.ok(updatedCurso);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un estudiante por id", description = "Elimina por id el estudainte")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            estudianteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
