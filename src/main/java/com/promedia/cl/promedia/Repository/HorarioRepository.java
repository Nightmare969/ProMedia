package com.promedia.cl.promedia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.promedia.cl.promedia.Model.Asignatura;
import com.promedia.cl.promedia.Model.DiaDeClase;
import com.promedia.cl.promedia.Model.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    Horario findById(Integer id);

    List<Horario> findByDiaDeClase(DiaDeClase diaDeClase);

    List<Horario> findByAsignatura(Asignatura asignatura);

}
