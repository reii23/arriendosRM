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

    @PostMapping(path = "/enviarMensaje")
    /**
     * Metodo que se encarga de enviar un mensaje
     * @param mensaje mensaje a enviar
     * @return mensaje enviado
     */
    public MensajeModel enviarMensaje(@RequestBody MensajeModel mensaje) {
        return this.mensajeService.enviarMensaje(mensaje);
    }


}
