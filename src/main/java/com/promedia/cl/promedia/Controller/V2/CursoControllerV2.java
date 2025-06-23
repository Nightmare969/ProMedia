package com.promedia.cl.promedia.Controller.V2;

import com.promedia.cl.promedia.Model.Curso;
import com.promedia.cl.promedia.Service.CursoService;
import com.promedia.cl.promedia.aseemblers.CursoModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/cursos")
@Tag(name = "Cursos V2", description = "Opciones disponibles de cursos")
public class CursoControllerV2 {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private CursoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los cursos", description = "Obten la lista de todos los cursos")
    public CollectionModel<EntityModel<Curso>> getAllCursos() {
        List<EntityModel<Curso>> cursos = cursoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                cursos,
                linkTo(methodOn(CursoControllerV2.class).getAllCursos()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar curso por id", description = "Busca un curso de toda la lista")
    public EntityModel<Curso> getCursoById(@PathVariable Long id) {
        Curso curso = cursoService.findById(id);
        return assembler.toModel(curso);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un curso", description = "Crea y agrega a la lista un curso")
    public ResponseEntity<EntityModel<Curso>> createCurso(@RequestBody Curso curso) {
        Curso newCurso = cursoService.save(curso);
        return ResponseEntity
                .created(linkTo(methodOn(CursoControllerV2.class).getCursoById(Long.valueOf(newCurso.getId()))).toUri())
                .body(assembler.toModel(newCurso));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar curso por id", description = "Cambia los datos de una id de cursos")
    public ResponseEntity<EntityModel<Curso>> updateCurso(@PathVariable Long id, @RequestBody Curso curso) {
        curso.setId(id.intValue());
        Curso updated = cursoService.save(curso);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente datos de un curso", description = "Actualiza los datos del curso ingresado")
    public ResponseEntity<EntityModel<Curso>> patchCurso(@PathVariable Long id, @RequestBody Curso curso) {
        Curso updatedCurso = cursoService.patchCurso(id, curso);
        if (updatedCurso == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedCurso));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina un curso por id", description = "Elimina por id el curso")
    public ResponseEntity<?> deleteCurso(@PathVariable Long id) {
        cursoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

