package com.api.crud.repository;

import com.api.crud.models.ChatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface IChatRepository extends JpaRepository<ChatModel, Long> {
    ChatModel findByIdUsuario1AndIdUsuario2(Long idUsuario1, Long idUsuario2);

    ArrayList<ChatModel> findByIdUsuario1(Long idUsuario1);
}
