package com.promedia.cl.promedia.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promedia.cl.promedia.Model.Estudiante;
import com.promedia.cl.promedia.Repository.EstudianteRepository;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    public Estudiante findById(long id) {
        return estudianteRepository.findById(id).orElse(null);
    }

    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public void deleteById(Long id) {
        estudianteRepository.deleteById(id);
    }

    public Estudiante patchEstudiante(Long id, Estudiante parcialEstudiante) {
        Optional<Estudiante> estudianteOptional = estudianteRepository.findById(id);
        if (estudianteOptional.isPresent()) {

            Estudiante estudianteToUpdate = estudianteOptional.get();

            if (parcialEstudiante.getNombre() != null) {
                estudianteToUpdate.setNombre(parcialEstudiante.getNombre());
            }
            if (parcialEstudiante.getApematerno() != null) {
                estudianteToUpdate.setApepaterno(parcialEstudiante.getApematerno());
            }
            if (parcialEstudiante.getApematerno() != null) {
                estudianteToUpdate.setApematerno(parcialEstudiante.getApematerno());
            }

            return estudianteRepository.save(estudianteToUpdate);
        } else {
            return null;
        }
    }
}
