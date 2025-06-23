package com.promedia.cl.promedia.aseemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.promedia.cl.promedia.Controller.V2.HorarioControllerV2;
import com.promedia.cl.promedia.Model.Horario;

@Component
public class HorarioModelAssembler implements RepresentationModelAssembler<Horario, EntityModel<Horario>> {

    @Override
    public EntityModel<Horario> toModel(Horario horario) {
        return EntityModel.of(horario,
                linkTo(methodOn(HorarioControllerV2.class).getHorarioById(Long.valueOf(horario.getId()))).withSelfRel(),
                linkTo(methodOn(HorarioControllerV2.class).getAllHorarios()).withRel("horarios"),
                linkTo(methodOn(HorarioControllerV2.class).updateHorario(Long.valueOf(horario.getId()), horario)).withRel("actualizar"),
                linkTo(methodOn(HorarioControllerV2.class).deleteHorario(Long.valueOf(horario.getId()))).withRel("eliminar"),
                linkTo(methodOn(HorarioControllerV2.class).patchHorario(Long.valueOf(horario.getId()), horario)).withRel("actualizar-parcial")
        );
    }
}

