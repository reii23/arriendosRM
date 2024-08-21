package com.api.crud.services;

import com.api.crud.models.HorarioVisitaModel;
import com.api.crud.repository.IHorarioVisitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
/**
 * Clase que define los servicios de los horarios de visita
 */
public class HorarioVisitaService {
    @Autowired
    IHorarioVisitaRepository horarioVisitaRepository;

    /**
     * Metodo que se encarga de obtener los horarios de visita
     * @return lista de horarios de visita
     */
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisita() {
        return (ArrayList<HorarioVisitaModel>) horarioVisitaRepository.findAll();
    }

    /**
     * Metodo que se encarga de obtener los horarios de visita por id de inmueble
     * @param id id del inmueble
     * @return lista de horarios de visita
     */
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaPorInmueble(Long id) {
        return horarioVisitaRepository.findByIdInmueble(id);
    }

    /**
     * Metodo que se encarga de obtener los horarios de visita por id de usuario
     * @param id id del usuario
     * @return lista de horarios de visita
     */
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaPorUsuario(Long id) {
        return horarioVisitaRepository.findByIdVisitante(id);
    }

    /**
     * Metodo que se encarga de crear un horario de visita
     * @param horarioVisita horario de visita a crear
     * @return horario de visita creado
     */
    public HorarioVisitaModel crearHorarioVisita(HorarioVisitaModel horarioVisita) {
        return horarioVisitaRepository.save(horarioVisita);
    }

    /**
     * Metodo que se encarga de obtener los horarios de visita disponibles por id de inmueble
     * @param idInmueble id del inmueble
     * @return lista de horarios de visita
     */
    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaDisponiblesPorIdInmueble(Long idInmueble) {
        ArrayList<HorarioVisitaModel> horariosVisita = horarioVisitaRepository.findByIdInmueble(idInmueble);
        ArrayList<HorarioVisitaModel> horariosVisitaDisponibles = new ArrayList<>();
        for (HorarioVisitaModel horarioVisita : horariosVisita) {
            if(horarioVisita.getIdVisitante() == -1){
                horariosVisitaDisponibles.add(horarioVisita);
            }
        }
        return horariosVisitaDisponibles;
    }

    /**
     * Metodo que se encarga de agendar una visita
     * @param id id del horario de visita
     * @param idVisitante id del visitante
     * @return horario de visita
     */
    public HorarioVisitaModel agendarVisita(Long id, Long idVisitante) {
        HorarioVisitaModel horarioVisita = horarioVisitaRepository.findById(id).get();
        horarioVisita.setIdVisitante(idVisitante);
        return horarioVisitaRepository.save(horarioVisita);


    }

    /**
     * Metodo que se encarga de desagendar una visita
     * @param id id del horario de visita
     * @return horario de visita
     */
    public HorarioVisitaModel desagendarVisita(Long id) {
        HorarioVisitaModel horarioVisita = horarioVisitaRepository.findById(id).get();
        horarioVisita.setIdVisitante(-1L);
        return horarioVisitaRepository.save(horarioVisita);
    }

    /**
     * Metodo que se encarga de eliminar un horario de visita
     * @param id id del horario de visita
     */
    public void agendarVisita(Long id) {
        horarioVisitaRepository.deleteById(id);
    }


    public ArrayList<HorarioVisitaModel> obtenerHorariosVisitaPorFecha(Long idInmueble, String fecha) {
        // Convertir el año a los dos últimos dígitos para hacer coincidir el formato
        String fechaFormateadaM = fecha.substring(0, 2) + fecha.substring(3, 5) + fecha.substring(6, 10) + "m";
        String fechaFormateadaT = fecha.substring(0, 2) + fecha.substring(3, 5) + fecha.substring(6, 10) + "t";
        String fechaFormateadaN = fecha.substring(0, 2) + fecha.substring(3, 5) + fecha.substring(6, 10) + "n";

        System.out.println("Buscando horarios para las fechas: " + fechaFormateadaM + ", " + fechaFormateadaT + ", " + fechaFormateadaN);

        ArrayList<HorarioVisitaModel> horariosVisita = horarioVisitaRepository.findByIdInmueble(idInmueble);
        ArrayList<HorarioVisitaModel> horariosVisitaPorFecha = new ArrayList<>();

        for (HorarioVisitaModel horarioVisita : horariosVisita) {
            System.out.println("Comparando con: " + horarioVisita.getFecha());
            if (horarioVisita.getFecha().equals(fechaFormateadaM) ||
                    horarioVisita.getFecha().equals(fechaFormateadaT) ||
                    horarioVisita.getFecha().equals(fechaFormateadaN)) {
                horariosVisitaPorFecha.add(horarioVisita);
            }
        }

        System.out.println("Horarios encontrados: " + horariosVisitaPorFecha);

        return horariosVisitaPorFecha;
    }
}

