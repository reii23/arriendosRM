package com.api.crud.controllers;

import com.api.crud.models.PublicacionModel;
import com.api.crud.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publicacion")
public class PublicacionController {
    @Autowired
    private PublicacionService publicacionService;

    @PostMapping("/crearPublicacion")
    public ResponseEntity<PublicacionModel> crearPublicacion(@RequestBody PublicacionModel publicacion) {
        return ResponseEntity.ok(publicacionService.crearPublicacion(publicacion));
    }

    @GetMapping("/obtenerPublicaciones")
    public ResponseEntity<?> obtenerPublicaciones() {
        return ResponseEntity.ok(publicacionService.obtenerPublicaciones());
    }

    @PutMapping("/editarPublicacion/{id}")
    public ResponseEntity<?> editarPublicacion(@PathVariable Long id, @RequestBody PublicacionModel publicacion) {
        return publicacionService.editarPublicacion(id, publicacion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
