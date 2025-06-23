package com.promedia.cl.promedia.Repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.promedia.cl.promedia.Model.Asignatura;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

    Asignatura findById(Integer id);

    List<Asignatura> findByHoraInicio(LocalTime horaInicio);

    List<Asignatura> findByProfesorNombre(String profesor);

    @Query("SELECT a FROM Asignatura a WHERE a.horaInicio = :hora")
    List<Asignatura> findAsignaturasPorHoraInicio(@Param("hora") LocalTime hora);

    @Query("SELECT a FROM Asignatura a WHERE a.profesor.nombre = :nombre")
    List<Asignatura> findAsignaturasPorProfesor(@Param("nombre") String nombre);

    @Query("SELECT a FROM Asignatura a WHERE a.curso.id = :cursoId")
    List<Asignatura> findByCursoId(@Param("cursoId") Long cursoId);
    
    @Query("""
        SELECT a
        FROM Asignatura a
        WHERE a.profesor.id = :profesorId
        ORDER BY a.nombreTipoDeAsignatura ASC
        """)
    List<Asignatura> findByProfesorId(@Param("profesorId") Long profesorId);

    
    @Query("""
        SELECT a
        FROM Asignatura a
        JOIN a.estudiante e
        WHERE e.id = :estudianteId
        ORDER BY a.nombreTipoDeAsignatura ASC   
        """)
    List<Asignatura> findByEstudianteId(@Param("estudianteId") Long estudianteId);

    
    @Query("""
        SELECT a
        FROM Asignatura a
        WHERE a.diaDeClase.nombre = :dia
        ORDER BY a.horaInicio ASC
        """)
    List<Asignatura> findByDiaDeClase(@Param("dia") String dia);

    
    Asignatura findByNombreTipoDeAsignatura(String nombreTipoDeAsignatura);

  
    @Query("""
        SELECT a
        FROM Asignatura a
        WHERE a.horaInicio >= :horaInicio AND a.horaFin <= :horaFin
        ORDER BY a.horaInicio ASC
        """)
    List<Asignatura> findByHorarioEntre(@Param("horaInicio") java.time.LocalTime horaInicio, @Param("horaFin") java.time.LocalTime horaFin);

    @Query("""
        SELECT a
        FROM Asignatura a
        JOIN a.curso c
        JOIN a.profesor p
        JOIN a.estudiante e
        JOIN a.diaDeClase d
        WHERE c.nombre = :nombreCurso AND d.nombre = :nombreDia
        ORDER BY a.horaInicio
        """)
    List<Asignatura> findAsignaturasConTodo(@Param("nombreCurso") String nombreCurso, @Param("nombreDia") String nombreDia);

    @Query("""
        SELECT a
        FROM Asignatura a
        JOIN a.profesor p
        JOIN a.curso c
        JOIN a.estudiante e
        JOIN a.diaDeClase d
        WHERE p.id = :profesorId AND c.id = :cursoId
        """)
    List<Asignatura> findByProfesorAndCurso(@Param("profesorId") Long profesorId, @Param("cursoId") Long cursoId);

    @Query("""
        SELECT a
        FROM Asignatura a
        JOIN a.estudiante e
        JOIN a.diaDeClase d
        JOIN a.profesor p
        JOIN a.curso c
        WHERE e.id = :estudianteId AND d.nombre = :dia
        """)
    List<Asignatura> findByEstudianteAndDia(@Param("estudianteId") Long estudianteId, @Param("dia") String dia);

    @Query("""
        SELECT a
        FROM Asignatura a
        JOIN a.curso c
        JOIN a.estudiante e
        JOIN a.diaDeClase d
        JOIN a.profesor p
        WHERE a.horaInicio >= :horaInicio AND a.horaFin <= :horaFin
        """)
    List<Asignatura> findByHorarioConTodo(@Param("horaInicio") LocalTime horaInicio, @Param("horaFin") LocalTime horaFin);




}

// http://localhost:8080/api/v1/asignaturas/por-horario?horaInicio={horaIni}&horaFin={horaFn}
// http://localhost:8080/api/v1/asignaturas/por-dia?dia={Dia}
// http://localhost:8080/api/v1/asignaturas/por-estudiante/{id}
// http://localhost:8080/api/v1/asignaturas/por-profesor/{id}

