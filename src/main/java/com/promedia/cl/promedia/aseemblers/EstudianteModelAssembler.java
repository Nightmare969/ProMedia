package com.promedia.cl.promedia.aseemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.promedia.cl.promedia.Controller.V2.EstudianteControllerV2;
import com.promedia.cl.promedia.Model.Estudiante;

@Component
public class EstudianteModelAssembler implements RepresentationModelAssembler<Estudiante, EntityModel<Estudiante>> {

    @Override
    public EntityModel<Estudiante> toModel(Estudiante estudiante) {
        return EntityModel.of(estudiante,
                linkTo(methodOn(EstudianteControllerV2.class).getEstudianteById(Long.valueOf(estudiante.getId()))).withSelfRel(),
                linkTo(methodOn(EstudianteControllerV2.class).getAllEstudiantes()).withRel("estudiantes"),
                linkTo(methodOn(EstudianteControllerV2.class).updateEstudiante(Long.valueOf(estudiante.getId()), estudiante)).withRel("actualizar"),
                linkTo(methodOn(EstudianteControllerV2.class).deleteEstudiante(Long.valueOf(estudiante.getId()))).withRel("eliminar"),
                linkTo(methodOn(EstudianteControllerV2.class).patchEstudiante(Long.valueOf(estudiante.getId()), estudiante)).withRel("actualizar-parcial")
        );
    }
}
