package com.api.crud.models;

import jakarta.persistence.*;

@Entity
@Table(name = "inmuebles")
@Inheritance(strategy = InheritanceType.JOINED)
/**
 * Clase abstracta que representa un inmueble en general.
 * Se utiliza para definir los atributos comunes de los inmuebles.
 */
public abstract class InmuebleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_inmueble")
    private TipoInmueble tipoInmueble;

    private String direccion;
    private double precio;
    private boolean disponible;
    private boolean verificado;
    private double metrosCuadrados;
    private Long idUsuario;

    public enum TipoInmueble {
        CASA, DEPARTAMENTO, TERRENO
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "comuna")
    private Comuna comuna;

    public enum Comuna {
        COLINA,
        LAMPA,
        TIL_TIL,
        PIRQUE,
        PUENTE_ALTO,
        SAN_JOSE_DE_MAIPO,
        BUIN,
        CALERA_DE_TANGO,
        PAINE,
        SAN_BERNARDO,
        ALHUE,
        CURACAVI,
        MARIA_PINTO,
        MELIPILLA,
        SAN_PEDRO,
        CERRILLOS,
        CERRO_NAVIA,
        CONCHALI,
        EL_BOSQUE,
        ESTACION_CENTRAL,
        HUECHURABA,
        INDEPENDENCIA,
        LA_CISTERNA,
        LA_GRANJA,
        LA_FLORIDA,
        LA_PINTANA,
        LA_REINA,
        LAS_CONDES,
        LO_BARNECHEA,
        LO_ESPEJO,
        LO_PRADO,
        MACUL,
        MAIPU,
        NUNOA,
        PEDRO_AGUIRRE_CERDA,
        PENALOLEN,
        PROVIDENCIA,
        PUDAHUEL,
        QUILICURA,
        QUINTA_NORMAL,
        RECOLETA,
        RENCA,
        SAN_MIGUEL,
        SAN_JOAQUIN,
        SAN_RAMON,
        SANTIAGO,
        VITACURA,
        EL_MONTE,
        ISLA_DE_MAIPO,
        PADRE_HURTADO,
        PENAFLOR,
        TALAGANTE
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoInmueble getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(TipoInmueble tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public double getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(double metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public Comuna getComuna() { return comuna;}

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
