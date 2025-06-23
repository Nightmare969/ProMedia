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

import com.promedia.cl.promedia.Model.Curso;
import com.promedia.cl.promedia.Service.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/cursos")
@Tag(name = "Cursos", description = "Opciones disponibles de cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    @Operation(summary = "Obtener todos los cursos", description = "Obten la lista de todos los cursos")
    public ResponseEntity<List<Curso>> listar() {
        List<Curso> cursos = cursoService.findAll();
        if (cursos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por id", description = "Busca un curso de toda la lista")
    public ResponseEntity<Curso> buscar(@PathVariable Long id) {
        try {
            Curso curso = cursoService.findById(id);
            return ResponseEntity.ok(curso);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea un curso", description = "Crea y agrega a la lista un curso")
    public ResponseEntity<Curso> guardar(@RequestBody Curso curso) {
        Curso cursoNuevo = cursoService.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar curso por id", description = "Cambia los datos de una id de cursos")
    public ResponseEntity<Curso> actualizar(@PathVariable Long id, @RequestBody Curso curso) {
        try {
            cursoService.save(curso);
            return ResponseEntity.ok(curso);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente datos de un curso", description = "Actualiza los datos del curso ingresado")
    public ResponseEntity<Curso> patchCurso(@PathVariable Long id, @RequestBody Curso partialCurso) {
        try {
            Curso updatedCurso = cursoService.patchCurso(id, partialCurso);
            return ResponseEntity.ok(updatedCurso);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un curso por id", description = "Elimina por id el curso")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
