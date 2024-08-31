package com.api.crud.services;

import com.api.crud.models.InmuebleModel;
import com.api.crud.models.UserModel;
import com.api.crud.repository.IInmuebleRepository;
import com.api.crud.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
/**
 * Clase que define los servicios de los usuarios
 */
public class UserService {
    @Autowired // inyeccion de dependencias
    IUserRepository userRepository;
    @Autowired
    IInmuebleRepository inmuebleRepository;

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


    /**
     * Metodo que se encarga de obtener los inmuebles favoritos de un usuario
     * @param idUsuario id del usuario
     * @return lista de inmuebles favoritos
     */
    public List<Long> obtenerFavoritos(Long idUsuario) {
        UserModel usuario = userRepository.findById(idUsuario).get(); // se obtiene el usuario
        List<InmuebleModel> listaInmueblesFav= usuario.getInmueblesFavoritos(); // se obtiene la lista de inmuebles favoritos
        List<Long> listaIdInmueblesFav = new ArrayList<>(); // se crea una lista de id de inmuebles favoritos vacía
        for (InmuebleModel inmueble : listaInmueblesFav) { // se recorre la lista de inmuebles favoritos
            listaIdInmueblesFav.add(inmueble.getId()); // se agrega el id del inmueble a la lista de id de inmuebles favoritos
        }
        return listaIdInmueblesFav;
    }

    /**
     * Metodo que se encarga de agregar un inmueble a favoritos
     * @param idUsuario id del usuario
     * @param idInmueble id del inmueble
     */
    public void agregarFavorito(Long idUsuario, Long idInmueble) {
        UserModel usuario = userRepository.findById(idUsuario).get(); // se obtiene el usuario
        InmuebleModel inmuebleFavorito = inmuebleRepository.findById(idInmueble).get(); // se obtiene el inmueble
        if(!usuario.getInmueblesFavoritos().contains(inmuebleFavorito)){ // si el inmueble no esta en la lista de favoritos
            usuario.setInmueblesFavoritos(inmuebleFavorito); // se agrega el inmueble a la lista de favoritos
            userRepository.save(usuario); // se guarda el usuario
        }
    }

    /**
     * Metodo que se encarga de eliminar un inmueble de favoritos
     * @param idUsuario id del usuario
     * @param idInmueble id del inmueble
     */
    public void eliminarFavorito(Long idUsuario, Long idInmueble) {
        UserModel usuario = userRepository.findById(idUsuario).get(); // se obtiene el usuario
        InmuebleModel inmueble = inmuebleRepository.findById(idInmueble).get(); // se obtiene el inmueble
        List<InmuebleModel> listaInmueblesFavoritos = usuario.getInmueblesFavoritos(); // se obtiene la lista de inmuebles favoritos del usuario
        if(listaInmueblesFavoritos.contains(inmueble)){ // se verifica si el inmueble esta en la lista de favoritos
            listaInmueblesFavoritos.remove(inmueble); // se elimina el inmueble de la lista de favoritos
            userRepository.save(usuario); // se guarda el usuario
        }else {
            System.out.println("No se encontro el inmueble en la lista de favoritos");
        }
    }
}


