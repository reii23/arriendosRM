package com.api.crud.repository;

import com.api.crud.models.InmuebleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface IInmuebleRepository extends JpaRepository<InmuebleModel, Long> {
    public ArrayList<InmuebleModel> findByIdUsuario(Long idUsuario);

}
