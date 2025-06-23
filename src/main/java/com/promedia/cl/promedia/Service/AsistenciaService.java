package com.promedia.cl.promedia.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promedia.cl.promedia.Model.Asistencia;
import com.promedia.cl.promedia.Repository.AsistenciaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public List<Asistencia> findAll(){
        return asistenciaRepository.findAll();
    }
    public Asistencia findById(long id){ 
        return asistenciaRepository.findById(id).orElse(null);
    }
    public Asistencia save(Asistencia asistencia){
        return asistenciaRepository.save(asistencia);
    }
    public void delete(Long id) {
        asistenciaRepository.deleteById(id);
    }
    public Asistencia patchAsistencia(Long id, Asistencia parcialAsistencia) {
        Optional<Asistencia> asistenciaOptional = asistenciaRepository.findById(id);
        if (asistenciaOptional.isPresent()) {

            Asistencia asistenciaToUpdate =asistenciaOptional.get();

            if (parcialAsistencia.getPorcentajeDeAsistencia() != null) {
                asistenciaToUpdate.setPorcentajeDeAsistencia(parcialAsistencia.getPorcentajeDeAsistencia());
            }
            
            return asistenciaRepository.save(asistenciaToUpdate);
        } else {
            return null;
        }
    }
}
