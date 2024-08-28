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

    public ChatModel crearChat(Long idUsuario1, Long idUsuario2, Long idInmueble) {
        ChatModel chat = new ChatModel();
        chat.setIdUsuario1(idUsuario1);
        chat.setIdUsuario2(idUsuario2);
        chat.setIdInmueble(idInmueble);
        return chatRepository.save(chat);
    }

    public ChatModel obtenerChatPorUsuarios(Long idUsuario1, Long idUsuario2) {
        return chatRepository.findByIdUsuario1AndIdUsuario2(idUsuario1, idUsuario2);
    }


    public ArrayList<ChatModel> obtenerChatPorIdUsuario1(Long idUsuario1) {
        // Obtengo todos los chats donde el idUsuario1 en la BD sea igual al idUsuario1 que recibo
        return chatRepository.findByIdUsuario1(idUsuario1);
    }

    public ChatModel obtenerChatPorId(Long id) {
        return chatRepository.findById(id).get();
    }

    public ChatModel actualizarChat(ChatModel chat) {
        return chatRepository.save(chat);
    }

    public ArrayList<ChatModel> obtenerChatPorIdUsuario1YIdUsuario2YIdInmueble(Long idUsuario1, Long idUsuario2, Long idInmueble) {
        return chatRepository.findByIdUsuario1AndIdUsuario2AndIdInmueble(idUsuario1, idUsuario2, idInmueble);
    }

    public ArrayList<ChatModel> obtenerChatPorIdUsuario2(Long idUsuario2) {
        return chatRepository.findByIdUsuario2(idUsuario2);
    }
}
