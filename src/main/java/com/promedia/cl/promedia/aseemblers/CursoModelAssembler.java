package com.promedia.cl.promedia.aseemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.promedia.cl.promedia.Controller.V2.CursoControllerV2;
import com.promedia.cl.promedia.Model.Curso;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>> {

    @Override
    public EntityModel<Curso> toModel(Curso asistencia) {
        return EntityModel.of(asistencia,
                linkTo(methodOn(CursoControllerV2.class).getCursoById(Long.valueOf(asistencia.getId()))).withSelfRel(),
                linkTo(methodOn(CursoControllerV2.class).getAllCursos()).withRel("asistencias"),
                linkTo(methodOn(CursoControllerV2.class).updateCurso(Long.valueOf(asistencia.getId()), asistencia)).withRel("actualizar"),
                linkTo(methodOn(CursoControllerV2.class).deleteCurso(Long.valueOf(asistencia.getId()))).withRel("eliminar"),
                linkTo(methodOn(CursoControllerV2.class).patchCurso(Long.valueOf(asistencia.getId()), asistencia)).withRel("actualizar-parcial")
        );
    }
}
