package com.promedia.cl.promedia.Controller.V2;

import com.promedia.cl.promedia.Model.Reporte;
import com.promedia.cl.promedia.Service.ReporteService;
import com.promedia.cl.promedia.aseemblers.ReporteModelAssembler;

import io.swagger.v3.oas.annotations.Operation;

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
@RequestMapping("/api/v2/reportes")
public class ReporteControllerV2 {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporteModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los reportes", description = "Obten la lista de todos los reportes disponibles")
    public CollectionModel<EntityModel<Reporte>> getAllReportes() {
        List<EntityModel<Reporte>> reportes = reporteService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                reportes,
                linkTo(methodOn(ReporteControllerV2.class).getAllReportes()).withSelfRel()
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar reporte por id", description = "Busca un reporte de toda la lista")
    public EntityModel<Reporte> getReporteById(@PathVariable Long id) {
        Reporte reporte = reporteService.findById(id);
        return assembler.toModel(reporte);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un reporte", description = "Crea y agrega a la lista un reporte")
    public ResponseEntity<EntityModel<Reporte>> createReporte(@RequestBody Reporte reporte) {
        Reporte newReporte = reporteService.save(reporte);
        return ResponseEntity
                .created(linkTo(methodOn(ReporteControllerV2.class).getReporteById(Long.valueOf(newReporte.getId()))).toUri())
                .body(assembler.toModel(newReporte));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar datos de reporte", description = "Cambia los datos de una id de reporte")
    public ResponseEntity<EntityModel<Reporte>> updateReporte(@PathVariable Long id, @RequestBody Reporte reporte) {
        reporte.setId(id.intValue());
        Reporte updated = reporteService.save(reporte);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente datos de un reporte", description = "Actualiza los datos del reporte ingresada")
    public ResponseEntity<EntityModel<Reporte>> patchReporte(@PathVariable Long id, @RequestBody Reporte reporte) {
        Reporte updatedReporte = reporteService.patchReporte(id, reporte);
        if (updatedReporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedReporte));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina un reporte por id", description = "Elimina por id el reporte")
    public ResponseEntity<?> deleteReporte(@PathVariable Long id) {
        reporteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
