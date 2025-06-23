package com.promedia.cl.promedia.Controller.V2;

import com.promedia.cl.promedia.Model.Institucion;
import com.promedia.cl.promedia.Service.InstitucionService;
import com.promedia.cl.promedia.aseemblers.InstitucionModelAssembler;

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
@RequestMapping("/api/v2/instituciones")
@Tag(name = "Instituciones V2", description = "Opciones disponibles de instituciones")
public class InstitucionControllerV2 {

    @Autowired
    private InstitucionService institucionService;

    @Autowired
    private InstitucionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las asignaturas", description = "Obten la lista de todas las asignaturas disponibles")
    public CollectionModel<EntityModel<Institucion>> getAllInstituciones() {
        List<EntityModel<Institucion>> instituciones = institucionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                instituciones,
                linkTo(methodOn(InstitucionControllerV2.class).getAllInstituciones()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar institucion por id", description = "Busca una institucion en toda la lista")
    public EntityModel<Institucion> getInstitucionById(@PathVariable Long id) {
        Institucion institucion = institucionService.findById(id);
        return assembler.toModel(institucion);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea una Institucion", description = "Crea y agrega a la lista una institucion")
    public ResponseEntity<EntityModel<Institucion>> createInstitucion(@RequestBody Institucion institucion) {
        Institucion newInstitucion = institucionService.save(institucion);
        return ResponseEntity
                .created(linkTo(methodOn(InstitucionControllerV2.class).getInstitucionById(Long.valueOf(newInstitucion.getId()))).toUri())
                .body(assembler.toModel(newInstitucion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar datos de Institucion", description = "Cambia los datos de una id de institucion")
    public ResponseEntity<EntityModel<Institucion>> updateInstitucion(@PathVariable Long id, @RequestBody Institucion institucion) {
        institucion.setId(id.intValue());
        Institucion updated = institucionService.save(institucion);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente datos de una institucion", description = "Actualiza los datos de la institucion ingresada")
    public ResponseEntity<EntityModel<Institucion>> patchInstitucion(@PathVariable Long id, @RequestBody Institucion institucion) {
        Institucion updatedInstitucion = institucionService.patchInstitucion(id, institucion);
        if (updatedInstitucion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedInstitucion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina una institucion por id", description = "Elimina por id la institucion")
    public ResponseEntity<?> deleteInstitucion(@PathVariable Long id) {
        institucionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
