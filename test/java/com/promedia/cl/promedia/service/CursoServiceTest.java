package com.promedia.cl.promedia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.promedia.cl.promedia.Model.Curso;
import com.promedia.cl.promedia.Repository.CursoRepository;
import com.promedia.cl.promedia.Service.CursoService;


@SpringBootTest
public class  CursoServiceTest {

    @Autowired
    private CursoService cursoService;

    @MockBean
    private CursoRepository cursoRepository;

    private Curso createCurso() {
        return new Curso(1, "Primero","F");
    }

    @Test
    public void testFindAll() {
        when(cursoRepository.findAll()).thenReturn(List.of(createCurso()));
        List<Curso> curso = cursoService.findAll();
        assertNotNull(curso);
        assertEquals(1, curso.size());
    }

    @Test
    public void testFindById() {
        when(cursoRepository.findById(1L)).thenReturn(java.util.Optional.of(createCurso()));
        Curso curso = cursoService.findById(1L);
        assertNotNull(curso);
        assertEquals("Primero", curso.getNombre());
    }

    @Test
    public void testSave() {
        Curso curso = createCurso();
        when(cursoRepository.save(curso)).thenReturn(curso);
        Curso savedCurso = cursoService.save(curso);
        assertNotNull(savedCurso);
        assertEquals("Primero", savedCurso.getNombre());
    }

    @Test
    public void testPatchCurso() {
        Curso existingCurso = createCurso();
        Curso patchData = new Curso();
        patchData.setNombre("Segundo");

        when(cursoRepository.findById(1L)).thenReturn(java.util.Optional.of(existingCurso));
        when(cursoRepository.save(any(Curso.class))).thenReturn(existingCurso);

        Curso patchedCurso = cursoService.patchCurso(1L, patchData);
        assertNotNull(patchedCurso);
        assertEquals("Segundo", patchedCurso.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(cursoRepository).deleteById(1L);
        cursoService.deleteById(1L);
        verify(cursoRepository, times(1)).deleteById(1L);
    }
}
