package com.api.crud.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "terrenos")
/**
 * Clase que define el modelo de un terreno
 */
public class TerrenoModel extends InmuebleModel {
    private boolean tieneServiciosBasicos;
    private String tipoSuelo;

    /**
     * Constructor de la clase TerrenoModel
     */
    public TerrenoModel() {
        setTipoInmueble(TipoInmueble.TERRENO);
    }

    // Getters y setters
    public boolean isTieneServiciosBasicos() {
        return tieneServiciosBasicos;
    }

    public void setTieneServiciosBasicos(boolean tieneServiciosBasicos) {
        this.tieneServiciosBasicos = tieneServiciosBasicos;
    }

    public String getTipoSuelo() {
        return tipoSuelo;
    }

    public void setTipoSuelo(String tipoSuelo) {
        this.tipoSuelo = tipoSuelo;
    }
}