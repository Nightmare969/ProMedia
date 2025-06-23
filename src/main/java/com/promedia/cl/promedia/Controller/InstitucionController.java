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

import com.promedia.cl.promedia.Model.Institucion;
import com.promedia.cl.promedia.Service.InstitucionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/Instituciones")
@Tag(name = "Instituciones", description = "Opciones disponibles de instituciones")
public class InstitucionController {

    @Autowired
    private InstitucionService institucionService;

    @GetMapping
    @Operation(summary = "Obtener todas las asignaturas", description = "Obten la lista de todas las asignaturas disponibles")
    public ResponseEntity<List<Institucion>> listar() {
        List<Institucion> instituciones = institucionService.findAll();
        if (instituciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(instituciones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar institucion por id", description = "Busca una institucion en toda la lista")
    public ResponseEntity<Institucion> buscar(@PathVariable Long id) {
        try {
            Institucion institucion = institucionService.findById(id);
            return ResponseEntity.ok(institucion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea una Institucion", description = "Crea y agrega a la lista una institucion")
    public ResponseEntity<Institucion> guardar(@RequestBody Institucion institucion) {
        Institucion institucionNueva = institucionService.save(institucion);
        return ResponseEntity.status(HttpStatus.CREATED).body(institucionNueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de Institucion", description = "Cambia los datos de una id de institucion")
    public ResponseEntity<Institucion> actualizar(@PathVariable Long id, @RequestBody Institucion institucion) {
        try {
            institucionService.save(institucion);
            return ResponseEntity.ok(institucion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente datos de una institucion", description = "Actualiza los datos de la institucion ingresada")
    public ResponseEntity<Institucion> patchAsignatura(@PathVariable Long id, @RequestBody Institucion partialInstitucion) {
        try {
            Institucion updatedInstitucion = institucionService.patchInstitucion(id, partialInstitucion);
            return ResponseEntity.ok(updatedInstitucion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una institucion por id", description = "Elimina por id la institucion")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            institucionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
