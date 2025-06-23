package com.promedia.cl.promedia.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promedia.cl.promedia.Model.Reporte;
import com.promedia.cl.promedia.Repository.ReporteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReporteService {
    
    @Autowired
    private ReporteRepository reporteRepository;
    
    public List<Reporte> findAll(){
        return reporteRepository.findAll();
    }
    public Reporte findById(long id){ 
        return reporteRepository.findById(id).orElse(null);
    }
    public Reporte save(Reporte reporte){
        return reporteRepository.save(reporte);
    }
    public void deleteById(Long id) {
        reporteRepository.deleteById(id);
    }
    public Reporte patchReporte(Long id, Reporte parcialReporte) {
        Optional<Reporte> reporteOptional = reporteRepository.findById(id);
        if (reporteOptional.isPresent()) {

            Reporte reporteToUpdate =reporteOptional.get();

            if (parcialReporte.getInstitucion() != null) {
                reporteToUpdate.setInstitucion(parcialReporte.getInstitucion());
            }
            if (parcialReporte.getAsignatura() != null) {
                reporteToUpdate.setAsignatura(parcialReporte.getAsignatura());
            }
            if (parcialReporte.getEstudiante() != null) {
                reporteToUpdate.setEstudiante(parcialReporte.getEstudiante());
            }
            if (parcialReporte.getCurso() != null) {
                reporteToUpdate.setCurso(parcialReporte.getCurso());
            }
            if (parcialReporte.getAsistencia() != null) {
                reporteToUpdate.setAsistencia(parcialReporte.getAsistencia());
            }
            if (parcialReporte.getNota() != null) {
                reporteToUpdate.setNota(parcialReporte.getNota());
            }
            
            return reporteRepository.save(reporteToUpdate);
        } else {
            return null;
        }
    }
}
