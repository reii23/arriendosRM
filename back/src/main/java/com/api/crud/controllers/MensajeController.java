package com.api.crud.controllers;


import com.api.crud.models.MensajeModel;
import com.api.crud.services.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/mensaje")
public class MensajeController {
    @Autowired
    private MensajeService mensajeService;

    @GetMapping(path = "/obtenerMensajesPorChat/{idChat}")
    /**
     * Metodo que se encarga de obtener los mensajes por chat
     * @param idChat id del chat
     * @return lista de mensajes
     */
    public ArrayList<MensajeModel> obtenerMensajesPorChat(@PathVariable("idChat") Long idChat) {
        return this.mensajeService.obtenerMensajesPorChat(idChat);
    }

    @PutMapping(path = "/enviarMensaje/{idChat}/{idUsuario}")
    /**
     * Metodo que se encarga de enviar un mensaje
     * @param mensaje mensaje a enviar
     * @param idChat id del chat
     * @param idUsuario id del usuario
     * @return mensaje enviado
     */
    public MensajeModel enviarMensaje(@RequestBody MensajeModel mensaje, @PathVariable("idChat") Long idChat, @PathVariable("idUsuario") Long idUsuario) {
        mensaje.setIdChat(idChat);
        mensaje.setIdUsuario(idUsuario);
        return this.mensajeService.crearMensaje(mensaje);
    }

}
