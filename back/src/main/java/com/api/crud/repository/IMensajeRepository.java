package com.api.crud.repository;

import com.api.crud.models.MensajeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IMensajeRepository extends JpaRepository<MensajeModel, Long> {
    /**
     * Metodo que se encarga de buscar los mensajes por id de chat
     * @param idChat id del chat
     * @return lista de mensajes
     */
    ArrayList<MensajeModel> findByIdChat(Long idChat);
}
