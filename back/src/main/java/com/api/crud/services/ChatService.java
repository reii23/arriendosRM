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

    public ChatModel crearChat(Long idUsuario1, Long idUsuario2) {
        ChatModel chat = new ChatModel();
        chat.setIdUsuario1(idUsuario1);
        chat.setIdUsuario2(idUsuario2);
        return chatRepository.save(chat);
    }

    public ChatModel obtenerChatPorUsuarios(Long idUsuario1, Long idUsuario2) {
        return chatRepository.findByIdUsuario1AndIdUsuario2(idUsuario1, idUsuario2);
    }


    public ArrayList<ChatModel> obtenerChatPorIdUsuario1(Long idUsuario1) {
        // Obtengo todos los chats donde el idUsuario1 en la BD sea igual al idUsuario1 que recibo
        return chatRepository.findByIdUsuario1(idUsuario1);
    }
}
