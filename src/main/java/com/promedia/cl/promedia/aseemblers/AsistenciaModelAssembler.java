package com.promedia.cl.promedia.aseemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.promedia.cl.promedia.Controller.V2.AsistenciaControllerV2;
import com.promedia.cl.promedia.Model.Asistencia;

@Component
public class AsistenciaModelAssembler implements RepresentationModelAssembler<Asistencia, EntityModel<Asistencia>> {

    @Override
    public EntityModel<Asistencia> toModel(Asistencia asistencia) {
        return EntityModel.of(asistencia,
                linkTo(methodOn(AsistenciaControllerV2.class).getAsistenciaById(Long.valueOf(asistencia.getId()))).withSelfRel(),
                linkTo(methodOn(AsistenciaControllerV2.class).getAllAsistencias()).withRel("asistencias"),
                linkTo(methodOn(AsistenciaControllerV2.class).updateAsistencia(Long.valueOf(asistencia.getId()), asistencia)).withRel("actualizar"),
                linkTo(methodOn(AsistenciaControllerV2.class).deleteAsistencia(Long.valueOf(asistencia.getId()))).withRel("eliminar"),
                linkTo(methodOn(AsistenciaControllerV2.class).patchAsistencia(Long.valueOf(asistencia.getId()), asistencia)).withRel("actualizar-parcial")
        );
    }
}

