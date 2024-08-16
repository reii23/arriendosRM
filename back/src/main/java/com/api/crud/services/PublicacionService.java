package com.api.crud.services;

import com.api.crud.models.PublicacionModel;
import com.api.crud.repository.IPublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionService {
    @Autowired
    private IPublicacionRepository publicacionRepository;

    public PublicacionModel crearPublicacion(PublicacionModel publicacion) {
        return publicacionRepository.save(publicacion);
    }

    public List<PublicacionModel> obtenerPublicaciones() {
        return publicacionRepository.findAll();
    }

    public Optional<PublicacionModel>editarPublicacion(Long id, PublicacionModel publicacionActualizada) {
        return publicacionRepository.findById(id).map(publicacion -> {
            publicacion.setFechaPublicacion(publicacionActualizada.getFechaPublicacion());
            publicacion.setDescripcion(publicacionActualizada.getDescripcion());
            return publicacionRepository.save(publicacion);
        });
    }
}
