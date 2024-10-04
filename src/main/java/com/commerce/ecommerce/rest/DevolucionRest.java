package com.commerce.ecommerce.rest;


import com.commerce.ecommerce.DTO.DevolucionDTO;
import com.commerce.ecommerce.model.Devolucion;
import com.commerce.ecommerce.model.OrdenProducto;

import com.commerce.ecommerce.service.DevolucionService;
import com.commerce.ecommerce.service.OrdenProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@PreAuthorize("hasRole('devolucion')")
@RestController
@RequestMapping("/devolucion")
public class DevolucionRest {
    @Autowired
    private DevolucionService devolucionService;

    @Autowired
    private OrdenProductoService ordenProducto;

    @GetMapping(path = "/read")
    public ResponseEntity<List<Devolucion>> getAll(){
        return ResponseEntity.ok(devolucionService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Devolucion> getDevolucion (@PathVariable Long id){
        try{
            Devolucion devolucion = devolucionService.findById(id).orElseThrow(() -> new Exception("no encontrado"));
            return ResponseEntity.ok(devolucion);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(path = "/crear")
    public ResponseEntity<Devolucion> store(@RequestBody DevolucionDTO c){
        try {
            OrdenProducto orden_producto = ordenProducto.findById(c.getId_rol()).orElseThrow(() -> new Exception("no encontrado"));

            Devolucion nuevoDevolucion = new Devolucion();
            nuevoDevolucion.setFecha(c.getFecha());
            nuevoDevolucion.setCantidad(c.getCantidad());
            nuevoDevolucion.setMotivo(c.getMotivo());
            nuevoDevolucion.setId_orden_producto(orden_producto);

            Devolucion nuevo = devolucionService.save(nuevoDevolucion);
            return ResponseEntity.created(new URI("/crear/" + nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (devolucionService.existsById(id)) {
                devolucionService.deleteById(id); // Elimina el si existe
                return ResponseEntity.ok().build(); // Respuesta 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Devolucion> update(@PathVariable Long id, @RequestBody DevolucionDTO devolucionActualizado) {
        try {
            Optional<Devolucion> devolucionExistente = devolucionService.findById(id);

            if (devolucionExistente.isPresent()) {
                Devolucion devolucion = devolucionExistente.get();
                OrdenProducto orden_producto = ordenProducto.findById(devolucionActualizado.getId_rol()).orElseThrow(() -> new Exception("Rol no encontrado"));
               
                devolucion.setFecha(devolucionActualizado.getFecha());
                devolucion.setCantidad(devolucionActualizado.getCantidad());
                devolucion.setMotivo(devolucionActualizado.getMotivo());
                devolucion.setId_orden_producto(orden_producto);


                devolucionService.save(devolucion);

                return ResponseEntity.ok(devolucion);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(path = "detalle/{id}")
    private ResponseEntity<List<Devolucion>> getDevolucionByDetalle (@PathVariable Long id){
        try{
            List<Devolucion> devoluciones = devolucionService.findAll();
            List<Devolucion> elegido = new ArrayList<>();
            for (Devolucion devolucion : devoluciones) {
                if(devolucion.getId_orden_producto().getId_orden().getId()== id){
                    elegido.add(devolucion);
                }
            }

            return ResponseEntity.ok(elegido);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
