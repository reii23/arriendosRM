package com.api.crud.controllers;


import com.api.crud.models.HorarioVisitaModel;
import com.api.crud.services.HorarioVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@RestController
@RequestMapping("/horarioVisita")
/**
 * Clase que define el controlador de los horarios de visita
 */
public class HorarioVisitaController {
    @Autowired
    private HorarioVisitaService horarioVisitaService;

    @GetMapping(path = "/obtenerHorariosVisita")
    /**
     * Metodo que se encarga de obtener los horarios de visita
     * @return lista de horarios de visita
     */
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisita() {
        return this.horarioVisitaService.obtenerHorariosVisita();
    }

    @GetMapping(path = "/obtenerHorariosVisitaPorInmueble/{id}")
    /**
     * Metodo que se encarga de obtener los horarios de visita por id de inmueble
     * @param id id del inmueble
     * @return lista de horarios de visita
     */
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaPorInmueble(@PathVariable Long id) {
        return this.horarioVisitaService.obtenerHorariosVisitaPorInmueble(id);
    }

    @GetMapping(path = "/obtenerHorariosVisitaPorUsuarioGuia/{id}")
    /**
     * Metodo que se encarga de obtener los horarios de visita por id de usuario agente inmobiliario
     * @param id id del usuario
     * @return lista de horarios de visita
     */
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaPorUsuarioGuia(@PathVariable Long id) {
        return this.horarioVisitaService.obtenerHorariosVisitaPorUsuarioGuia(id);
    }

    @GetMapping(path = "/obtenerHorariosVisitaPorUsuarioVisitante/{id}")
    /**
     * Metodo que se encarga de obtener los horarios de visita por id de usuario cliente
     * @param id id del usuario
     * @return lista de horarios de visita
     */
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaPorUsuarioVisitante(@PathVariable Long id) {
        return this.horarioVisitaService.obtenerHorariosVisitaPorUsuarioVisitante(id);
    }

    @GetMapping(path = "obtenerHorariosVisitaDisponiblesPorInmueble/{id}")
    /**
     * Metodo que se encarga de obtener los horarios de visita disponibles por id de inmueble
     * @param id id del inmueble
     * @return lista de horarios de visita
     */
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaDisponiblesPorIdInmueble(@PathVariable Long id) {
        return this.horarioVisitaService.obtenerHorariosVisitaDisponiblesPorIdInmueble(id);
    }

    @PostMapping(path = "/crearHorarioVisita")
    /**
     * Metodo que se encarga de crear un horario de visita
     * @param horarioVisita horario de visita
     * @return respuesta
     */
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
    /**
     * Metodo que se encarga de agendar una visita
     * @param id id del horario de visita
     * @param idVisitante id del visitante
     * @return horario de visita
     */
    public HorarioVisitaModel agendarVisita(@PathVariable Long id, @PathVariable Long idVisitante) {
        return this.horarioVisitaService.agendarVisita(id, idVisitante);
    }

    @PostMapping(path = "/desagendarVisita/{id}")
    /**
     * Metodo que se encarga de desagendar una visita
     * @param id id del horario de visita
     * @return horario de visita
     */
    public HorarioVisitaModel desagendarVisita(@PathVariable Long id) {
        return this.horarioVisitaService.desagendarVisita(id);
    }

    @DeleteMapping(path = "/eliminarHorarioVisita/{id}")
    /**
     * Metodo que se encarga de agendar una visita
     * @param id id del horario de visita
     */
    public void agendarVisita(@PathVariable Long id) {
        this.horarioVisitaService.agendarVisita(id);
    }

    /**
     * Metodo que se encarga de obtener los horarios de visita por fecha
     * @param id id del inmueble
     * @param fecha fecha
     * @return lista de horarios de visita
     */
    @GetMapping(path = "/obtenerHorariosVisitaPorFecha/{id}/{fecha}")
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaPorFecha(@PathVariable Long id, @PathVariable String fecha) {
        return this.horarioVisitaService.obtenerHorariosVisitaPorFecha(id, fecha);
    }

}
