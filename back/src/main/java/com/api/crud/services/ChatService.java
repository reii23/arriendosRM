package com.api.crud.services;

import com.api.crud.models.ChatModel;
import com.api.crud.repository.IChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatService {
    @Autowired
    IChatRepository chatRepository;

    /**
     * Metodo que se encarga de crear un chat
     * @param idUsuario1 id del usuario 1
     * @param idUsuario2 id del usuario 2
     * @param idInmueble id del inmueble
     * @return chat creado
     */
    public ChatModel crearChat(Long idUsuario1, Long idUsuario2, Long idInmueble) {
        ChatModel chat = new ChatModel();
        chat.setIdUsuario1(idUsuario1);
        chat.setIdUsuario2(idUsuario2);
        chat.setIdInmueble(idInmueble);
        return chatRepository.save(chat);
    }

    /**
     * Metodo que se encarga de obtener un chat por usuarios
     * @param idUsuario1 id del usuario 1
     * @param idUsuario2 id del usuario 2
     * @return chat
     */
    public ChatModel obtenerChatPorUsuarios(Long idUsuario1, Long idUsuario2) {
        return chatRepository.findByIdUsuario1AndIdUsuario2(idUsuario1, idUsuario2);
    }

    /**
     * Metodo que se encarga de obtener todos los chats de un usuario propietario
     * @param idUsuario1 id del usuario
     * @return lista de chats
     */
    public ArrayList<ChatModel> obtenerChatPorIdUsuario1(Long idUsuario1) {
        // Obtengo todos los chats donde el idUsuario1 en la BD sea igual al idUsuario1 que recibo
        return chatRepository.findByIdUsuario1(idUsuario1);
    }

    /**
     * Metodo que se encarga de obtener un chat por id
     * @param id id del chat
     * @return chat
     */
    public ChatModel obtenerChatPorId(Long id) {
        return chatRepository.findById(id).get();
    }

    /**
     * Metodo que se encarga de actualizar un chat
     * @param chat chat a actualizar
     * @return chat actualizado
     */
    public ChatModel actualizarChat(ChatModel chat) {
        return chatRepository.save(chat);
    }

    /**
     * Metodo que se encarga de obtener todos los chats de un usuario propietario
     * @param idUsuario1 id del usuario
     * @param idUsuario2 id del usuario
     * @param idInmueble id del inmueble
     * @return lista de chats
     */
    public ArrayList<ChatModel> obtenerChatPorIdUsuario1YIdUsuario2YIdInmueble(Long idUsuario1, Long idUsuario2, Long idInmueble) {
        return chatRepository.findByIdUsuario1AndIdUsuario2AndIdInmueble(idUsuario1, idUsuario2, idInmueble);
    }

    /**
     * Metodo que se encarga de obtener todos los chats de un usuario interesado
     * @param idUsuario2 id del usuario
     * @return lista de chats
     */
    public ArrayList<ChatModel> obtenerChatPorIdUsuario2(Long idUsuario2) {
        return chatRepository.findByIdUsuario2(idUsuario2);
    }
}
