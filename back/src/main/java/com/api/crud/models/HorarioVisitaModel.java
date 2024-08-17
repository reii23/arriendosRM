package com.api.crud.models;

import jakarta.persistence.*;

@Entity
@Table(name = "horarioVisita")
public class HorarioVisitaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idInmueble;
    private Long idUsuario;
    private Long idVisitante;
    private String fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(Long idInmueble) {
        this.idInmueble = idInmueble;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdVisitante() {return idVisitante;}

    public void setIdVisitante(Long idVisitante) {this.idVisitante = idVisitante;}

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
