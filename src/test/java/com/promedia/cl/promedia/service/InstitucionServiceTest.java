package com.promedia.cl.promedia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.promedia.cl.promedia.Model.Institucion;
import com.promedia.cl.promedia.Repository.InstitucionRepository;
import com.promedia.cl.promedia.Service.InstitucionService;

@SpringBootTest
public class InstitucionServiceTest {

    @Autowired
    private InstitucionService institucionService;

    @MockBean
    private InstitucionRepository institucionRepository;

    private Institucion createInstitucionDuoc() {
        return new Institucion(1, "Duoc NOC", "Plaza Oeste 123", 123456789);
    }

    private Institucion createInstitucionIncapaz() {
        return new Institucion(2, "INCAPAz", "Av. San Pablo 2000", 987654321);
    }

    @Test
    public void testFindAll() {
        when(institucionRepository.findAll()).thenReturn(List.of(createInstitucionDuoc(), createInstitucionIncapaz()));
        List<Institucion> instituciones = institucionService.findAll();
        assertNotNull(instituciones);
        assertEquals(2, instituciones.size());
    }

    @Test
    public void testFindById() {
        when(institucionRepository.findById(1L)).thenReturn(Optional.of(createInstitucionDuoc()));
        Institucion institucion = institucionService.findById(1L);
        assertNotNull(institucion);
        assertEquals("Duoc NOC", institucion.getNombre());
        assertEquals("Plaza Oeste 123", institucion.getDireccion());
    }

    @Test
    public void testSave() {
        Institucion institucion = createInstitucionIncapaz();
        when(institucionRepository.save(institucion)).thenReturn(institucion);
        Institucion guardada = institucionService.save(institucion);
        assertNotNull(guardada);
        assertEquals("INCAPAz", guardada.getNombre());
        assertEquals("Av. San Pablo 2000", guardada.getDireccion());
    }

    @Test
    public void testPatchInstitucion() {
        Institucion original = createInstitucionDuoc();
        Institucion parcial = new Institucion();
        parcial.setNombre("INCAPAz");
        parcial.setDireccion("Av. San Pablo 2000");
        parcial.setTelefono(987654321);

        when(institucionRepository.findById(1L)).thenReturn(Optional.of(original));
        when(institucionRepository.save(any(Institucion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Institucion actualizada = institucionService.patchInstitucion(1L, parcial);
        assertNotNull(actualizada);
        assertEquals("INCAPAz", actualizada.getNombre());
        assertEquals("Av. San Pablo 2000", actualizada.getDireccion());
        assertEquals(987654321, actualizada.getTelefono());
    }

    @Test
    public void testDelete() {
        doNothing().when(institucionRepository).deleteById(1L);
        institucionService.delete(1L);
        verify(institucionRepository, times(1)).deleteById(1L);
    }
}
