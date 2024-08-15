package com.api.crud.models;

import jakarta.persistence.*;
import java.util.Date;
import com.api.crud.models.InmuebleModel;

@Entity
@Table(name = "publicaciones")
@Inheritance(strategy = InheritanceType.JOINED)
public class PublicacionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaPublicacion;
    private String descripcion;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public InmuebleModel getInmueble() {
        return inmueble;
    }

    public void setInmueble(InmuebleModel inmueble) {
        this.inmueble = inmueble;
    }

    // Relación de uno a uno con InmuebleModel
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inmueble_id", unique = true)
    private InmuebleModel inmueble;

    // Relación de muchos a uno con UserModel
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserModel usuario;


}
