package com.promedia.cl.promedia.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.promedia.cl.promedia.Model.Estudiante;
import com.promedia.cl.promedia.Repository.EstudianteRepository;
import com.promedia.cl.promedia.Service.EstudianteService;

@SpringBootTest
public class EstudianteServiceTest {

    @Autowired
    private EstudianteService estudianteService;

    @MockBean
    private EstudianteRepository estudianteRepository;
    
    private Estudiante createEstudiante() {
        return new Estudiante(
            1,  
            "Juan", 
            "Perez",
            "Nuñez"
            
        );
    }

    @Test
    public void testFindAll() {
        when(estudianteRepository.findAll()).thenReturn(List.of(createEstudiante()));
        List<Estudiante> estudiantes = estudianteService.findAll();
        assertNotNull(estudiantes);
        assertEquals(1, estudiantes.size());
    }

    @Test
    public void testFindById() {
        when(estudianteRepository.findById(1L)).thenReturn(java.util.Optional.of(createEstudiante()));
        Estudiante estudiante = estudianteService.findById(1L);
        assertNotNull(estudiante);
        assertEquals("Juan", estudiante.getNombre());
    }

    @Test
    public void testSave() {
        Estudiante estudiante = createEstudiante();
        when(estudianteRepository.save(estudiante)).thenReturn(estudiante);
        Estudiante savedEstudiante = estudianteService.save(estudiante);
        assertNotNull(savedEstudiante);
        assertEquals("Juan", savedEstudiante.getNombre());
    }

    @Test
    public void testPatchEstudiante() {
        Estudiante existingEstudiante = createEstudiante();
        Estudiante patchData = new Estudiante();
        patchData.setNombre("Juan Actualizado");

        when(estudianteRepository.findById(1L)).thenReturn(java.util.Optional.of(existingEstudiante));
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(existingEstudiante);

        Estudiante patchedEstudiante = estudianteService.patchEstudiante(1L, patchData);
        assertNotNull(patchedEstudiante);
        assertEquals("Juan Actualizado", patchedEstudiante.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(estudianteRepository).deleteById(1L);
        estudianteService.deleteById(1L);
        verify(estudianteRepository, times(1)).deleteById(1L);
    }
}
