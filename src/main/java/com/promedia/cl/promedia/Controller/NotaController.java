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

import com.promedia.cl.promedia.Model.Nota;
import com.promedia.cl.promedia.Service.NotaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/notas")
@Tag(name = "Notas", description = "Opciones disponibles de notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @GetMapping
    @Operation(summary = "Obtener todas las notas", description = "Obten la lista de todas las notas disponibles")
    public ResponseEntity<List<Nota>> listar() {
        List<Nota> notas = notaService.findAll();
        if (notas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar nota por id", description = "Busca una nota de toda la lista")
    public ResponseEntity<Nota> buscar(@PathVariable Long id) {
        try {
            Nota nota = notaService.findById(id);
            return ResponseEntity.ok(nota);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea una nota", description = "Crea y agrega a la lista una nota")
    public ResponseEntity<Nota> guardar(@RequestBody Nota nota) {
        Nota notaNueva = notaService.save(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(notaNueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de nota", description = "Cambia los datos de una id de nota")
    public ResponseEntity<Nota> actualizar(@PathVariable Long id, @RequestBody Nota nota) {
        try {
            notaService.save(nota);
            return ResponseEntity.ok(nota);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente datos de una nota", description = "Actualiza los datos de la nota ingresada")
    public ResponseEntity<Nota> patchNota(@PathVariable Long id, @RequestBody Nota partialNota) {
        try {
            Nota updatedNota = notaService.patchNota(id, partialNota);
            return ResponseEntity.ok(updatedNota);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una nota por id", description = "Elimina por id la nota")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            notaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
