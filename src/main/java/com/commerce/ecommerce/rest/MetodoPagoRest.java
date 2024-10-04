package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.model.MetodoPago;
import com.commerce.ecommerce.service.MetodoPagoService;

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


@PreAuthorize("hasRole('metodo_pago')")
@RestController
@RequestMapping("/metodo_pago")
public class MetodoPagoRest {
    @Autowired
    private MetodoPagoService metodoPagoService;
        @GetMapping(path = "/read")
    private ResponseEntity<List<MetodoPago>> getAllAsistencia(){
        return ResponseEntity.ok(metodoPagoService.findAll());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<MetodoPago> getAsistencia(@PathVariable Long id){
        try{
            MetodoPago metodoPago = metodoPagoService.findById(id).orElseThrow(() -> new Exception("Rol no encontrado"));
            return ResponseEntity.ok(metodoPago);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/crear")
    private ResponseEntity<MetodoPago> store(@RequestBody MetodoPago metodoPago){
        try {
            MetodoPago nuevo = metodoPagoService.save(metodoPago);
            return ResponseEntity.created(new URI("/metodoPago/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (metodoPagoService.existsById(id)) {
                metodoPagoService.deleteById(id); // Elimina el si existe
                return ResponseEntity.ok().build(); // Respuesta 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> update(@PathVariable Long id, @RequestBody MetodoPago metodoPagoActualizado) {
        try {
            Optional<MetodoPago> metodoPagoExistente = metodoPagoService.findById(id);

            if (metodoPagoExistente.isPresent()) {
                MetodoPago metodoPago = metodoPagoExistente.get();
                metodoPago.setNombre(metodoPagoActualizado.getNombre());
                metodoPago.setDescripcion(metodoPagoActualizado.getDescripcion());

                metodoPagoService.save(metodoPago);

                return ResponseEntity.ok(metodoPago);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
