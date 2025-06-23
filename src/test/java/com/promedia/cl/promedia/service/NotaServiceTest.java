package com.promedia.cl.promedia.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.promedia.cl.promedia.Model.Nota;
import com.promedia.cl.promedia.Repository.NotaRepository;
import com.promedia.cl.promedia.Service.NotaService;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NotaServiceTest {

    @Autowired
    private NotaService notaService;

    @MockBean
    private NotaRepository notaRepository;

    private Nota createNota() {
        return new Nota(1, 60);
    }


    @Test
    public void testFindAll() {
        when(notaRepository.findAll()).thenReturn(List.of(createNota()));
        List<Nota> notas = notaService.findAll();
        assertNotNull(notas);
        assertEquals(1, notas.size());
    }

    @Test
    public void testFindById() {
        when(notaRepository.findById(1L)).thenReturn(Optional.of(createNota()));
        Nota nota = notaService.findById(1L);
        assertNotNull(nota);
        assertEquals(60, nota.getCalificacion());
    }

    @Test
    public void testSaveNotaValida() {
        Nota nota = createNota();
        when(notaRepository.save(nota)).thenReturn(nota);
        Nota guardada = notaService.save(nota);
        assertNotNull(guardada);
        assertEquals(60, guardada.getCalificacion());
    }

    @Test
    public void testPatchNota() {
        Nota original = createNota();
        Nota nueva = new Nota();
        nueva.setCalificacion(65);

        when(notaRepository.findById(1L)).thenReturn(Optional.of(original));
        when(notaRepository.save(any(Nota.class))).thenReturn(original);

        Nota actualizada = notaService.patchNota(1L, nueva);
        assertNotNull(actualizada);
        assertEquals(65, actualizada.getCalificacion());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(notaRepository).deleteById(1L);
        notaService.delete(1L);
        verify(notaRepository, times(1)).deleteById(1L);
    }
}

