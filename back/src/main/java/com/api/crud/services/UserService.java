package com.api.crud.services;

import com.api.crud.models.UserModel;
import com.api.crud.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
/**
 * Clase que define los servicios de los usuarios
 */
public class UserService {
    @Autowired // inyeccion de dependencias
    IUserRepository userRepository;

    /**
     * Metodo que se encarga de obtener los usuarios
     * @return lista de usuarios
     */
    public ArrayList<UserModel> obtenerUsuarios() {
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    /**
     * Metodo que se encarga de crear un usuario
     * @param user usuario a crear
     * @return usuario creado
     */
    public UserModel crearUsuario(UserModel user) {
        return userRepository.save(user);
    }

    /**
     * Metodo que se encarga de obtener un usuario por id
     * @param id id del usuario
     * @return usuario
     */
    public Optional<UserModel> obtenerUsuarioPorId(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Metodo que se encarga de modificar un usuario por id
     * @param request usuario a modificar
     * @param id id del usuario
     * @return usuario modificado
     */
    public UserModel modificarUsuarioPorId(UserModel request, Long id) {
        UserModel user = userRepository.findById(id).get();

        user.setNombre(request.getNombre());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    /**
     * Metodo que se encarga de eliminar un usuario por id
     * @param id id del usuario
     * @return true si se elimino, false si no se elimino
     */
    public boolean eliminarUsuarioPorId(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo que se encarga de loguear un usuario
     * @param user usuario a loguear
     * @return usuario logueado
     */
    public UserModel login(UserModel user) {
        UserModel userDB = userRepository.findByEmail(user.getEmail());
        if (userDB != null) {
            if (userDB.getPassword().equals(user.getPassword())) {
                return userDB;
            }
        }
        return null;
    }

    /**
     * Metodo que se encarga de obtener un usuario por email
     * @param email email del usuario
     * @return usuario
     */
    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}


