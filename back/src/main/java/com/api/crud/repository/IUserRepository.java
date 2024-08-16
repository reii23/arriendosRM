package com.api.crud.repository;

import com.api.crud.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// nos permite realizar consultas a la base de datos
// Jpa viene con metodos predefinidos para realizar consultas
@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);
    UserModel findUserModelById(Long id);
}
