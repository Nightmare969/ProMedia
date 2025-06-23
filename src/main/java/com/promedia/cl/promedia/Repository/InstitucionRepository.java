package com.promedia.cl.promedia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.promedia.cl.promedia.Model.Institucion;

@Repository
public interface InstitucionRepository extends JpaRepository<Institucion,Long>{

    Institucion findById(Integer id);

    List<Institucion>findByNombre(String nombre);

    List<Institucion>findByDireccion(String direccion);

    

    
}
