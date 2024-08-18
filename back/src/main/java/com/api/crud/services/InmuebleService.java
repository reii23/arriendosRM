package com.api.crud.services;

import com.api.crud.models.*;
import com.api.crud.repository.IInmuebleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InmuebleService {
    @Autowired
    private IInmuebleRepository inmuebleRepository;

    public List<InmuebleModel> obtenerInmuebles() {
        return inmuebleRepository.findAll();
    }

    public Optional<InmuebleModel> obtenerInmueblePorId(Long id) {
        return inmuebleRepository.findById(id);
    }

    public CasaModel crearCasa(CasaModel casa) {
        return inmuebleRepository.save(casa);
    }

    public DepartamentoModel crearDepartamento(DepartamentoModel departamento) {
        return inmuebleRepository.save(departamento);
    }

    public ArrayList<InmuebleModel> obtenerInmueblePorIdUsuario(Long idUsuario) {
        return inmuebleRepository.findByIdUsuario(idUsuario);
    }

    public TerrenoModel crearTerreno(TerrenoModel terreno) {
        return inmuebleRepository.save(terreno);
    }

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

    public boolean eliminarInmueble(Long id) {
        if (inmuebleRepository.existsById(id)) {
            inmuebleRepository.deleteById(id);
            return true;
        }
        return false;
    }


}