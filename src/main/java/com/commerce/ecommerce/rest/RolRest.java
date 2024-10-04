package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.model.Rol;
import com.commerce.ecommerce.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@PreAuthorize("hasRole('rol')")
@RestController
@RequestMapping("/rol")
public class RolRest {
    @Autowired
    private RolService rolService;

    @GetMapping(path = "/read")
    private ResponseEntity<List<Rol>> getAllAsistencia(){
        return ResponseEntity.ok(rolService.findAll());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<Rol> getAsistencia(@PathVariable Long id){
        try{
            Rol rol = rolService.findById(id).orElseThrow(() -> new Exception("Rol no encontrado"));
            return ResponseEntity.ok(rol);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/crear")
    private ResponseEntity<Rol> store(@RequestBody Rol rol){
        try {
            Rol nuevo = rolService.save(rol);
            return ResponseEntity.created(new URI("/rol/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (rolService.existsById(id)) {
                rolService.deleteById(id); // Elimina el usuario si existe
                return ResponseEntity.ok().build(); // Respuesta 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> update(@PathVariable Long id, @RequestBody Rol rolActualizado) {
        try {
            Optional<Rol> rolExistente = rolService.findById(id);

            if (rolExistente.isPresent()) {
                // Se actualizan los valores del rol existente
                Rol rol = rolExistente.get();
                rol.setNombre(rolActualizado.getNombre());

                // Guardar el rol actualizado
                rolService.save(rol);

                return ResponseEntity.ok(rol); // Retorna el rol actualizado con 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 si el rol no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Retorna 500 si hay un error
        }
    }


}
