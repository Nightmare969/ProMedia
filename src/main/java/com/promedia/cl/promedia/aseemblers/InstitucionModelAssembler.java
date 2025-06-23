package com.promedia.cl.promedia.aseemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.promedia.cl.promedia.Controller.V2.InstitucionControllerV2;
import com.promedia.cl.promedia.Model.Institucion;

@Component
public class InstitucionModelAssembler implements RepresentationModelAssembler<Institucion, EntityModel<Institucion>> {

    @Override
    public EntityModel<Institucion> toModel(Institucion institucion) {
        return EntityModel.of(institucion,
                linkTo(methodOn(InstitucionControllerV2.class).getInstitucionById(Long.valueOf(institucion.getId()))).withSelfRel(),
                linkTo(methodOn(InstitucionControllerV2.class).getAllInstituciones()).withRel("instituciones"),
                linkTo(methodOn(InstitucionControllerV2.class).updateInstitucion(Long.valueOf(institucion.getId()), institucion)).withRel("actualizar"),
                linkTo(methodOn(InstitucionControllerV2.class).deleteInstitucion(Long.valueOf(institucion.getId()))).withRel("eliminar"),
                linkTo(methodOn(InstitucionControllerV2.class).patchInstitucion(Long.valueOf(institucion.getId()), institucion)).withRel("actualizar-parcial")
        );
    }
}
