package com.api.crud.repository;


import com.api.crud.models.HorarioVisitaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IHorarioVisitaRepository extends JpaRepository<HorarioVisitaModel, Long> {
    ArrayList<HorarioVisitaModel> findByIdInmueble(Long idInmueble);
}
