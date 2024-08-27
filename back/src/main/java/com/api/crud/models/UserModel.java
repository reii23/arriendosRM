package com.api.crud.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
/**
 * Clase que define el modelo de un usuario
 */
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String email;
    private String password;
    private String numeroTelefono;
    private Integer rol; // 0: Admin, 1: usuarioRegistrado, 2: Agente Inmobiliario

    @ManyToMany(cascade = CascadeType.ALL) // Relación de muchos es a muchos con inmuebles
    @JoinTable(
            name = "usuario_inmueble_favorito", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "usuario_id"), // Relación con la tabla usuario
            inverseJoinColumns = @JoinColumn(name = "inmueble_id") // Relación con la tabla inmueble
    )
    private List<InmuebleModel> inmueblesFavoritos; // Lista de inmuebles favoritos

    // Getters y setters
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

    public String getnumeroTelefono() { return numeroTelefono; }

    public void setnumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }

    public List<InmuebleModel> getInmueblesFavoritos() {
        return inmueblesFavoritos;
    }

    public void setInmueblesFavoritos(InmuebleModel inmueblesFavoritos) {
        // Si la lista de inmuebles favoritos no contiene el inmueble, se agrega
        if(!this.inmueblesFavoritos.contains(inmueblesFavoritos)){
            this.inmueblesFavoritos.add(inmueblesFavoritos);
        }
    }

}
