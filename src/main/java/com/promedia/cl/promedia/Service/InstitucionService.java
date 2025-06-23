package com.promedia.cl.promedia.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promedia.cl.promedia.Model.Institucion;
import com.promedia.cl.promedia.Repository.InstitucionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InstitucionService {
    
    @Autowired
    private InstitucionRepository institucionRepository;
    
    public List<Institucion> findAll(){
        return institucionRepository.findAll();
    }
    public Institucion findById(long id){ 
        return institucionRepository.findById(id).orElse(null);
    }
    public Institucion save(Institucion institucion){
        return institucionRepository.save(institucion);
    }
    public void delete(Long id) {
        institucionRepository.deleteById(id);
    }
    public Institucion patchInstitucion(Long id, Institucion parcialInstitucion) {
        Optional<Institucion> institucionOptional = institucionRepository.findById(id);
        if (institucionOptional.isPresent()) {

            Institucion institucionToUpdate =institucionOptional.get();

            if (parcialInstitucion.getNombre() != null) {
                institucionToUpdate.setNombre(parcialInstitucion.getNombre());
            }
            
            if (parcialInstitucion.getDireccion() != null) {
                institucionToUpdate.setDireccion(parcialInstitucion.getDireccion());
            }
            
            if (parcialInstitucion.getTelefono() != 0) {
                institucionToUpdate.setTelefono(parcialInstitucion.getTelefono());
            }
          
            
            return institucionRepository.save(institucionToUpdate);
        } else {
            return null;
        }
    }
}