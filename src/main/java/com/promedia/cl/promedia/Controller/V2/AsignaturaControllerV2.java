package com.promedia.cl.promedia.Controller.V2;

import com.promedia.cl.promedia.Model.Asignatura;
import com.promedia.cl.promedia.Service.AsignaturaService;
import com.promedia.cl.promedia.aseemblers.AsignaturaModelAssembler;

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
@RequestMapping("/api/v2/asignaturas")
@Tag(name = "Asignaturas V2", description = "Opciones disponibles para asignatura")
public class AsignaturaControllerV2 {

    @Autowired
    private AsignaturaService asignaturaService;

    @Autowired
    private AsignaturaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las asignaturas", description = "Obten la lista de todas las asignaturas disponibles")
    public CollectionModel<EntityModel<Asignatura>> getAllAsignaturas() {
        List<EntityModel<Asignatura>> asignaturas = asignaturaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                asignaturas,
                linkTo(methodOn(AsignaturaControllerV2.class).getAllAsignaturas()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar asignatura por id", description = "Busca una asignatura de toda la lista")
    public EntityModel<Asignatura> getAsignaturaById(@PathVariable Long id) {
        Asignatura asignatura = asignaturaService.findById(id);
        return assembler.toModel(asignatura);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea una asignatura", description = "Crea y agrega a la lista una asignatura")
    public ResponseEntity<EntityModel<Asignatura>> createAsignatura(@RequestBody Asignatura asignatura) {
        Asignatura newAsignatura = asignaturaService.save(asignatura);
        return ResponseEntity
                .created(linkTo(methodOn(AsignaturaControllerV2.class).getAsignaturaById(Long.valueOf(newAsignatura.getId()))).toUri())
                .body(assembler.toModel(newAsignatura));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar datos de asignatura por id", description = "Actualiza los datos de una id de asignatura")
        public ResponseEntity<EntityModel<Asignatura>> updateAsignatura(@PathVariable Long id, @RequestBody Asignatura asignatura) {
        asignatura.setId(id.intValue());
        Asignatura updated = asignaturaService.save(asignatura);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }


    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente datos de una asignatura", description = "Actualiza los datos de la asignatura ingresada")
    public ResponseEntity<EntityModel<Asignatura>> patchAsignatura(@PathVariable Long id, @RequestBody Asignatura asignatura) {
        Asignatura updatedAsignatura = asignaturaService.patchAsignatura(id, asignatura);
        if (updatedAsignatura == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedAsignatura));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina una asignatura por id", description = "Elimina por id la asignatura")
    public ResponseEntity<?> deleteAsignatura(@PathVariable Long id) {
        asignaturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
