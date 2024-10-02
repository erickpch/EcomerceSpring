package com.commerce.ecommerce.rest;


import com.commerce.ecommerce.DTO.UsuarioCrearDTO;
import com.commerce.ecommerce.model.Caja;
import com.commerce.ecommerce.model.Rol;
import com.commerce.ecommerce.model.Usuario;
import com.commerce.ecommerce.service.RolService;
import com.commerce.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioRest {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(path = "/read")
    private ResponseEntity<List<Usuario>> getAllAsistencia(){
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping(path = "/by/{user}")
    private  ResponseEntity<Usuario> usuarioByUsername(@PathVariable String user){
        try {
            Usuario buscado = usuarioService.findByUsername(user).orElseThrow();
            return ResponseEntity.ok(buscado);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PostMapping(path = "/crear")
    private ResponseEntity<Usuario> store(@RequestBody UsuarioCrearDTO c){
        try {
            Rol rol = rolService.findById(c.getId_rol()).orElseThrow(() -> new Exception("Rol no encontrado"));

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setEmail(c.getEmail());
            nuevoUsuario.setUsername(c.getUsername());
            nuevoUsuario.setPassword(passwordEncoder.encode(c.getPassword()));
            nuevoUsuario.setId_rol(rol);  // Asigna el rol encontrado

            Usuario nuevo = usuarioService.save(nuevoUsuario);
            return ResponseEntity.created(new URI("/aulas/crear/" + nuevo.getId())).body(nuevo);


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<Usuario> get(@PathVariable Long id){
        try{
            Usuario usuario = usuarioService.findById(id).orElseThrow(() -> new Exception("no encontrado"));
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (usuarioService.existsById(id)) {
                usuarioService.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody UsuarioCrearDTO usuarioActualizado) {
        try {
            Optional<Usuario> existente = usuarioService.findById(id);

            if (existente.isPresent()) {
                Rol rol = rolService.findById(usuarioActualizado.getId_rol()).orElseThrow(() -> new Exception("no encontrado"));


                Usuario usuario = existente.get();
                usuario.setUsername(usuarioActualizado.getUsername());
                usuario.setEmail(usuarioActualizado.getEmail());
                usuario.setPassword(usuarioActualizado.getPassword());
                usuario.setId_rol(rol);

                // Guardar el rol actualizado
               usuarioService.save(usuario);

                return ResponseEntity.ok(usuario); // Retorna el rol actualizado con 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 si el rol no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}
