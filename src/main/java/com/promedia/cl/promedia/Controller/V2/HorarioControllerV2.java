package com.promedia.cl.promedia.Controller.V2;

import com.promedia.cl.promedia.Model.Horario;
import com.promedia.cl.promedia.Service.HorarioService;
import com.promedia.cl.promedia.aseemblers.HorarioModelAssembler;

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
@RequestMapping("/api/v2/horarios")
@Tag(name = "Horarios V2", description = "Opciones disponibles de horarios")
public class HorarioControllerV2 {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private HorarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los horarios", description = "Obten la lista de todos los horarios disponibles")
    public CollectionModel<EntityModel<Horario>> getAllHorarios() {
        List<EntityModel<Horario>> horarios = horarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                horarios,
                linkTo(methodOn(HorarioControllerV2.class).getAllHorarios()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar horario por id", description = "Busca un horario de toda la lista")
    public EntityModel<Horario> getHorarioById(@PathVariable Long id) {
        Horario horario = horarioService.findById(id);
        return assembler.toModel(horario);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un horario", description = "Crea y agrega a la lista un horario")
    public ResponseEntity<EntityModel<Horario>> createHorario(@RequestBody Horario horario) {
        Horario newHorario = horarioService.save(horario);
        return ResponseEntity
                .created(linkTo(methodOn(HorarioControllerV2.class).getHorarioById(Long.valueOf(newHorario.getId()))).toUri())
                .body(assembler.toModel(newHorario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar datos de horario", description = "Cambia los datos de una id de horario")
    public ResponseEntity<EntityModel<Horario>> updateHorario(@PathVariable Long id, @RequestBody Horario horario) {
        horario.setId(id.intValue());
        Horario updated = horarioService.save(horario);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente datos de un horario", description = "Actualiza los datos del horario ingresada")
    public ResponseEntity<EntityModel<Horario>> patchHorario(@PathVariable Long id, @RequestBody Horario horario) {
        Horario updatedHorario = horarioService.patchHorario(id, horario);
        if (updatedHorario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedHorario));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina un horario por id", description = "Elimina por id el horario")
    public ResponseEntity<?> deleteHorario(@PathVariable Long id) {
        horarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
