package com.promedia.cl.promedia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.promedia.cl.promedia.Model.Profesor;

@Repository

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    Profesor findById(Integer id);

    List<Profesor> findByNombre(String nombre);

    List<Profesor> findByAsignaturaQueDa(String asignaturaQueDa);
}
