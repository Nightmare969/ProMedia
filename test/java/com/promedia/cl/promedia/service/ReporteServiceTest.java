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
    
import com.promedia.cl.promedia.Model.Asignatura;
import com.promedia.cl.promedia.Model.Asistencia;
import com.promedia.cl.promedia.Model.Curso;
import com.promedia.cl.promedia.Model.Estudiante;
import com.promedia.cl.promedia.Model.Institucion;
import com.promedia.cl.promedia.Model.Nota;
import com.promedia.cl.promedia.Model.Reporte;
import com.promedia.cl.promedia.Repository.ReporteRepository;
import com.promedia.cl.promedia.Service.ReporteService;
    
@SpringBootTest
public class ReporteServiceTest {
        
    @Autowired
    private ReporteService reporteService;
    
    @MockBean
    ReporteRepository reporteRepository;
        
    private Reporte createReporte(){
        return new Reporte(
            1,
            new Institucion(),
            new Asignatura(),
            new Estudiante(),
            new Curso(),
            new Asistencia(),
            new Nota()
                
    
        );
    }
    
    @Test
    public void testFindAll(){
        when(reporteRepository.findAll()).thenReturn(List.of(createReporte()));
        List<Reporte> reportes = reporteService.findAll();
        assertNotNull(reportes);
        assertEquals(1,reportes.size());
    }
    
    @Test
    public void testFindById(){
        when(reporteRepository.findById(1L)).thenReturn(java.util.Optional.of(createReporte()));
        Reporte reporte = reporteService.findById(1L);
        assertNotNull(reporte);
        assertEquals(1,reporte.getId());
    }

    @Test
    public void testSave(){
        Reporte reporte= createReporte();
        when(reporteRepository.save(reporte)).thenReturn(reporte);
        Reporte savedReporte = reporteService.save(reporte);
        assertNotNull(savedReporte);
        assertEquals(1,savedReporte.getId());
    
    }
    
    @Test
    public void TestDeleteById(){
        doNothing().when(reporteRepository).deleteById(1L);
        reporteService.deleteById(1L);
        verify(reporteRepository, times(1)).deleteById(1L);
    
    }

    @Test
    public void TestPatchReporte(){
        Reporte existingReporte = createReporte();
        Reporte patchData = new Reporte();
        patchData.setId(001);
    
        when(reporteRepository.findById(1L)).thenReturn(java.util.Optional.of(existingReporte));
        when(reporteRepository.save(any(Reporte.class))).thenReturn(existingReporte);
    
        Reporte patchedReporte = reporteService.patchReporte(1L, patchData);
    assertNotNull(patchedReporte);
        assertEquals(001,patchedReporte.getId());
        
    
    }
    
}

