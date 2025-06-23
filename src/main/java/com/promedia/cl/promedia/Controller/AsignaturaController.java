package com.promedia.cl.promedia.Controller;

import java.time.LocalTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.promedia.cl.promedia.Model.Asignatura;
import com.promedia.cl.promedia.Service.AsignaturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/asignaturas")
@Tag(name = "Asignaturas", description = "Opciones disponibles para asignatura")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;


    @GetMapping
    @Operation(summary = "Obtener todas las asignaturas", description = "Obten la lista de todas las asignaturas disponibles")
    public ResponseEntity<List<Asignatura>> listar() {
        List<Asignatura> asignaturas = asignaturaService.findAll();
        if (asignaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(asignaturas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar asignatura por id", description = "Busca una asignatura de toda la lista")
    public ResponseEntity<Asignatura> buscar(@PathVariable Long id) {
        try {
            Asignatura asignatura = asignaturaService.findById(id);
            return ResponseEntity.ok(asignatura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea una asignatura", description = "Crea y agrega a la lista una asignatura")
    public ResponseEntity<Asignatura> guardar(@RequestBody Asignatura asignatura) {
        Asignatura asignaturaNueva = asignaturaService.save(asignatura);
        return ResponseEntity.status(HttpStatus.CREATED).body(asignaturaNueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de asignatura por id", description = "Actualiza los datos de una id de asignatura")
    public ResponseEntity<Asignatura> actualizar(@PathVariable Long id, @RequestBody Asignatura asignatura) {
        try {
            asignaturaService.save(asignatura);
            return ResponseEntity.ok(asignatura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente datos de una asignatura", description = "Actualiza los datos de la asignatura ingresada")
    public ResponseEntity<Asignatura> patchAsignatura(@PathVariable Long id, @RequestBody Asignatura partialAsignatura) {
        try {
            Asignatura updatedAsignatura = asignaturaService.patchAsignatura(id, partialAsignatura);
            return ResponseEntity.ok(updatedAsignatura);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una asignatura por id", description = "Elimina por id la asignatura")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            asignaturaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    //Operaciones Query
    @GetMapping("/por-profesor/{profesorId}")
    public ResponseEntity<List<Asignatura>> listarPorProfesor(@PathVariable Long profesorId) {
        List<Asignatura> asignaturas = asignaturaService.findByProfesorId(profesorId);
        if (asignaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(asignaturas);
    }

    @GetMapping("/por-estudiante/{estudianteId}")
    public ResponseEntity<List<Asignatura>> listarPorEstudiante(@PathVariable Long estudianteId) {
        List<Asignatura> asignaturas = asignaturaService.findByEstudianteId(estudianteId);
        if (asignaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
    return ResponseEntity.ok(asignaturas);
    }

    @GetMapping("/por-dia")
    public ResponseEntity<List<Asignatura>> getAsignaturasPorDia(@RequestParam String dia) {
        List<Asignatura> asignaturas = asignaturaService.findByDiaDeClase(dia);
        if (asignaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
    return ResponseEntity.ok(asignaturas);
    }

    @GetMapping("/por-horario")
    public ResponseEntity<List<Asignatura>> getAsignaturasPorHorario(
        @RequestParam("horaInicio") String horaInicioStr,
        @RequestParam("horaFin") String horaFinStr) {

        LocalTime horaInicio = LocalTime.parse(horaInicioStr);
        LocalTime horaFin = LocalTime.parse(horaFinStr);

        List<Asignatura> asignaturas = asignaturaService.findByHorarioEntre(horaInicio, horaFin);
        if (asignaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(asignaturas);
    }

}
