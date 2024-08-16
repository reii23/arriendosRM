package com.api.crud.services;

import com.api.crud.models.HorarioVisitaModel;
import com.api.crud.repository.IHorarioVisitaRepository;
import com.api.crud.repository.IInmuebleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

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




}
