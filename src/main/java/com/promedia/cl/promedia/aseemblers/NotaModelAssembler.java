package com.promedia.cl.promedia.aseemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.promedia.cl.promedia.Controller.V2.NotaControllerV2;
import com.promedia.cl.promedia.Model.Nota;

@Component
public class NotaModelAssembler implements RepresentationModelAssembler<Nota, EntityModel<Nota>> {

    @Override
    public EntityModel<Nota> toModel(Nota nota) {
        return EntityModel.of(nota,
                linkTo(methodOn(NotaControllerV2.class).getNotaById(Long.valueOf(nota.getId()))).withSelfRel(),
                linkTo(methodOn(NotaControllerV2.class).getAllNotas()).withRel("notas"),
                linkTo(methodOn(NotaControllerV2.class).updateNota(Long.valueOf(nota.getId()), nota)).withRel("actualizar"),
                linkTo(methodOn(NotaControllerV2.class).deleteNota(Long.valueOf(nota.getId()))).withRel("eliminar"),
                linkTo(methodOn(NotaControllerV2.class).patchNota(Long.valueOf(nota.getId()), nota)).withRel("actualizar-parcial")
        );
    }
}
