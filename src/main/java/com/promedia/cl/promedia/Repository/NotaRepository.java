package com.promedia.cl.promedia.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.promedia.cl.promedia.Model.Nota;

@Repository
public interface NotaRepository extends JpaRepository<Nota,Long>{

    Nota findById(Integer id);

    List<Nota>findByCalificacion(Integer calificacion);

    

    
}