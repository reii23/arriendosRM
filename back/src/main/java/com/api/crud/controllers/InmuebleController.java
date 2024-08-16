package com.api.crud.controllers;

import com.api.crud.models.*;
import com.api.crud.services.InmuebleService;
import com.api.crud.services.UserService;
import com.api.crud.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inmuebles")
public class InmuebleController {
    @Autowired
    private InmuebleService inmuebleService;
    private UserService userService;

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

    @PutMapping("/crearPublicacion/{idUsuario}")
    public ResponseEntity<InmuebleModel> crearPublicacion(@RequestBody InmuebleModel inmueble, @PathVariable Long idUsuario) {
        UserModel usuario = userService.obtenerUsuarioPorId(idUsuario);

        if(inmueble.getTipoInmueble() == InmuebleModel.TipoInmueble.CASA){
            InmuebleModel inmuebleCreado = inmuebleService.crearCasa((CasaModel) inmueble);
            usuario.getInmueble().add(inmuebleCreado);
            return ResponseEntity.ok(inmuebleCreado);

        } else if(inmueble.getTipoInmueble() == InmuebleModel.TipoInmueble.DEPARTAMENTO){
            InmuebleModel inmuebleCreado = inmuebleService.crearDepartamento((DepartamentoModel) inmueble);
            usuario.getInmueble().add(inmuebleCreado);
            return ResponseEntity.ok(inmuebleCreado);

        } else {
            InmuebleModel inmuebleCreado = inmuebleService.crearTerreno((TerrenoModel) inmueble);
            usuario.getInmueble().add(inmuebleCreado);
            return ResponseEntity.ok(inmuebleCreado);
        }
    }
}