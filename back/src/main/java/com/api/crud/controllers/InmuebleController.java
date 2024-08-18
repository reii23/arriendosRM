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
public class InmuebleController {
    @Autowired
    private InmuebleService inmuebleService;

    @GetMapping
    public ResponseEntity<List<InmuebleModel>> obtenerInmuebles() {
        return ResponseEntity.ok(inmuebleService.obtenerInmuebles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InmuebleModel> obtenerInmueblePorId(@PathVariable Long id) {
        return inmuebleService.obtenerInmueblePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/property/{id}")
    public ResponseEntity<InmuebleModel> obtenerPropiedadesPorId(@PathVariable Long id) {
        return inmuebleService.obtenerInmueblePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/obtenerInmueblesPorUsuario/{idUsuario}")
    public ArrayList<InmuebleModel> obtenerInmueblesPorIdUsuario(@PathVariable Long idUsuario) {
        return inmuebleService.obtenerInmueblePorIdUsuario(idUsuario);
    }

    @PostMapping("/casa")
    public ResponseEntity<CasaModel> crearCasa(@RequestBody CasaModel casa) {
        return ResponseEntity.ok(inmuebleService.crearCasa(casa));
    }

    @PostMapping("/departamento")
    public ResponseEntity<DepartamentoModel> crearDepartamento(@RequestBody DepartamentoModel departamento) {
        return ResponseEntity.ok(inmuebleService.crearDepartamento(departamento));
    }

    @PostMapping("/terreno")
    public ResponseEntity<TerrenoModel> crearTerreno(@RequestBody TerrenoModel terreno) {
        return ResponseEntity.ok(inmuebleService.crearTerreno(terreno));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InmuebleModel> actualizarInmueble(@PathVariable Long id, @RequestBody InmuebleModel inmueble) {
        return inmuebleService.actualizarInmueble(id, inmueble)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInmueble(@PathVariable Long id) {
        return inmuebleService.eliminarInmueble(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }


}