package com.api.crud.repository;

import com.api.crud.models.ChatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface IChatRepository extends JpaRepository<ChatModel, Long> {
    /**
     * Metodo que se encarga de buscar un chat por id de usuario 1 y id de usuario 2
     * @param idUsuario1 id del usuario 1
     * @param idUsuario2 id del usuario 2
     * @return chat
     */
    ChatModel findByIdUsuario1AndIdUsuario2(Long idUsuario1, Long idUsuario2);

    /**
     * Metodo que se encarga de buscar un chat por id de usuario 1
     * @param idUsuario1 id del usuario 1
     * @return lista de chats
     */
    ArrayList<ChatModel> findByIdUsuario1(Long idUsuario1);

    /**
     * Metodo que se encarga de buscar un chat por id de usuario 2
     * @param idUsuario1 id del usuario 2
     * @param idUsuario2 id del usuario 2
     * @param idInmueble id del inmueble
     * @return lista de chats
     */
    ArrayList<ChatModel> findByIdUsuario1AndIdUsuario2AndIdInmueble(Long idUsuario1, Long idUsuario2, Long idInmueble);

    /**
     * Metodo que se encarga de buscar un chat por id de usuario 2
     * @param idUsuario2 id del usuario 2
     * @return lista de chats
     */
    ArrayList<ChatModel> findByIdUsuario2(Long idUsuario2);
}
