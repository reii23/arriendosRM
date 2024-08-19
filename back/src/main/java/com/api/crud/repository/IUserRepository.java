package com.api.crud.repository;

import com.api.crud.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// nos permite realizar consultas a la base de datos
// Jpa viene con metodos predefinidos para realizar consultas
@Repository
/**
 * Esta interfaz se encarga de realizar las consultas a la base de datos
 */
public interface IUserRepository extends JpaRepository<UserModel, Long> {
    /**
     * Metodo que se encarga de buscar un usuario por email
     * @param email email del usuario
     * @return usuario
     */
    UserModel findByEmail(String email);

}
