package com.promedia.cl.promedia.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promedia.cl.promedia.Model.Profesor;
import com.promedia.cl.promedia.Repository.ProfesorRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    public List<Profesor> findAll(){
        return profesorRepository.findAll();
    }
    public Profesor findById(long id){ 
        return profesorRepository.findById(id).orElse(null);
    }
    public Profesor save(Profesor profesor){
        return profesorRepository.save(profesor);
    }
    public void delete(Long id) {
        profesorRepository.deleteById(id);
    }
    public Profesor patchProfesor(Long id, Profesor parcialProfesor) {
        Optional<Profesor> profesorOptional = profesorRepository.findById(id);
        if (profesorOptional.isPresent()) {

            Profesor asistenciaToUpdate =profesorOptional.get();

            if (parcialProfesor.getNombre() != null) {
                asistenciaToUpdate.setNombre(parcialProfesor.getNombre());
            }
            if (parcialProfesor.getApaterno() != null) {
                asistenciaToUpdate.setApaterno(parcialProfesor.getApaterno());
            }
            if (parcialProfesor.getAmaterno() != null) {
                asistenciaToUpdate.setAmaterno(parcialProfesor.getAmaterno());
            }
            if (parcialProfesor.getAsignaturaQueDa() != null) {
                asistenciaToUpdate.setAsignaturaQueDa(parcialProfesor.getAsignaturaQueDa());
            }
            return profesorRepository.save(asistenciaToUpdate);
        } else {
            return null;
        }
    }
}
