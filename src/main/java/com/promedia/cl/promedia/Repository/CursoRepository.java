package com.promedia.cl.promedia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.promedia.cl.promedia.Model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    Curso findById(Integer id);

    List<Curso> findByNombre(String nombre);

    List<Curso> findBySigla(String sigla);

}
