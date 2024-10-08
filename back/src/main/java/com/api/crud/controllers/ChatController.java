package com.api.crud.controllers;


import com.api.crud.models.ChatModel;
import com.api.crud.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping(path = "/crearChat/{idUsuario1}/{idUsuario2}/{idInmueble}")
    /**
     * Metodo que se encarga de crear un chat
     * @param idUsuario1 id del usuario 1
     * @param idUsuario2 id del usuario 2
     * @param idInmueble id del inmueble
     * @return chat creado
     */
    public ChatModel crearChat(@PathVariable("idUsuario1") Long idUsuario1, @PathVariable("idUsuario2") Long idUsuario2, @PathVariable("idInmueble") Long idInmueble) {
        return this.chatService.crearChat(idUsuario1, idUsuario2, idInmueble);
    }

    @GetMapping(path = "/obtenerChatPorUsuarios/{idUsuario1}/{idUsuario2}")
    /**
     * Metodo que se encarga de obtener un chat por usuarios
     * @param idUsuario1 id del usuario 1
     * @param idUsuario2 id del usuario 2
     * @return chat
     */
    public ChatModel obtenerChatPorUsuarios(@PathVariable("idUsuario1") Long idUsuario1, @PathVariable("idUsuario2") Long idUsuario2) {
        return this.chatService.obtenerChatPorUsuarios(idUsuario1, idUsuario2);
    }

    @GetMapping(path = "/obtenerChatPorIdUsuario1/{idUsuario1}")
    /**
     * Metodo que se encarga de obtener todos los chats de un usuario propietario
     * @param idUsuario1 id del usuario
     * @return lista de chats
     */
    public ArrayList<ChatModel> obtenerChatPorIdUsuario1(@PathVariable("idUsuario1") Long idUsuario1) {
        System.out.println("idUsuario1: " + idUsuario1);
        return this.chatService.obtenerChatPorIdUsuario1(idUsuario1);
    }

    @GetMapping(path = "/obtenerChatPorIdUsuario2/{idUsuario2}")
    /**
     * Metodo que se encarga de obtener todos los chats de un usuario interesado
     * @param idUsuario2 id del usuario
     * @return lista de chats
     */
    public ArrayList<ChatModel> obtenerChatPorIdUsuario2(@PathVariable("idUsuario2") Long idUsuario2) {
        return this.chatService.obtenerChatPorIdUsuario2(idUsuario2);
    }

    @GetMapping(path = "/obtenerChatPorIdUsuario1YIdUsuario2YIdInmueble/{idUsuario1}/{idUsuario2}/{idInmueble}")
    /**
     * Metodo que se encarga de obtener un chat por id de usuario 1, id de usuario 2 e id de inmueble
     * @param idUsuario1 id del usuario 1
     * @param idUsuario2 id del usuario 2
     * @param idInmueble id del inmueble
     * @return chat
     */
    public ArrayList<ChatModel> obtenerChatPorIdUsuario1YIdUsuario2YIdInmueble(@PathVariable("idUsuario1") Long idUsuario1, @PathVariable("idUsuario2") Long idUsuario2, @PathVariable("idInmueble") Long idInmueble) {
        return this.chatService.obtenerChatPorIdUsuario1YIdUsuario2YIdInmueble(idUsuario1, idUsuario2, idInmueble);
    }


}
