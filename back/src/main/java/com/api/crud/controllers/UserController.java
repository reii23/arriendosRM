package com.api.crud.controllers;

import com.api.crud.models.UserModel;
import com.api.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/obtenerUsuarios")
    public ArrayList<UserModel> obtenerUsuarios() {
        return this.userService.obtenerUsuarios();
    }

    @PostMapping(path ="/crearUsuario")
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
    public Optional<UserModel> obtenerUsuarioPorId(@PathVariable("id") Long id) {
        return this.userService.obtenerUsuarioPorId(id);
    }

    // Obtener usuario por email
    @GetMapping(path = "/obtenerUsuarioPorEmail/{email}")
    public UserModel obtenerUsuarioPorEmail(@PathVariable("email") String email) {
        return this.userService.findByEmail(email);
    }

    @PutMapping(path = "/modificarUsuarioPorId/{id}")
    public UserModel modificarUsuarioPorId(@RequestBody UserModel request, @PathVariable("id") Long id) {
        return this.userService.modificarUsuarioPorId(request, id);
    }

    @DeleteMapping(path = "/eliminarUsuarioPorId/{id}")
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
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        UserModel userDB = this.userService.login(user);
        if (userDB != null) {
            return ResponseEntity.ok().body(new MensajeRespuesta("Login exitoso", userDB));
        } else {
            return ResponseEntity.badRequest().body(new MensajeRespuesta("Credenciales incorrectas"));
        }
    }

    private static class MensajeRespuesta {
        private String mensaje;
        private Object data;

        public MensajeRespuesta(String mensaje) {
            this.mensaje = mensaje;
        }

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
}

// Consultas en POSTMAN:
// obtener todos los usuarios: GET http://localhost:8080/user/obtenerUsuarios
// crear un usuario: POST http://localhost:8080/user/crearUsuario
// obtener un usuario por id: GET http://localhost:8080/user/obtenerUsuarioPorId/
// modificar un usuario por id: PUT http://localhost:8080/user/modificarUsuarioPorId/
// eliminar un usuario por id: DELETE http://localhost:8080/user/eliminarUsuarioPorId/
// login: POST http://localhost:8080/user/login