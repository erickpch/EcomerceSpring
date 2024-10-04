package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.DTO.FacturaDTO;
import com.commerce.ecommerce.model.Factura;
import com.commerce.ecommerce.model.Orden;
import com.commerce.ecommerce.service.FacturaService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.commerce.ecommerce.service.OrdenService;
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


@PreAuthorize("hasRole('factura')")
@RestController
@RequestMapping("/factura")
public class FacturaRest {
    @Autowired
    private FacturaService facturaService;

    @Autowired
    private OrdenService ordenService;

        @GetMapping(path = "/read")
    private ResponseEntity<List<Factura>> getAllAsistencia(){
        return ResponseEntity.ok(facturaService.findAll());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<Factura> getAsistencia(@PathVariable Long id){
        try{
            Factura factura = facturaService.findById(id).orElseThrow(() -> new Exception("Rol no encontrado"));
            return ResponseEntity.ok(factura);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/crear")
    private ResponseEntity<Factura> store(@RequestBody FacturaDTO factura){
        try {
            Factura nuevo = new Factura();
            Orden orden = ordenService.findById(factura.getId_orden()).orElseThrow();

            nuevo.setCorreo(factura.getCorreo());
            nuevo.setAnulado(false);
            nuevo.setNit(factura.getNit());
            nuevo.setDireccion(factura.getDireccion());
            nuevo.setIva(orden.getTotal_descuento() * 0.13);
            nuevo.setNombre(factura.getNombre());
            nuevo.setId_orden(orden);

            facturaService.save(nuevo);

            return ResponseEntity.created(new URI("/factura/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (facturaService.existsById(id)) {
                facturaService.deleteById(id); // Elimina el si existe
                return ResponseEntity.ok().build(); // Respuesta 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Factura> update(@PathVariable Long id, @RequestBody FacturaDTO factura) {
        try {
            Optional<Factura> facturaExistente = facturaService.findById(id);

            if (facturaExistente.isPresent()) {
                Factura nuevo = facturaExistente.get();
                Orden orden = ordenService.findById(factura.getId_orden()).orElseThrow();

                nuevo.setCorreo(factura.getCorreo());
                nuevo.setAnulado(false);
                nuevo.setNit(factura.getNit());
                nuevo.setDireccion(factura.getDireccion());
                nuevo.setIva(orden.getTotal_descuento() * 0.13);
                nuevo.setNombre(factura.getNombre());
                nuevo.setId_orden(orden);

                facturaService.save(nuevo);

                return ResponseEntity.ok(nuevo);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
