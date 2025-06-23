package com.promedia.cl.promedia.aseemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.promedia.cl.promedia.Controller.V2.ReporteControllerV2;
import com.promedia.cl.promedia.Model.Reporte;

@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<Reporte, EntityModel<Reporte>> {

    @Override
    public EntityModel<Reporte> toModel(Reporte reporte) {
        return EntityModel.of(reporte,
                linkTo(methodOn(ReporteControllerV2.class).getReporteById(Long.valueOf(reporte.getId()))).withSelfRel(),
                linkTo(methodOn(ReporteControllerV2.class).getAllReportes()).withRel("reportes"),
                linkTo(methodOn(ReporteControllerV2.class).updateReporte(Long.valueOf(reporte.getId()), reporte)).withRel("actualizar"),
                linkTo(methodOn(ReporteControllerV2.class).deleteReporte(Long.valueOf(reporte.getId()))).withRel("eliminar"),
                linkTo(methodOn(ReporteControllerV2.class).patchReporte(Long.valueOf(reporte.getId()), reporte)).withRel("actualizar-parcial")
        );
    }
}
