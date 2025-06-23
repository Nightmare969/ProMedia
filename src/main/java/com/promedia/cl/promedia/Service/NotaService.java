package com.promedia.cl.promedia.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promedia.cl.promedia.Model.Nota;
import com.promedia.cl.promedia.Repository.NotaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NotaService {
    
    @Autowired
    private NotaRepository notaRepository;
    
    public List<Nota> findAll(){
        return notaRepository.findAll();
    }
    public Nota findById(long id){ 
        return notaRepository.findById(id).orElse(null);
    }
    public Nota save(Nota nota){
        return notaRepository.save(nota);
    }
    public void delete(Long id) {
        notaRepository.deleteById(id);
    }
    public Nota patchNota(Long id, Nota parcialNota) {
        Optional<Nota> notaOptional = notaRepository.findById(id);
        if (notaOptional.isPresent()) {

            Nota notaToUpdate =notaOptional.get();

            if (parcialNota.getCalificacion() != null) {
                notaToUpdate.setCalificacion(parcialNota.getCalificacion());
            }
            
            
            return notaRepository.save(notaToUpdate);
        } else {
            return null;
        }
    }
}
