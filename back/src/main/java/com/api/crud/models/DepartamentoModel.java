package com.api.crud.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "departamentos")
/**
 * Clase que define el modelo de un departamento
 */
public class DepartamentoModel extends InmuebleModel {
    private int piso;
    private boolean tieneAscensor;

    /**
     * Constructor de la clase DepartamentoModel
     */
    public DepartamentoModel() {
        setTipoInmueble(TipoInmueble.DEPARTAMENTO);
    }

    // Getters and setters
    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public boolean isTieneAscensor() {
        return tieneAscensor;
    }

    public void setTieneAscensor(boolean tieneAscensor) {
        this.tieneAscensor = tieneAscensor;
    }
}