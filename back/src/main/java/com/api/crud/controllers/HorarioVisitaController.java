package com.api.crud.controllers;


import com.api.crud.models.HorarioVisitaModel;
import com.api.crud.services.HorarioVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

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
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaPorInmueble(@PathVariable Long id) {
        return this.horarioVisitaService.obtenerHorariosVisitaPorInmueble(id);
    }

    @GetMapping(path = "/obtenerHorariosVisitaPorUsuario/{id}")
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaPorUsuario(@PathVariable Long id) {
        return this.horarioVisitaService.obtenerHorariosVisitaPorUsuario(id);
    }

    @GetMapping(path = "obtenerHorariosVisitaDisponiblesPorInmueble/{id}")
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaDisponiblesPorIdInmueble(@PathVariable Long id) {
        return this.horarioVisitaService.obtenerHorariosVisitaDisponiblesPorIdInmueble(id);
    }

    @PostMapping(path = "/crearHorarioVisita")
    public ResponseEntity<?> crearHorarioVisita(@RequestBody HorarioVisitaModel horarioVisita) {
        try {
            // Validar los datos de entrada
            if (horarioVisita.getFecha() == null || horarioVisita.getIdInmueble() == null || horarioVisita.getIdUsuario() == null) {
                return ResponseEntity.badRequest().body("Faltan datos necesarios");
            }

            // Crear el horario de visita
            HorarioVisitaModel horarioCreado = this.horarioVisitaService.crearHorarioVisita(horarioVisita);

            // Devolver la respuesta
            return ResponseEntity.ok().body("Horario de visita creado correctamente: " + horarioCreado.toString());
        } catch (Exception e) {
            // Manejar excepciones
            return ResponseEntity.badRequest().body("Error al crear horario de visita: " + e.getMessage());
        }
    }

    @PostMapping(path = "agendarVisita/{id}/{idVisitante}")
    public HorarioVisitaModel agendarVisita(@PathVariable Long id, @PathVariable Long idVisitante) {
        return this.horarioVisitaService.agendarVisita(id, idVisitante);
    }

    @PostMapping(path = "/desagendarVisita/{id}")
    public HorarioVisitaModel desagendarVisita(@PathVariable Long id) {
        return this.horarioVisitaService.desagendarVisita(id);
    }

    @DeleteMapping(path = "/agendarVisita/{id}")
    public void agendarVisita(@PathVariable Long id) {
        this.horarioVisitaService.agendarVisita(id);
    }
}
