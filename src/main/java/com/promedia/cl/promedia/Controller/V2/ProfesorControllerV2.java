package com.promedia.cl.promedia.Controller.V2;

import com.promedia.cl.promedia.Model.Profesor;
import com.promedia.cl.promedia.Service.ProfesorService;
import com.promedia.cl.promedia.aseemblers.ProfesorModelAssembler;

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
@RequestMapping("/api/v2/profesores")
@Tag(name = "Profesores V2", description = "Opciones disponibles de profesores")
public class ProfesorControllerV2 {

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProfesorModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los profesores", description = "Obten la lista de todos los profesores disponibles")
    public CollectionModel<EntityModel<Profesor>> getAllProfesores() {
        List<EntityModel<Profesor>> profesores = profesorService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                profesores,
                linkTo(methodOn(ProfesorControllerV2.class).getAllProfesores()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar profesor por id", description = "Busca un profesor de toda la lista")
    public EntityModel<Profesor> getProfesorById(@PathVariable Long id) {
        Profesor profesor = profesorService.findById(id);
        return assembler.toModel(profesor);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un profesor", description = "Crea y agrega a la lista un profesor")
    public ResponseEntity<EntityModel<Profesor>> createProfesor(@RequestBody Profesor profesor) {
        Profesor newProfesor = profesorService.save(profesor);
        return ResponseEntity
                .created(linkTo(methodOn(ProfesorControllerV2.class).getProfesorById(Long.valueOf(newProfesor.getId()))).toUri())
                .body(assembler.toModel(newProfesor));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar datos de profesor", description = "Cambia los datos de una id de profesor")
    public ResponseEntity<EntityModel<Profesor>> updateProfesor(@PathVariable Long id, @RequestBody Profesor profesor) {
        profesor.setId(id.intValue());
        Profesor updated = profesorService.save(profesor);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente datos de un profesor", description = "Actualiza los datos del profesor ingresado")
    public ResponseEntity<EntityModel<Profesor>> patchProfesor(@PathVariable Long id, @RequestBody Profesor profesor) {
        Profesor updatedProfesor = profesorService.patchProfesor(id, profesor);
        if (updatedProfesor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedProfesor));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina un profesor por id", description = "Elimina por id el profesor")
    public ResponseEntity<?> deleteProfesor(@PathVariable Long id) {
        profesorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
