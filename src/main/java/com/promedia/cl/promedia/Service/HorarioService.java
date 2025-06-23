package com.promedia.cl.promedia.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promedia.cl.promedia.Model.Horario;
import com.promedia.cl.promedia.Repository.HorarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    public List<Horario> findAll() {
        return horarioRepository.findAll();
    }

    public Horario findById(long id) {
        return horarioRepository.findById(id).orElse(null);
    }

    public Horario save(Horario horario) {
        return horarioRepository.save(horario);
    }

    public void delete(Long id) {
        horarioRepository.deleteById(id);
    }

    public Horario patchHorario(Long id, Horario parcialHorario) {
        Optional<Horario> horarioOptional = horarioRepository.findById(id);
        if (horarioOptional.isPresent()) {

            Horario horarioToUpdate = horarioOptional.get();

            if (parcialHorario.getDiaDeClase() != null) {
                horarioToUpdate.setDiaDeClase(parcialHorario.getDiaDeClase());
            }
            if (parcialHorario.getAsignatura() != null) {
                horarioToUpdate.setAsignatura(parcialHorario.getAsignatura());
            }

            return horarioRepository.save(horarioToUpdate);
        } else {
            return null;
        }
    }
}
