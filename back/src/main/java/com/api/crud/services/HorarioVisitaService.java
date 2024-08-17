package com.api.crud.services;

import com.api.crud.models.HorarioVisitaModel;
import com.api.crud.repository.IHorarioVisitaRepository;
import com.api.crud.repository.IInmuebleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class HorarioVisitaService {
    @Autowired
    IHorarioVisitaRepository horarioVisitaRepository;

    @GetMapping(path = "/obtenerHorariosVisita")
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisita() {
        return (ArrayList<HorarioVisitaModel>) horarioVisitaRepository.findAll();
    }

    //Obtener horarios de visita por id de inmueble
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaPorInmueble(Long id) {
        return horarioVisitaRepository.findByIdInmueble(id);
    }

    // Crear horario de visita
    public HorarioVisitaModel crearHorarioVisita(HorarioVisitaModel horarioVisita) {
        return horarioVisitaRepository.save(horarioVisita);
    }

    public ArrayList<HorarioVisitaModel>  obtenerHorariosVisitaDisponiblesPorIdInmueble(Long idInmueble) {
        ArrayList<HorarioVisitaModel> horariosVisita = horarioVisitaRepository.findByIdInmueble(idInmueble);
        ArrayList<HorarioVisitaModel> horariosVisitaDisponibles = new ArrayList<HorarioVisitaModel>();
        for (HorarioVisitaModel horarioVisita : horariosVisita) {
            if(horarioVisita.getIdVisitante() == -1){
                horariosVisitaDisponibles.add(horarioVisita);
            }
        }
        return horariosVisitaDisponibles;
    }

    @PostMapping(path = "/agendarVisita/{id}/{idVisitante}")
    public HorarioVisitaModel agendarVisita(@PathVariable Long id, @PathVariable Long idVisitante) {
        HorarioVisitaModel horarioVisita = horarioVisitaRepository.findById(id).get();
        horarioVisita.setIdVisitante(idVisitante);
        return horarioVisitaRepository.save(horarioVisita);


    }

    @DeleteMapping(path = "/agendarVisita/{id}")
    public void agendarVisita(@PathVariable Long id) {
        horarioVisitaRepository.deleteById(id);
    }


}
