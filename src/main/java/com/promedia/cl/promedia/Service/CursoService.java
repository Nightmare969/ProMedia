package com.promedia.cl.promedia.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promedia.cl.promedia.Model.Asignatura;
import com.promedia.cl.promedia.Model.Curso;
import com.promedia.cl.promedia.Repository.AsignaturaRepository;
import com.promedia.cl.promedia.Repository.CursoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    public List<Curso> findAll(){
        return cursoRepository.findAll();
    }
    public Curso findById(long id){ 
        return cursoRepository.findById(id).orElse(null);
    }
    public Curso save(Curso curso){
        return cursoRepository.save(curso);
    }
    public void deleteById(Long id) {
        List<Asignatura> asignaturas = asignaturaRepository.findByCursoId(id);
        if (!asignaturas.isEmpty()) {
            asignaturaRepository.deleteAll(asignaturas);
        }
        cursoRepository.deleteById(id);
    }
    
    public Curso patchCurso(Long id, Curso parcialCurso) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if (cursoOptional.isPresent()) {

            Curso cursoToUpdate =cursoOptional.get();

            if (parcialCurso.getNombre() != null) {
                cursoToUpdate.setNombre(parcialCurso.getNombre());
            }
            if (parcialCurso.getSigla() != null) {
                cursoToUpdate.setSigla(parcialCurso.getSigla());
            }
            
            return cursoRepository.save(cursoToUpdate);
        } else {
            return null;
        }
    }

    
}
