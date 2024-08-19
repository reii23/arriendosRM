package com.api.crud.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "casas")
/**
 * Clase que define el modelo de una casa
 */
public class CasaModel extends InmuebleModel {
    private int numPisos;
    private boolean tienePatio;

    /**
     * Constructor de la clase CasaModel
     */
    public CasaModel() {
        setTipoInmueble(TipoInmueble.CASA);
    }

    // Getters and setters
    public int getNumPisos() {
        return numPisos;
    }

    public void setNumPisos(int numPisos) {
        this.numPisos = numPisos;
    }

    public boolean isTienePatio() {
        return tienePatio;
    }

    public void setTienePatio(boolean tienePatio) {
        this.tienePatio = tienePatio;
    }
}