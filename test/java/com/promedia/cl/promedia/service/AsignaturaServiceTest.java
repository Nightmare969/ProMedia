package com.promedia.cl.promedia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.promedia.cl.promedia.Model.Asignatura;
import com.promedia.cl.promedia.Model.Estudiante;
import com.promedia.cl.promedia.Model.Profesor;
import com.promedia.cl.promedia.Service.AsignaturaService;
import com.promedia.cl.promedia.Repository.AsignaturaRepository;

@SpringBootTest
public class AsignaturaServiceTest {

    @Autowired
    private AsignaturaService asignaturaService;

    @MockBean
    private AsignaturaRepository asignaturaRepository;

    private Asignatura createAsignatura() {
        Asignatura asignatura = new Asignatura();
        asignatura.setId(1);
        asignatura.setNombreTipoDeAsignatura("Matematicas");
        asignatura.setHoraInicio(LocalTime.of(8, 0));
        asignatura.setHoraFin(LocalTime.of(10, 0));
        asignatura.setEstudiante(new Estudiante(1, "Juan", "Perez", "Gonzalez"));
        asignatura.setProfesor(new Profesor(1, "Ana", "Lopez", "Martinez", "Matematicas"));
        asignatura.setDiaDeClase(null);
        return asignatura;
    }

    @Test
    public void testFindAll() {
        when(asignaturaRepository.findAll()).thenReturn(List.of(createAsignatura()));
        List<Asignatura> asignaturas = asignaturaService.findAll();
        assertNotNull(asignaturas);
        assertEquals(1, asignaturas.size());
    }

    @Test
    public void testFindById() {
        when(asignaturaRepository.findById(1L)).thenReturn(java.util.Optional.of(createAsignatura()));
        Asignatura asignatura = asignaturaService.findById(1L);
        assertNotNull(asignatura);
        assertEquals("Matematicas", asignatura.getNombreTipoDeAsignatura());
    }

    @Test
    public void testSave() {
        Asignatura asignatura = createAsignatura();
        when(asignaturaRepository.save(asignatura)).thenReturn(asignatura);
        Asignatura savedAsignatura = asignaturaService.save(asignatura);
        assertNotNull(savedAsignatura);
        assertEquals("Matematicas", savedAsignatura.getNombreTipoDeAsignatura());
    }

    @Test
    public void testPatchAsignatura() {
        Asignatura existingAsignatura = createAsignatura();
        Asignatura patchData = new Asignatura();
        patchData.setNombreTipoDeAsignatura("Lenguaje");

        when(asignaturaRepository.findById(1L)).thenReturn(java.util.Optional.of(existingAsignatura));
        when(asignaturaRepository.save(any(Asignatura.class))).thenReturn(existingAsignatura);

        Asignatura patchedAsignatura = asignaturaService.patchAsignatura(1L, patchData);
        assertNotNull(patchedAsignatura);
        assertEquals("Lenguaje", patchedAsignatura.getNombreTipoDeAsignatura());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(asignaturaRepository).deleteById(1L);
        asignaturaService.delete(1L);
        verify(asignaturaRepository, times(1)).deleteById(1L);
    }
}