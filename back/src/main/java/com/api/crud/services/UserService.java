package com.api.crud.services;

import com.api.crud.models.UserModel;
import com.api.crud.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired // inyeccion de dependencias
    IUserRepository userRepository;

    public ArrayList<UserModel> obtenerUsuarios() {
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public UserModel crearUsuario(UserModel user) {
        return userRepository.save(user);
    }

    public Optional<UserModel> obtenerUsuarioPorId(Long id) {
        return userRepository.findById(id);
    }


    public UserModel modificarUsuarioPorId(UserModel request, Long id) {
        UserModel user = userRepository.findById(id).get();

        user.setNombre(request.getNombre());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    public boolean eliminarUsuarioPorId(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserModel login(UserModel user) {
        UserModel userDB = userRepository.findByEmail(user.getEmail());
        if (userDB != null) {
            if (userDB.getPassword().equals(user.getPassword())) {
                return userDB;
            }
        }
        return null;
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}


