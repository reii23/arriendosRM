package com.api.crud.repository;

import com.api.crud.models.InmuebleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
<<<<<<< Updated upstream
/**
 * Esta interfaz se encarga de realizar las consultas a la base de datos
 */
=======
>>>>>>> Stashed changes
public interface IInmuebleRepository extends JpaRepository<InmuebleModel, Long> {
    /**
     * Metodo que se encarga de buscar los inmuebles por id de usuario
     * @param idUsuario id del usuario
     * @return lista de inmuebles
     */
    public ArrayList<InmuebleModel> findByIdUsuario(Long idUsuario);

    public InmuebleModel findByDireccion(String direccion);
}
