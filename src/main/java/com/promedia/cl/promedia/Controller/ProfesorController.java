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

import com.promedia.cl.promedia.Model.Profesor;
import com.promedia.cl.promedia.Service.ProfesorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/profesores")
@Tag(name = "Profesores", description = "Opciones disponibles de profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @GetMapping
    @Operation(summary = "Obtener todos los profesores", description = "Obten la lista de todos los profesores disponibles")
    public ResponseEntity<List<Profesor>> listar() {
        List<Profesor> profesor = profesorService.findAll();
        if (profesor.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(profesor);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar profesor por id", description = "Busca un profesor de toda la lista")
    public ResponseEntity<Profesor> buscar(@PathVariable Long id) {
        try {
            Profesor profesor = profesorService.findById(id);
            return ResponseEntity.ok(profesor);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea un profesor", description = "Crea y agrega a la lista un profesor")
    public ResponseEntity<Profesor> guardar(@RequestBody Profesor profesor) {
        Profesor profesorNueva = profesorService.save(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(profesorNueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de profesor", description = "Cambia los datos de una id de profesor")
    public ResponseEntity<Profesor> actualizar(@PathVariable Long id, @RequestBody Profesor profesor) {
        try {
            profesorService.save(profesor);
            return ResponseEntity.ok(profesor);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente datos de un profesor", description = "Actualiza los datos del profesor ingresado")
    public ResponseEntity<Profesor> patchProfesor(@PathVariable Long id, @RequestBody Profesor partialProfesor) {
        try {
            Profesor updatedProfesor = profesorService.patchProfesor(id, partialProfesor);
            return ResponseEntity.ok(updatedProfesor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un profesor por id", description = "Elimina por id el profesor")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            profesorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
