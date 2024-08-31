package com.api.crud.services;

import com.api.crud.models.MensajeModel;
import com.api.crud.repository.IMensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MensajeService {
    @Autowired
    IMensajeRepository mensajeRepository;

    /**
     * Metodo que se encarga de obtener los mensajes por chat
     * @param idChat id del chat
     * @return lista de mensajes
     */
    public ArrayList<MensajeModel> obtenerMensajesPorChat(Long idChat) {
        return mensajeRepository.findByIdChat(idChat);
    }

    /**
     * Metodo que se encarga de crear un mensaje
     * @param mensaje mensaje a crear
     * @return mensaje creado
     */
    public MensajeModel crearMensaje(MensajeModel mensaje) {
        return mensajeRepository.save(mensaje);
    }

    /**
     * Metodo que se encarga de enviar un mensaje
     * @param mensaje mensaje a enviar
     * @return mensaje enviado
     */
    public MensajeModel enviarMensaje(MensajeModel mensaje) {
        return mensajeRepository.save(mensaje);
    }
}
