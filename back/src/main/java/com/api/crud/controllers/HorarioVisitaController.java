package com.api.crud.controllers;


import com.api.crud.models.HorarioVisitaModel;
import com.api.crud.services.HorarioVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/horarioVisita")
public class HorarioVisitaController {
    @Autowired
    private HorarioVisitaService horarioVisitaService;

    @GetMapping(path = "/obtenerHorariosVisita")
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisita() {
        return this.horarioVisitaService.obtenerHorariosVisita();
    }

    @GetMapping(path = "/obtenerHorariosVisitaPorInmueble/{id}")
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaPorInmueble(Long id) {
        return this.horarioVisitaService.obtenerHorariosVisitaPorInmueble(id);
    }
}
