package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.DTO.CajaDTO;
import com.commerce.ecommerce.model.Caja;
import com.commerce.ecommerce.model.Sucursal;
import com.commerce.ecommerce.service.CajaService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.commerce.ecommerce.service.SucursalService;
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
@PreAuthorize("hasRole('caja')")
@RestController
@RequestMapping("/caja")
public class CajaRest {
    @Autowired
    private CajaService cajaService;

    @Autowired
    private SucursalService sucursalService;

    @GetMapping(path = "/read")
    private ResponseEntity<List<Caja>> getAllAsistencia(){
        return ResponseEntity.ok(cajaService.findAll());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<Caja> getAsistencia(@PathVariable Long id){
        try{
            Caja caja = cajaService.findById(id).orElseThrow(() -> new Exception("Rol no encontrado"));
            return ResponseEntity.ok(caja);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/crear")
    private ResponseEntity<Caja> store(@RequestBody CajaDTO caja){
        try {
            Sucursal sucursal = sucursalService.findById(caja.getId_sucursal()).orElseThrow(() -> new Exception("no encontrado"));
            Caja nuevo = new Caja();

            nuevo.setNumero(caja.getNumero());
            nuevo.setId_sucursal(sucursal);

            Caja creado= cajaService.save(nuevo);
            return ResponseEntity.created(new URI("/caja/crear/"+nuevo.getId())).body(creado);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (cajaService.existsById(id)) {
                cajaService.deleteById(id); // Elimina el si existe
                return ResponseEntity.ok().build(); // Respuesta 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Caja> update(@PathVariable Long id, @RequestBody CajaDTO cajaActualizado) {
        try {
            Optional<Caja> cajaExistente = cajaService.findById(id);

            if (cajaExistente.isPresent()) {
                Caja caja = cajaExistente.get();
                Sucursal sucursal = sucursalService.findById(cajaActualizado.getId_sucursal()).orElseThrow(() -> new Exception("no encontrado"));
                caja.setNumero(cajaActualizado.getNumero());
                caja.setId_sucursal(sucursal);

                cajaService.save(caja);

                return ResponseEntity.ok(caja);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
