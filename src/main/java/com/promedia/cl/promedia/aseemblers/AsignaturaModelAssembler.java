package com.promedia.cl.promedia.aseemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.promedia.cl.promedia.Controller.V2.AsignaturaControllerV2;
import com.promedia.cl.promedia.Model.Asignatura;

@Component
public class AsignaturaModelAssembler implements RepresentationModelAssembler<Asignatura, EntityModel<Asignatura>> {

    @Override
    public EntityModel<Asignatura> toModel(Asignatura asignatura) {
        return EntityModel.of(asignatura,
                linkTo(methodOn(AsignaturaControllerV2.class).getAsignaturaById(Long.valueOf(asignatura.getId()))).withSelfRel(),
                linkTo(methodOn(AsignaturaControllerV2.class).getAllAsignaturas()).withRel("asignaturas"),
                linkTo(methodOn(AsignaturaControllerV2.class).updateAsignatura(Long.valueOf(asignatura.getId()), asignatura)).withRel("actualizar"),
                linkTo(methodOn(AsignaturaControllerV2.class).deleteAsignatura(Long.valueOf(asignatura.getId()))).withRel("eliminar"),
                linkTo(methodOn(AsignaturaControllerV2.class).patchAsignatura(Long.valueOf(asignatura.getId()), asignatura)).withRel("actualizar-parcial")
        );
    }
}
