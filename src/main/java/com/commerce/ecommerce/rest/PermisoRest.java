package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.model.Permiso;
import com.commerce.ecommerce.service.PermisoService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('permiso')")
@RestController
@RequestMapping("/permiso")
public class PermisoRest {
    @Autowired
    private PermisoService permisoService;

    @GetMapping(path = "/read")
    public ResponseEntity<List<Permiso>> getAll(){
        return ResponseEntity.ok(permisoService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Permiso> getPermiso(@PathVariable Long id){
        try{
            Permiso permiso = permisoService.findById(id).orElseThrow(() -> new Exception("Rol no encontrado"));
            return ResponseEntity.ok(permiso);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<Permiso> store(@RequestBody Permiso permiso){
        try {
            Permiso nuevo = permisoService.save(permiso);
            return ResponseEntity.created(new URI("/permiso/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (permisoService.existsById(id)) {
                permisoService.deleteById(id); // Elimina el si existe
                return ResponseEntity.ok().build(); // Respuesta 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permiso> update(@PathVariable Long id, @RequestBody Permiso permisoActualizado) {
        try {
            Optional<Permiso> permisoExistente = permisoService.findById(id);

            if (permisoExistente.isPresent()) {
                Permiso permiso = permisoExistente.get();
                permiso.setPermiso(permisoActualizado.getPermiso());

                permisoService.save(permiso);

                return ResponseEntity.ok(permiso);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
