package com.api.crud.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    private String password;

    private Integer rol; // TO DO: preguntar -> 0: Admin, 1: Propietario, 2: UsuarioInteresado, 3: Notario

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRol() {
        return rol;
    } // TO DO: preguntar

    public void setRol(Integer rol) {
        this.rol = rol;
    } // TO DO: preguntar

}
