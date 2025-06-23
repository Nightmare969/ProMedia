package com.promedia.cl.promedia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.promedia.cl.promedia.Model.Horario;
import com.promedia.cl.promedia.Repository.HorarioRepository;
import com.promedia.cl.promedia.Service.HorarioService;
import com.promedia.cl.promedia.Model.Asignatura;

@SpringBootTest
public class HorarioServiceTest {

    @Autowired
    private HorarioService horarioService;

    @MockBean
    private HorarioRepository horarioRepository;

    private Horario createHorario() {
        Horario horario = new Horario();
        Asignatura asignatura = new Asignatura();
        asignatura.setNombreTipoDeAsignatura("Matemáticas");
        horario.setAsignatura(asignatura);
        return horario;
    }

    @Test
    public void testFindAll() {
        when(horarioRepository.findAll()).thenReturn(List.of(createHorario()));
        List<Horario> horarios = horarioService.findAll();
        assertNotNull(horarios);
        assertEquals(1, horarios.size());
    }

    @Test
    public void testFindById() {
        when(horarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createHorario()));
        Horario horario = horarioService.findById(1L);
        assertNotNull(horario);
        assertEquals("Matemáticas", horario.getAsignatura().getNombreTipoDeAsignatura());
    }

    @Test
    public void testSave() {
        Horario horario = createHorario();
        when(horarioRepository.save(horario)).thenReturn(horario);
        Horario savedHorario = horarioService.save(horario);
        assertNotNull(savedHorario);
        assertEquals("Matemáticas", savedHorario.getAsignatura().getNombreTipoDeAsignatura());
    }

    @Test
    public void testPatchHorario() {
        Horario existingHorario = createHorario();
        Horario patchData = new Horario();
        
        Asignatura nuevaAsignatura = new Asignatura();
        nuevaAsignatura.setNombreTipoDeAsignatura("Lenguaje");
        patchData.setAsignatura(nuevaAsignatura);

        when(horarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingHorario));
        when(horarioRepository.save(any(Horario.class))).thenReturn(existingHorario);

        Horario patchedHorario = horarioService.patchHorario(1L, patchData);
        assertNotNull(patchedHorario);
        assertEquals("Lenguaje", patchedHorario.getAsignatura().getNombreTipoDeAsignatura());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(horarioRepository).deleteById(1L);
        horarioService.delete(1L);
        verify(horarioRepository, times(1)).deleteById(1L);
    }
}
