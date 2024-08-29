package com.api.crud.controllers;

import com.api.crud.models.InmuebleModel;
import com.api.crud.models.UserModel;
import com.api.crud.services.InmuebleService;
import com.api.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
/**
 * Clase que define el controlador de los usuarios
 */
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private InmuebleService inmuebleService;

    @GetMapping(path = "/obtenerUsuarios")
    /**
     * Metodo que se encarga de obtener los usuarios
     * @return lista de usuarios
     */
    public ArrayList<UserModel> obtenerUsuarios() {
        return this.userService.obtenerUsuarios();
    }

    @PostMapping(path ="/crearUsuario")
    /**
     * Metodo que se encarga de crear un usuario
     * @param user usuario a crear
     * @return respuesta de la creacion del usuario
     */
    public ResponseEntity<?> crearUsuario(@RequestBody UserModel user) {
        try {
            // Validar si el email ya existe
            UserModel userDB = this.userService.findByEmail(user.getEmail());
            if (userDB != null) {
                return ResponseEntity.badRequest().body(new MensajeRespuesta("El email ya existe"));
            }
            UserModel userCreado = this.userService.crearUsuario(user);
            return ResponseEntity.ok().body(new MensajeRespuesta("Usuario creado correctamente", userCreado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MensajeRespuesta("Error al crear usuario: " + e.getMessage()));
        }
    }

    @GetMapping(path = "/obtenerUsuarioPorId/{id}")
    /**
     * Metodo que se encarga de obtener un usuario por id
     * @param id id del usuario
     * @return usuario
     */
    public Optional<UserModel> obtenerUsuarioPorId(@PathVariable("id") Long id) {
        return this.userService.obtenerUsuarioPorId(id);
    }


    @GetMapping(path = "/obtenerUsuarioPorEmail/{email}")
    /**
     * Metodo que se encarga de obtener un usuario por email
     * @param email email del usuario
     * @return usuario
     */
    public UserModel obtenerUsuarioPorEmail(@PathVariable("email") String email) {
        return this.userService.findByEmail(email);
    }

    @PutMapping(path = "/modificarUsuarioPorId/{id}")
    /**
     * Metodo que se encarga de modificar un usuario por id
     * @param request usuario a modificar
     * @param id id del usuario
     * @return usuario modificado
     */
    public UserModel modificarUsuarioPorId(@RequestBody UserModel request, @PathVariable("id") Long id) {
        return this.userService.modificarUsuarioPorId(request, id);
    }

    @DeleteMapping(path = "/eliminarUsuarioPorId/{id}")
    /**
     * Metodo que se encarga de eliminar un usuario por id
     * @param id id del usuario
     * @return respuesta de la eliminacion del usuario
     */
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable("id") Long id){
        boolean resultado = this.userService.eliminarUsuarioPorId(id);
        if (resultado) {
            return ResponseEntity.ok().body(new MensajeRespuesta("Usuario eliminado correctamente"));
        } else {
            return ResponseEntity.badRequest().body(new MensajeRespuesta("Error al eliminar el usuario"));
        }
    }


    // Login
    @PostMapping(path = "/login")
    /**
     * Metodo que se encarga de realizar el login
     * @param user usuario
     * @return respuesta del login
     */
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        UserModel userDB = this.userService.login(user);
        if (userDB != null) {
            return ResponseEntity.ok().body(new MensajeRespuesta("Login exitoso", userDB));
        } else {
            return ResponseEntity.badRequest().body(new MensajeRespuesta("Credenciales incorrectas"));
        }
    }

    /**
     * Clase que se encarga de enviar mensajes de respuesta
     */
    private static class MensajeRespuesta {
        private String mensaje;
        private Object data;

        // Constructores

        /**
         * Constructor de la clase
         * @param mensaje
         */
        public MensajeRespuesta(String mensaje) {
            this.mensaje = mensaje;
        }

        /**
         * Constructor de la clase
         * @param mensaje
         * @param data
         */
        public MensajeRespuesta(String mensaje, Object data) {
            this.mensaje = mensaje;
            this.data = data;
        }

        // Getters y setters
        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }
        public Object getData() { return data; }
        public void setData(Object data) { this.data = data; }
    }

    @GetMapping(path = "/obtenerInmueblesFavoritos/{idUsuario}")
    /**
     * Metodo que se encarga de obtener los inmuebles favoritos de un usuario
     * @param idUsuario id del usuario
     * @return lista de inmuebles
     */
    public List<Long> obtenerFavoritos(@PathVariable Long idUsuario) {
        return this.userService.obtenerFavoritos(idUsuario);
    }

    @PostMapping("/agregarInmuebleFavorito/{idUsuario}/{idInmueble}")
    /**
     * Metodo que se encarga de agregar un inmueble a favoritos
     * @param idUsuario id del usuario
     * @param idInmueble id del inmueble
     */
    public ResponseEntity<?> agregarFavorito(@PathVariable Long idUsuario, @PathVariable Long idInmueble) {
        this.userService.agregarFavorito(idUsuario, idInmueble); // Agregar inmueble a favoritos del usuario
        InmuebleModel inmuebleAgregado = this.inmuebleService.obtenerInmueblePorId(idInmueble).get(); // Obtener inmueble de la clase inmueble
        this.inmuebleService.agregarMeGusta(inmuebleAgregado); // Agregar un me gusta al inmueble
        return ResponseEntity.ok().body(new MensajeRespuesta("Inmueble agregado a favoritos"));
    }

    @DeleteMapping("/eliminarInmuebleFavorito/{idUsuario}/{idInmueble}")
    /**
     * Metodo que se encarga de eliminar un inmueble de favoritos
     * @param idUsuario id del usuario
     * @param idInmueble id del inmueble
     */
    public ResponseEntity<?> eliminarFavorito(@PathVariable Long idUsuario, @PathVariable Long idInmueble){
        this.userService.eliminarFavorito(idUsuario, idInmueble); // Eliminar inmueble de favoritos del usuario
        InmuebleModel inmuebleEliminado = this.inmuebleService.obtenerInmueblePorId(idInmueble).get(); // Obtener inmueble de la clase inmueble
        this.inmuebleService.eliminarMeGusta(inmuebleEliminado); // Eliminar un me gusta al inmueble
        return ResponseEntity.ok().body(new MensajeRespuesta("Inmueble eliminado de favoritos"));
    }
}

// Consultas en POSTMAN:
// obtener todos los usuarios: GET http://localhost:8080/user/obtenerUsuarios
// crear un usuario: POST http://localhost:8080/user/crearUsuario
// obtener un usuario por id: GET http://localhost:8080/user/obtenerUsuarioPorId/
// modificar un usuario por id: PUT http://localhost:8080/user/modificarUsuarioPorId/
// eliminar un usuario por id: DELETE http://localhost:8080/user/eliminarUsuarioPorId/
// login: POST http://localhost:8080/user/login