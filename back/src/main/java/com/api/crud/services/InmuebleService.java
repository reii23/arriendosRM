package com.api.crud.services;

import com.api.crud.models.*;
import com.api.crud.repository.IInmuebleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
/**
 * Clase que define los servicios de los inmuebles
 */
public class InmuebleService {
    @Autowired
    private IInmuebleRepository inmuebleRepository;

    /**
     * Metodo que se encarga de obtener los inmuebles
     * @return lista de inmuebles
     */
    public List<InmuebleModel> obtenerInmuebles() {
        return inmuebleRepository.findAll();
    }

    /**
     * Metodo que se encarga de obtener un inmueble por id
     * @param id id del inmueble
     * @return inmueble
     */
    public Optional<InmuebleModel> obtenerInmueblePorId(Long id) {
        return inmuebleRepository.findById(id);
    }

    /**
     * Metodo que se encarga de obtener un inmueble por id
     * @param casa id del inmueble
     * @return casa
     */
    public CasaModel crearCasa(CasaModel casa) {
        return inmuebleRepository.save(casa);
    }

    /**
     * Metodo que se encarga de obtener un inmueble por id
     * @param departamento id del inmueble
     * @return departamento
     */
    public DepartamentoModel crearDepartamento(DepartamentoModel departamento) {
        return inmuebleRepository.save(departamento);
    }

    /**
     * Metodo que se encarga de obtener un inmueble por id
     * @param idUsuario id del usuario
     * @return lista de inmuebles
     */
    public ArrayList<InmuebleModel> obtenerInmueblePorIdUsuario(Long idUsuario) {
        return inmuebleRepository.findByIdUsuario(idUsuario);
    }

    /**
     * Metodo que crea un terreno
     * @param terreno terreno
     * @return terreno
     */
    public TerrenoModel crearTerreno(TerrenoModel terreno) {
        return inmuebleRepository.save(terreno);
    }

    /**
     * Metodo que se encarga de actualizar un inmueble
     * @param id id del inmueble
     * @param inmuebleActualizado inmueble actualizado
     * @return inmueble actualizado
     */
    public Optional<InmuebleModel> actualizarInmueble(Long id, InmuebleModel inmuebleActualizado) {
        return inmuebleRepository.findById(id).map(inmueble -> {
            inmueble.setDireccion(inmuebleActualizado.getDireccion());
            inmueble.setPrecio(inmuebleActualizado.getPrecio());
            inmueble.setDisponible(inmuebleActualizado.isDisponible());
            inmueble.setVerificado(inmuebleActualizado.isVerificado());
            inmueble.setMetrosCuadrados(inmuebleActualizado.getMetrosCuadrados());
            return inmuebleRepository.save(inmueble);
        });
    }

    /**
     * Metodo que se encarga de eliminar un inmueble
     * @param id id del inmueble
     * @return true si se elimino, false si no
     */
    public boolean eliminarInmueble(Long id) {
        if (inmuebleRepository.existsById(id)) {
            inmuebleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Metodo que se encarga de verificar si existe un inmueble por direccion
     * @param direccion
     * @return true si existe, false si no
     */
    public boolean existeInmueblePorDireccion(String direccion) {
        InmuebleModel inmueble = inmuebleRepository.findByDireccion(direccion);
        return inmueble != null;
    }

    public Integer obtenerMeGustasInmueble(InmuebleModel inmueble){
        return inmueble.getMeGustas();
    }

    public ArrayList<Integer> obtenerMeGustas() {
        ArrayList<InmuebleModel> inmuebles = (ArrayList<InmuebleModel>) inmuebleRepository.findAll();
        ArrayList<Integer> listaMeGustas = new ArrayList<>();
        for(InmuebleModel inmueble : inmuebles){
            listaMeGustas.add(inmueble.getMeGustas());
        }
        return listaMeGustas;
    }

    public void agregarMeGusta(InmuebleModel inmueble) {
        inmueble.setMeGustas(inmueble.getMeGustas() + 1);
        inmuebleRepository.save(inmueble);
    }
}