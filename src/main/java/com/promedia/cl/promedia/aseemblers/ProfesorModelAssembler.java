package com.promedia.cl.promedia.aseemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.promedia.cl.promedia.Controller.V2.ProfesorControllerV2;
import com.promedia.cl.promedia.Model.Profesor;

@Component
public class ProfesorModelAssembler implements RepresentationModelAssembler<Profesor, EntityModel<Profesor>> {

    @Override
    public EntityModel<Profesor> toModel(Profesor profesor) {
        return EntityModel.of(profesor,
                linkTo(methodOn(ProfesorControllerV2.class).getProfesorById(Long.valueOf(profesor.getId()))).withSelfRel(),
                linkTo(methodOn(ProfesorControllerV2.class).getAllProfesores()).withRel("profesores"),
                linkTo(methodOn(ProfesorControllerV2.class).updateProfesor(Long.valueOf(profesor.getId()), profesor)).withRel("actualizar"),
                linkTo(methodOn(ProfesorControllerV2.class).deleteProfesor(Long.valueOf(profesor.getId()))).withRel("eliminar"),
                linkTo(methodOn(ProfesorControllerV2.class).patchProfesor(Long.valueOf(profesor.getId()), profesor)).withRel("actualizar-parcial")
        );
    }
}
