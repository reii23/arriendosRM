package com.api.crud.controllers;

import com.api.crud.models.*;
import com.api.crud.services.InmuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/inmuebles")
/**
 * Clase que define el controlador de los inmuebles
 */
public class InmuebleController {
    @Autowired
    private InmuebleService inmuebleService;

    @GetMapping
    /**
     * Metodo que se encarga de obtener los inmuebles
     * @return lista de inmuebles
     */
    public ResponseEntity<List<InmuebleModel>> obtenerInmuebles() {
        return ResponseEntity.ok(inmuebleService.obtenerInmuebles());
    }

    @GetMapping("/{id}")
    /**
     * Metodo que se encarga de obtener un inmueble por id
     * @param id id del inmueble
     * @return inmueble
     */
    public ResponseEntity<InmuebleModel> obtenerInmueblePorId(@PathVariable Long id) {
        return inmuebleService.obtenerInmueblePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/property/{id}")
    /**
     * Metodo que se encarga de obtener un inmueble por id
     * @param id id del inmueble
     * @return inmueble
     */
    public ResponseEntity<InmuebleModel> obtenerPropiedadesPorId(@PathVariable Long id) {
        return inmuebleService.obtenerInmueblePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/obtenerInmueblesPorUsuario/{idUsuario}")
    /**
     * Metodo que se encarga de obtener los inmuebles por id de usuario
     * @param idUsuario id del usuario
     * @return lista de inmuebles
     */
    public ArrayList<InmuebleModel> obtenerInmueblesPorIdUsuario(@PathVariable Long idUsuario) {
        return inmuebleService.obtenerInmueblePorIdUsuario(idUsuario);
    }

    @PostMapping("/casa")
    /**
     * Metodo que se encarga de crear una casa
     * @param casa casa
     * @return casa
     */
    public ResponseEntity<CasaModel> crearCasa(@RequestBody CasaModel casa) {
        return ResponseEntity.ok(inmuebleService.crearCasa(casa));
    }

    @PostMapping("/departamento")
    /**
     * Metodo que se encarga de crear un departamento
     * @param departamento departamento
     * @return departamento
     */
    public ResponseEntity<DepartamentoModel> crearDepartamento(@RequestBody DepartamentoModel departamento) {
        return ResponseEntity.ok(inmuebleService.crearDepartamento(departamento));
    }

    @PostMapping("/terreno")
    /**
     * Metodo que se encarga de crear un terreno
     * @param terreno terreno
     * @return terreno
     */
    public ResponseEntity<TerrenoModel> crearTerreno(@RequestBody TerrenoModel terreno) {
        return ResponseEntity.ok(inmuebleService.crearTerreno(terreno));
    }

    @PutMapping("/{id}")
    /**
     * Metodo que se encarga de actualizar un inmueble
     * @param id id del inmueble
     * @param inmueble inmueble
     * @return inmueble
     */
    public ResponseEntity<InmuebleModel> actualizarInmueble(@PathVariable Long id, @RequestBody InmuebleModel inmueble) {
        return inmuebleService.actualizarInmueble(id, inmueble)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    /**
     * Metodo que se encarga de eliminar un inmueble
     * @param id id del inmueble
     * @return respuesta
     */
    public ResponseEntity<Void> eliminarInmueble(@PathVariable Long id) {
        return inmuebleService.eliminarInmueble(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }


    @GetMapping("/verificar-direccion")
    /**
     * Metodo que se encarga de verificar si existe un inmueble por direccion
     * @param direccion direccion del inmueble
     * @return respuesta
     */
    public ResponseEntity<Boolean> verificarDireccion(@RequestParam String direccion) {
        boolean existeInmueble = inmuebleService.existeInmueblePorDireccion(direccion);
        return ResponseEntity.ok(existeInmueble);
    }

    @PostMapping("/agregarMeGusta/{inmueble}")
    /**
     * Metodo que se encarga de agregar un me gusta a un inmueble
     * @param inmueble inmueble
     */
    public void agregarMeGusta(@PathVariable InmuebleModel inmueble){
        inmuebleService.agregarMeGusta(inmueble);
    }

    @DeleteMapping("/eliminarMeGusta/{inmueble}")
    /**
     * Metodo que se encarga de eliminar un me gusta a un inmueble
     * @param inmueble inmueble
     */
    public void eliminarMeGusta(@PathVariable InmuebleModel inmueble){
        inmuebleService.eliminarMeGusta(inmueble);
    }

    @GetMapping("/obtenerMeGustasInmueble")
    /**
     * Metodo que se encarga de obtener los me gustas de un inmueble
     * @param inmmueble inmueble
     * @return me gustas
     */
    public Integer obtenerMeGustasInmueble(@RequestBody InmuebleModel inmmueble){
        return inmmueble.getMeGustas();
    }

    @GetMapping("/obtenerMeGustas")
    /**
     * Metodo que se encarga de obtener los me gustas de todos los inmuebles
     * @return me gustas
     */
    public ArrayList<Integer> obtenerMeGustas(){
        return inmuebleService.obtenerMeGustas();
    }
}