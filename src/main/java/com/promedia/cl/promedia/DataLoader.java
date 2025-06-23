package com.promedia.cl.promedia;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.promedia.cl.promedia.Model.Asignatura;
import com.promedia.cl.promedia.Model.Asistencia;
import com.promedia.cl.promedia.Model.Curso;
import com.promedia.cl.promedia.Model.DiaDeClase;
import com.promedia.cl.promedia.Model.Estudiante;
import com.promedia.cl.promedia.Model.Horario;
import com.promedia.cl.promedia.Model.Institucion;
import com.promedia.cl.promedia.Model.Nota;
import com.promedia.cl.promedia.Model.Profesor;
import com.promedia.cl.promedia.Model.Reporte;
import com.promedia.cl.promedia.Repository.AsignaturaRepository;
import com.promedia.cl.promedia.Repository.AsistenciaRepository;
import com.promedia.cl.promedia.Repository.CursoRepository;
import com.promedia.cl.promedia.Repository.DiaDeClaseRepository;
import com.promedia.cl.promedia.Repository.EstudianteRepository;
import com.promedia.cl.promedia.Repository.HorarioRepository;
import com.promedia.cl.promedia.Repository.InstitucionRepository;
import com.promedia.cl.promedia.Repository.NotaRepository;
import com.promedia.cl.promedia.Repository.ProfesorRepository;
import com.promedia.cl.promedia.Repository.ReporteRepository;

import net.datafaker.Faker;


@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private DiaDeClaseRepository diaDeClaseRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private InstitucionRepository institucionRepository;

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private ReporteRepository reporteRepository;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();
        Random random = new Random();
        
        /*Asistencia*/
        for (int i = 0; i < 3; i++) {
            Asistencia asistencia = new Asistencia();
            asistencia.setId(i + 1);
            asistencia.setPorcentajeDeAsistencia(faker.number().numberBetween(70, 100) + "%"
            );
            asistenciaRepository.save(asistencia);
        }

        /*Dia de clases*/
        for (int i = 0; i < 5; i++) {
            DiaDeClase diaDeClase = new DiaDeClase();
            diaDeClase.setNombre(faker.options().option("LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES"));
            diaDeClaseRepository.save(diaDeClase);
        }

        /*Estudiante*/
        for (int i = 0; i < 5; i++) {
            Estudiante estudiante = new Estudiante();
            estudiante.setNombre(faker.name().firstName());
            estudiante.setApepaterno(faker.name().lastName());
            estudiante.setApematerno(faker.name().lastName());
            estudianteRepository.save(estudiante);
        }

        /*Profesor*/
        for (int i = 0; i < 5; i++) {
            Profesor profesor = new Profesor();
            profesor.setNombre(faker.name().firstName());
            profesor.setApaterno(faker.name().lastName());
            profesor.setAmaterno(faker.name().lastName());
            profesor.setAsignaturaQueDa(faker.educator().course());
            profesorRepository.save(profesor);
        }


        List<DiaDeClase> diasDeClase = diaDeClaseRepository.findAll();
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        List<Profesor> profesores = profesorRepository.findAll();
       
        /*Asignatura*/
        for (int i = 0; i < 6; i++) {
            Asignatura asignatura = new Asignatura();
            asignatura.setId(i + 1);
            asignatura.setNombreTipoDeAsignatura(faker.educator().course());
            asignatura.setHoraInicio(LocalTime.of(faker.number().numberBetween(8, 11), 0));
            asignatura.setHoraFin(LocalTime.of(faker.number().numberBetween(12, 17), 0));
            asignatura.setDiaDeClase(diasDeClase.get(random.nextInt(diasDeClase.size())));
            asignatura.setEstudiante(estudiantes.get(random.nextInt(estudiantes.size())));
            asignatura.setProfesor(profesores.get(random.nextInt(profesores.size())));
            asignaturaRepository.save(asignatura);
        }

        /*Curso*/
        for (int i = 0; i < 5; i++) {
            Curso curso = new Curso();
            curso.setNombre(faker.educator().course());
            curso.setSigla(faker.bothify("???###"));
            cursoRepository.save(curso);
        }

        List<Asignatura> asignaturas = asignaturaRepository.findAll();

        /*Horario*/
        for (int i = 0; i < 5; i++) {
            Horario horario = new Horario();
            horario.setDiaDeClase(diasDeClase.get(faker.random().nextInt(diasDeClase.size())));
            horario.setAsignatura(asignaturas.get(faker.random().nextInt(asignaturas.size())));
            horarioRepository.save(horario);
        }

        /*Institucion*/
        for (int i = 0; i < 5; i++) {
            Institucion institucion = new Institucion();
            institucion.setNombre(faker.university().name());
            institucion.setDireccion(faker.address().fullAddress());
            institucion.setTelefono(Integer.parseInt(faker.phoneNumber().subscriberNumber(7)));
            institucionRepository.save(institucion);
        }

        /*Nota*/
        for (int i = 0; i < 5; i++) {
            Nota nota = new Nota();
            nota.setCalificacion(faker.number().numberBetween(10, 100));
            notaRepository.save(nota);
        }

        List<Institucion> instituciones = institucionRepository.findAll();
        List<Curso> cursos = cursoRepository.findAll();
        List<Asistencia> asistencias = asistenciaRepository.findAll();
        List<Nota> notas = notaRepository.findAll();

        /*Reporte*/
        for (int i = 0; i < 5; i++) {
            Reporte reporte = new Reporte();
            reporte.setInstitucion(instituciones.get(faker.random().nextInt(instituciones.size())));
            reporte.setAsignatura(asignaturas.get(faker.random().nextInt(asignaturas.size())));
            reporte.setEstudiante(estudiantes.get(faker.random().nextInt(estudiantes.size())));
            reporte.setCurso(cursos.get(faker.random().nextInt(cursos.size())));
            reporte.setAsistencia(asistencias.get(faker.random().nextInt(asistencias.size())));
            reporte.setNota(notas.get(faker.random().nextInt(notas.size())));
            reporteRepository.save(reporte);
        }
    }
}
