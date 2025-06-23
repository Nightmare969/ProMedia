package com.promedia.cl.promedia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.promedia.cl.promedia.Model.Asistencia;
import com.promedia.cl.promedia.Repository.AsistenciaRepository;
import com.promedia.cl.promedia.Service.AsistenciaService;

@SpringBootTest
public class AsistenciaServiceTest {

    @Autowired
    private AsistenciaService asistenciaService;

    @MockBean
    private AsistenciaRepository asistenciaRepository;

    private Asistencia createAsistencia() {
        return new Asistencia(
            1,
            "85%"
        );
    }

    @Test
    public void testFindAll() {
        when(asistenciaRepository.findAll()).thenReturn(List.of(createAsistencia()));
        List<Asistencia> asistencias = asistenciaService.findAll();
        assertNotNull(asistencias);
        assertEquals(1, asistencias.size());
    }

    @Test
    public void testFindById() {
        when(asistenciaRepository.findById(1L)).thenReturn(Optional.of(createAsistencia()));
        Asistencia asistencia = asistenciaService.findById(1L);
        assertNotNull(asistencia);
        assertEquals("85%", asistencia.getPorcentajeDeAsistencia());
    }

    @Test
    public void testSave() {
        Asistencia asistencia = createAsistencia();
        when(asistenciaRepository.save(asistencia)).thenReturn(asistencia);
        Asistencia saved = asistenciaService.save(asistencia);
        assertNotNull(saved);
        assertEquals("85%", saved.getPorcentajeDeAsistencia());
    }

    @Test
    public void testPatchAsistencia() {
        Asistencia existing = createAsistencia();
        Asistencia patch = new Asistencia();
        patch.setPorcentajeDeAsistencia("90%");

        when(asistenciaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(asistenciaRepository.save(any(Asistencia.class))).thenReturn(existing);

        Asistencia patched = asistenciaService.patchAsistencia(1L, patch);
        assertNotNull(patched);
        assertEquals("90%", patched.getPorcentajeDeAsistencia());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(asistenciaRepository).deleteById(1L);
        asistenciaService.delete(1L);
        verify(asistenciaRepository, times(1)).deleteById(1L);
    }
}