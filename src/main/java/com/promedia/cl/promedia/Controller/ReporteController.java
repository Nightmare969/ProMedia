package com.promedia.cl.promedia.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.promedia.cl.promedia.Model.Reporte;
import com.promedia.cl.promedia.Service.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "Opciones disponibles de reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    @Operation(summary = "Obtener todos los reportes", description = "Obten la lista de todos los reportes disponibles")
    public ResponseEntity<List<Reporte>> listar() {
        List<Reporte> Reportes = reporteService.findAll();
        if (Reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Reportes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reporte por id", description = "Busca un reporte de toda la lista")
    public ResponseEntity<Reporte> buscar(@PathVariable Long id) {
        try {
            Reporte reporte = reporteService.findById(id);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea un reporte", description = "Crea y agrega a la lista un reporte")
    public ResponseEntity<Reporte> guardar(@RequestBody Reporte reporte) {
        Reporte reporteNUevo = reporteService.save(reporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteNUevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de reporte", description = "Cambia los datos de una id de reporte")
    public ResponseEntity<Reporte> actualizar(@PathVariable Long id, @RequestBody Reporte reporte) {
        try {
            reporteService.save(reporte);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente datos de un reporte", description = "Actualiza los datos del reporte ingresada")
    public ResponseEntity<Reporte> patchReporte(@PathVariable Long id, @RequestBody Reporte partialReporte) {
        try {
            Reporte updatedReporte = reporteService.patchReporte(id, partialReporte);
            return ResponseEntity.ok(updatedReporte);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un reporte por id", description = "Elimina por id el reporte")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            reporteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
