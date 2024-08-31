package com.api.crud.repository;


import com.api.crud.models.HorarioVisitaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
/**
 * Esta interfaz se encarga de realizar las consultas a la base de datos
 */
public interface IHorarioVisitaRepository extends JpaRepository<HorarioVisitaModel, Long> {
    /**
     * Metodo que se encarga de buscar los horarios de visita por id de inmueble
     * @param idInmueble id del inmueble
     * @return lista de horarios de visita
     */
    ArrayList<HorarioVisitaModel> findByIdInmueble(Long idInmueble);

    /**
     * Metodo que se encarga de buscar los horarios de visita por id de visitante
     * @param idVisitante id del visitante
     * @return lista de horarios de visita
     */
    ArrayList<HorarioVisitaModel> findByIdVisitante(Long idVisitante);

    /**
     * Metodo que se encarga de buscar los horarios de visita por id de guia
     * @param idGuia id del visitante
     * @return lista de horarios de visita
     */
    ArrayList<HorarioVisitaModel> findByIdUsuario(Long idGuia);
}
