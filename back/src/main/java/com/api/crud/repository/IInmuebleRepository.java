package com.api.crud.repository;

import com.api.crud.models.InmuebleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInmuebleRepository extends JpaRepository<InmuebleModel, Long> {

}
