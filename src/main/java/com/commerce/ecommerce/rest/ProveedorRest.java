package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.model.Proveedor;
import com.commerce.ecommerce.service.ProveedorService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proveedor")
public class ProveedorRest {
    @Autowired
    private ProveedorService marcaService;

        @GetMapping(path = "/read")
    private ResponseEntity<List<Proveedor>> getAll(){
        return ResponseEntity.ok(marcaService.findAll());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<Proveedor> get(@PathVariable Long id){
        try{
            Proveedor marca = marcaService.findById(id).orElseThrow(() -> new Exception("Rol no encontrado"));
            return ResponseEntity.ok(marca);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/crear")
    private ResponseEntity<Proveedor> store(@RequestBody Proveedor proveedor){
        try {
            Proveedor nuevo = marcaService.save(proveedor);
            return ResponseEntity.created(new URI("/proveedor/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (marcaService.existsById(id)) {
                marcaService.deleteById(id); // Elimina el si existe
                return ResponseEntity.ok().build(); // Respuesta 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> update(@PathVariable Long id, @RequestBody Proveedor proveedorActualizado) {
        try {
            Optional<Proveedor> proveedorExistente = marcaService.findById(id);

            if (proveedorExistente.isPresent()) {
                Proveedor proveedor = proveedorExistente.get();
                proveedor.setNombre(proveedorActualizado.getNombre());
                proveedor.setContacto(proveedorActualizado.getContacto());
                proveedor.setEncargado(proveedorActualizado.getEncargado());

                marcaService.save(proveedor);

                return ResponseEntity.ok(proveedor);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
