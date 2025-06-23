package com.promedia.cl.promedia.Controller.V2;

import com.promedia.cl.promedia.Model.Estudiante;
import com.promedia.cl.promedia.Service.EstudianteService;
import com.promedia.cl.promedia.aseemblers.EstudianteModelAssembler;

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
@RequestMapping("/api/v2/estudiantes")
@Tag(name = "Estudiantes V2", description = "Opciones disponibles de estudiante")
public class EstudianteControllerV2 {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private EstudianteModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los estudiantes", description = "Obten la lista de todos los estudiantes")
    public CollectionModel<EntityModel<Estudiante>> getAllEstudiantes() {
        List<EntityModel<Estudiante>> estudiantes = estudianteService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                estudiantes,
                linkTo(methodOn(EstudianteControllerV2.class).getAllEstudiantes()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar estudiante por id", description = "Busca un estudiante de toda la lista")
    public EntityModel<Estudiante> getEstudianteById(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.findById(id);
        return assembler.toModel(estudiante);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea datos de un estudiante", description = "Crea y agrega a la lista un estudiante")
    public ResponseEntity<EntityModel<Estudiante>> createEstudiante(@RequestBody Estudiante estudiante) {
        Estudiante newEstudiante = estudianteService.save(estudiante);
        return ResponseEntity
                .created(linkTo(methodOn(EstudianteControllerV2.class).getEstudianteById(Long.valueOf(newEstudiante.getId()))).toUri())
                .body(assembler.toModel(newEstudiante));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar datos de estudiante", description = "Cambia los datos de una id de estudiante")
    public ResponseEntity<EntityModel<Estudiante>> updateEstudiante(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        estudiante.setId(id.intValue());
        Estudiante updated = estudianteService.save(estudiante);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente datos de un estudiante", description = "Actualiza los datos del estudiante ingresada")
    public ResponseEntity<EntityModel<Estudiante>> patchEstudiante(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        Estudiante updatedEstudiante = estudianteService.patchEstudiante(id, estudiante);
        if (updatedEstudiante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedEstudiante));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina un estudiante por id", description = "Elimina por id el estudainte")
    public ResponseEntity<?> deleteEstudiante(@PathVariable Long id) {
        estudianteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
