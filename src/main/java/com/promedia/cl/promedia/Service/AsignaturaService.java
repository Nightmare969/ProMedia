package com.promedia.cl.promedia.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promedia.cl.promedia.Model.Asignatura;
import com.promedia.cl.promedia.Repository.AsignaturaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AsignaturaService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    public List<Asignatura> findAll() {
        return asignaturaRepository.findAll();
    }

    public Asignatura findById(long id) {
        return asignaturaRepository.findById(id).orElse(null);
    }

    public Asignatura save(Asignatura asignatura) {
        return asignaturaRepository.save(asignatura);
    }

    public void delete(Long id) {
        asignaturaRepository.deleteById(id);
    }

    public Asignatura patchAsignatura(Long id, Asignatura parcialAsignatura) {
        Optional<Asignatura> asignaturaOptional = asignaturaRepository.findById(id);
        if (asignaturaOptional.isPresent()) {

            Asignatura asignaturaToUpdate = asignaturaOptional.get();

            if (parcialAsignatura.getNombreTipoDeAsignatura() != null) {
                asignaturaToUpdate.setNombreTipoDeAsignatura(parcialAsignatura.getNombreTipoDeAsignatura());
            }

            if (parcialAsignatura.getDiaDeClase() != null) {
                asignaturaToUpdate.setDiaDeClase(parcialAsignatura.getDiaDeClase());
            }

            if (parcialAsignatura.getEstudiante() != null) {
                asignaturaToUpdate.setEstudiante(parcialAsignatura.getEstudiante());
            }

            if (parcialAsignatura.getProfesor() != null) {
                asignaturaToUpdate.setProfesor(parcialAsignatura.getProfesor());
            }

            if (parcialAsignatura.getHoraInicio() != null) {
                asignaturaToUpdate.setHoraInicio(parcialAsignatura.getHoraInicio());
            }

            if (parcialAsignatura.getHoraFin() != null) {
                asignaturaToUpdate.setHoraFin(parcialAsignatura.getHoraFin());
            }

            return asignaturaRepository.save(asignaturaToUpdate);
        } else {
            return null;
        }
    }
    
    public List<Asignatura> findByProfesorId(Long profesorId) {
        return asignaturaRepository.findByProfesorId(profesorId);
    }

    public List<Asignatura> findByEstudianteId(Long estudianteId) {
        return asignaturaRepository.findByEstudianteId(estudianteId);
    }

    public List<Asignatura> findByDiaDeClase(String dia) {
        return asignaturaRepository.findByDiaDeClase(dia);
    }  

    public List<Asignatura> findByHorarioEntre(LocalTime horaInicio, LocalTime horaFin) {
        return asignaturaRepository.findByHorarioEntre(horaInicio, horaFin);
    }
}
