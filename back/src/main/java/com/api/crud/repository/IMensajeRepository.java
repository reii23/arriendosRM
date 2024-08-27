package com.api.crud.repository;

import com.api.crud.models.MensajeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IMensajeRepository extends JpaRepository<MensajeModel, Long> {
    ArrayList<MensajeModel> findByIdChat(Long idChat);
}
