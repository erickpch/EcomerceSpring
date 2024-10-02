package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.DTO.AlmacenDTO;
import com.commerce.ecommerce.model.Almacen;
import com.commerce.ecommerce.model.Sucursal;
import com.commerce.ecommerce.service.AlmacenService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.commerce.ecommerce.service.SucursalService;
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
@RequestMapping("/almacen")
public class AlmacenRest {
    @Autowired
    private AlmacenService almacenService;
    @Autowired
    private SucursalService sucursalService;

    @GetMapping(path = "/read")
    public ResponseEntity<List<Almacen>> getAll(){
        return ResponseEntity.ok(almacenService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Almacen> get(@PathVariable Long id){
        try{
            Almacen almacen = almacenService.findById(id).orElseThrow(() -> new Exception("Rol no encontrado"));
            return ResponseEntity.ok(almacen);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<Almacen> store(@RequestBody AlmacenDTO almacen){
        try {
            Sucursal sucursal = sucursalService.findById(almacen.getId_sucursal()).orElseThrow();
            Almacen nuevo = new Almacen();
            nuevo.setDescripcion(almacen.getDescripcion());
            nuevo.setTipo(almacen.getTipo());
            nuevo.setNumero(almacen.getNumero());
            nuevo.setId_sucursal(sucursal);
            almacenService.save(nuevo);
            return ResponseEntity.created(new URI("/almacen/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (almacenService.existsById(id)) {
                almacenService.deleteById(id); // Elimina el si existe
                return ResponseEntity.ok().build(); // Respuesta 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Almacen> update(@PathVariable Long id, @RequestBody AlmacenDTO almacenActualizado) {
        try {
            Optional<Almacen> almacenExistente = almacenService.findById(id);
            Sucursal sucursal = sucursalService.findById(almacenActualizado.getId_sucursal()).orElseThrow();

            if (almacenExistente.isPresent()) {
                Almacen almacen = almacenExistente.get();
                almacen.setNumero(almacenActualizado.getNumero());
                almacen.setTipo(almacenActualizado.getTipo());
                almacen.setDescripcion(almacenActualizado.getDescripcion());
                almacen.setId_sucursal(sucursal);

                almacenService.save(almacen);

                return ResponseEntity.ok(almacen);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
