package com.api.crud.repository;

import com.api.crud.models.InmuebleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
/**
 * Esta interfaz se encarga de realizar las consultas a la base de datos
 */
public interface IInmuebleRepository extends JpaRepository<InmuebleModel, Long> {
    /**
     * Metodo que se encarga de buscar los inmuebles por id de usuario
     * @param idUsuario id del usuario
     * @return lista de inmuebles
     */
    public ArrayList<InmuebleModel> findByIdUsuario(Long idUsuario);

    /**
     * Metodo que se encarga de buscar los inmuebles por direccion
     * @param direccion direccion del inmueble
     * @return inmueble
     */
    public InmuebleModel findByDireccion(String direccion);

    /**
     * Método que busca inmuebles dentro de un rango de precios
     * @param precioMin Precio mínimo
     * @param precioMax Precio máximo
     * @return Lista de inmuebles dentro del rango de precios especificado
     */
    public List<InmuebleModel> findByPrecioBetween(double precioMin, double precioMax);
}
