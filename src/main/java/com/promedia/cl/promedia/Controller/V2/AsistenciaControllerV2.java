package com.promedia.cl.promedia.Controller.V2;

import com.promedia.cl.promedia.Model.Asistencia;
import com.promedia.cl.promedia.Service.AsistenciaService;
import com.promedia.cl.promedia.aseemblers.AsistenciaModelAssembler;

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
@RequestMapping("/api/v2/asistencias")
@Tag(name = "Asistencia V2", description = "Opciones disponibles de asistencia")
public class AsistenciaControllerV2 {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private AsistenciaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las asistencias", description = "Obten la lista de todas las asistencias con sus respectivos ids")
    public CollectionModel<EntityModel<Asistencia>> getAllAsistencias() {
        List<EntityModel<Asistencia>> asistencias = asistenciaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                asistencias,
                linkTo(methodOn(AsistenciaControllerV2.class).getAllAsistencias()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar asistencia por id", description = "Busca una asistencia en especifica por id")
    public EntityModel<Asistencia> getAsistenciaById(@PathVariable Long id) {
        Asistencia asistencia = asistenciaService.findById(id);
        return assembler.toModel(asistencia);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear asistencia", description = "Crea y agrega a la lista de asistencias")
    public ResponseEntity<EntityModel<Asistencia>> createAsistencia(@RequestBody Asistencia asistencia) {
        Asistencia newAsistencia = asistenciaService.save(asistencia);
        return ResponseEntity
                .created(linkTo(methodOn(AsistenciaControllerV2.class).getAsistenciaById(Long.valueOf(newAsistencia.getId()))).toUri())
                .body(assembler.toModel(newAsistencia));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar asistencia por id", description = "Cambia los datos de una id de asistencia")
    public ResponseEntity<EntityModel<Asistencia>> updateAsistencia(@PathVariable Long id, @RequestBody Asistencia asistencia) {
        asistencia.setId(id.intValue());
        Asistencia updated = asistenciaService.save(asistencia);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente datos de una asistencia", description = "Actualiza los datos de la asistencia ingresada")
    public ResponseEntity<EntityModel<Asistencia>> patchAsistencia(@PathVariable Long id, @RequestBody Asistencia asistencia) {
        Asistencia updatedAsistencia = asistenciaService.patchAsistencia(id, asistencia);
        if (updatedAsistencia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedAsistencia));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina una asistencia por id", description = "Elimina por id la asistencia")
    public ResponseEntity<?> deleteAsistencia(@PathVariable Long id) {
        asistenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

