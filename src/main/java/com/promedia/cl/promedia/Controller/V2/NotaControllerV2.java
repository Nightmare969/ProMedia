package com.promedia.cl.promedia.Controller.V2;

import com.promedia.cl.promedia.Model.Nota;
import com.promedia.cl.promedia.Service.NotaService;
import com.promedia.cl.promedia.aseemblers.NotaModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/notas")
@Tag(name = "Notas V2", description = "Opciones disponibles de notas")
public class NotaControllerV2 {

    @Autowired
    private NotaService notaService;

    @Autowired
    private NotaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las notas", description = "Obten la lista de todas las notas disponibles")
    public CollectionModel<EntityModel<Nota>> getAllNotas() {
        List<EntityModel<Nota>> notas = notaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(
                notas,
                linkTo(methodOn(NotaControllerV2.class).getAllNotas()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar nota por id", description = "Busca una nota de toda la lista")
    public EntityModel<Nota> getNotaById(@PathVariable Long id) {
        Nota nota = notaService.findById(id);
        return assembler.toModel(nota);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea una nota", description = "Crea y agrega a la lista una nota")
    public ResponseEntity<EntityModel<Nota>> createNota(@RequestBody Nota nota) {
        Nota newNota = notaService.save(nota);
        return ResponseEntity
                .created(linkTo(methodOn(NotaControllerV2.class).getNotaById(Long.valueOf(newNota.getId()))).toUri())
                .body(assembler.toModel(newNota));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar datos de nota", description = "Cambia los datos de una id de nota")
    public ResponseEntity<EntityModel<Nota>> updateNota(@PathVariable Long id, @RequestBody Nota nota) {
        nota.setId(id.intValue());
        Nota updated = notaService.save(nota);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente datos de una nota", description = "Actualiza los datos de la nota ingresada")
    public ResponseEntity<EntityModel<Nota>> patchNota(@PathVariable Long id, @RequestBody Nota nota) {
        Nota updatedNota = notaService.patchNota(id, nota);
        if (updatedNota == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedNota));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina una nota por id", description = "Elimina por id la nota")
    public ResponseEntity<?> deleteNota(@PathVariable Long id) {
        notaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
