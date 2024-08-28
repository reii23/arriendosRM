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

    public ArrayList<MensajeModel> obtenerMensajesPorChat(Long idChat) {
        return mensajeRepository.findByIdChat(idChat);
    }

    public MensajeModel crearMensaje(MensajeModel mensaje) {
        return mensajeRepository.save(mensaje);
    }

    public MensajeModel enviarMensaje(MensajeModel mensaje) {
        return mensajeRepository.save(mensaje);
    }
}
