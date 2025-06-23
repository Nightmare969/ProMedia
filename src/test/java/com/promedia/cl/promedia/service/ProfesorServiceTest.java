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

import com.promedia.cl.promedia.Model.Profesor;
import com.promedia.cl.promedia.Repository.ProfesorRepository;
import com.promedia.cl.promedia.Service.ProfesorService;

@SpringBootTest
public class ProfesorServiceTest {

    @Autowired
    private ProfesorService profesorService;

    @MockBean
    private ProfesorRepository profesorRepository;
    
    private Profesor createProfesor() {
        return new Profesor(
            1,  
            "Juan", 
            "Perez",
            "Nuñez",
            "Lenguaje"
            
        );
    }

    @Test
    public void testFindAll() {
        when(profesorRepository.findAll()).thenReturn(List.of(createProfesor()));
        List<Profesor> profesor = profesorService.findAll();
        assertNotNull(profesor);
        assertEquals(1, profesor.size());
    }

    @Test
    public void testFindById() {
        when(profesorRepository.findById(1L)).thenReturn(java.util.Optional.of(createProfesor()));
        Profesor profesor = profesorService.findById(1L);
        assertNotNull(profesor);
        assertEquals("Juan", profesor.getNombre());
    }

    @Test
    public void testSave() {
        Profesor profesor = createProfesor();
        when(profesorRepository.save(profesor)).thenReturn(profesor);
        Profesor savedProfesor = profesorService.save(profesor);
        assertNotNull(savedProfesor);
        assertEquals("Juan", savedProfesor.getNombre());
    }

    @Test
    public void testPatchProfesor() {
        Profesor existingProfesor = createProfesor();
        Profesor patchData = new Profesor();
        patchData.setNombre("Juan Actualizado");

        when(profesorRepository.findById(1L)).thenReturn(java.util.Optional.of(existingProfesor));
        when(profesorRepository.save(any(Profesor.class))).thenReturn(existingProfesor);

        Profesor patchedProfesor = profesorService.patchProfesor(1L, patchData);
        assertNotNull(patchedProfesor);
        assertEquals("Juan Actualizado", patchedProfesor.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(profesorRepository).deleteById(1L);
        profesorService.delete(1L);
        verify(profesorRepository, times(1)).deleteById(1L);
    }
}