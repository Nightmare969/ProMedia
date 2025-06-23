package com.promedia.cl.promedia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.promedia.cl.promedia.Model.Asistencia;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    Asistencia findById(Integer id);

    List<Asistencia> findByPorcentajeDeAsistencia(String porcentajeDeAsistencia);
}
