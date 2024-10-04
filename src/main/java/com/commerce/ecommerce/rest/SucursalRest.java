package com.commerce.ecommerce.rest;


import com.commerce.ecommerce.model.Sucursal;
import com.commerce.ecommerce.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@PreAuthorize("hasRole('sucursal')")
@RestController
@RequestMapping("/sucursal")
public class SucursalRest {
    @Autowired
    private SucursalService sucursalService;

    @GetMapping(path = "/read")
    private ResponseEntity<List<Sucursal>> getAllAsistencia(){
        return ResponseEntity.ok(sucursalService.findAll());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<Sucursal> getAsistencia(@PathVariable Long id){
        try{
            Sucursal sucursal = sucursalService.findById(id).orElseThrow(() -> new Exception("Rol no encontrado"));
            return ResponseEntity.ok(sucursal);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/crear")
    private ResponseEntity<Sucursal> store(@RequestBody Sucursal sucursal){
        try {
            Sucursal nuevo = sucursalService.save(sucursal);
            return ResponseEntity.created(new URI("/sucursal/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (sucursalService.existsById(id)) {
                sucursalService.deleteById(id); // Elimina el si existe
                return ResponseEntity.ok().build(); // Respuesta 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> update(@PathVariable Long id, @RequestBody Sucursal sucursalActualizado) {
        try {
            Optional<Sucursal> sucursalExistente = sucursalService.findById(id);

            if (sucursalExistente.isPresent()) {
                Sucursal sucursal = sucursalExistente.get();
                sucursal.setNombre(sucursalActualizado.getNombre());
                sucursal.setDireccion(sucursalActualizado.getDireccion());

                sucursalService.save(sucursal);

                return ResponseEntity.ok(sucursal);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
