package com.promedia.cl.promedia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.promedia.cl.promedia.Model.Curso;
import com.promedia.cl.promedia.Model.Estudiante;
import com.promedia.cl.promedia.Model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    Reporte findById(Integer id);

    List<Reporte> findByEstudiante(Estudiante estudiante);

    List<Reporte> findByCurso(Curso curso);

}
