package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.DTO.AbrirCajaDTO;
import com.commerce.ecommerce.DTO.CerrarCajaDTO;
import com.commerce.ecommerce.model.*;
import com.commerce.ecommerce.service.CajaService;
import com.commerce.ecommerce.service.CajaUsuarioService;
import com.commerce.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



@RestController
@RequestMapping("/caja_usuario")
public class CajaUsuarioRest {
    @Autowired
    private CajaUsuarioService cajaUsuarioService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CajaService cajaService;

    @GetMapping(path = "/read")
    private  ResponseEntity<List<CajaUsuario>> mostrar (){
        return ResponseEntity.ok().body(cajaUsuarioService.findAll());
    }

    @GetMapping(path = "/{id}")
    private  ResponseEntity<CajaUsuario> mostrarcaja (@PathVariable Long id){
        return ResponseEntity.ok().body(cajaUsuarioService.findById(id).orElseThrow());
    }

    @PostMapping(path = "/abrir")
    private ResponseEntity<CajaUsuario> registrar(@RequestBody AbrirCajaDTO abrir) {
        try {

            Usuario usuario = usuarioService.findById(abrir.getId_usuario()).orElseThrow();
            Caja caja = cajaService.findById(abrir.getId_caja()).orElseThrow();
            LocalDate fechaActual = LocalDate.now();
            LocalTime hora_actual =  LocalTime.now();

            CajaUsuario existente = cajaUsuarioService.findByIdUsuarioFecha(abrir.getId_usuario(), fechaActual).orElse(null);
            if(existente != null){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
            else{
                CajaUsuario nuevo = new CajaUsuario();
                nuevo.setId_usuario(usuario);
                nuevo.setId_caja(caja);
                nuevo.setFecha(fechaActual);
                nuevo.setHora_inicio(hora_actual);
                nuevo.setMonto(0.0);

                cajaUsuarioService.save(nuevo);
                return ResponseEntity.ok(nuevo);
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping(path = "verificar/{id}")
    private ResponseEntity<Caja> verifica(@PathVariable Long id) {

        LocalDate fechaActual = LocalDate.now();

        CajaUsuario caja = cajaUsuarioService.CajaAbierta(id,fechaActual).orElse(null);
        if (caja != null){

            Caja retorno =  caja.getId_caja();
            return ResponseEntity.ok(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "/cerrar")
    private  ResponseEntity<CajaUsuario> cierre(@RequestBody CerrarCajaDTO c){
        try {
            Optional<CajaUsuario> existente = cajaUsuarioService.findById(c.getId());
            if (existente.isPresent()) {
                LocalTime hora_actual =  LocalTime.now();
                CajaUsuario actual = existente.get();
                actual.setHora_final(hora_actual);
                actual = cajaUsuarioService.save(actual);
                return ResponseEntity.ok(actual);
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        }
        catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping(path = "/arqueo")
    private  ResponseEntity<List<Orden>> arqueo(@RequestBody CerrarCajaDTO c){
        try {
            Optional<CajaUsuario> existente = cajaUsuarioService.findById(c.getId());
            if (existente.isPresent()) {
               List<Orden> ordenes = existente.get().getOrdenes();
               return ResponseEntity.ok(ordenes);

            } else {
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

}
