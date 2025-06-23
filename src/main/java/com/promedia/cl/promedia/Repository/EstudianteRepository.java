package com.promedia.cl.promedia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.promedia.cl.promedia.Model.Estudiante;

@Repository

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Estudiante findById(Integer id);

    List<Estudiante> findByNombre(String nombre);

    List<Estudiante> findByApepaterno(String Apepaterno);
}
