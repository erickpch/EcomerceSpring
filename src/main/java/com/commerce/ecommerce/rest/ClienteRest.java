package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.DTO.ClienteDTO;
import com.commerce.ecommerce.DTO.ClienteUsuarioDTO;
import com.commerce.ecommerce.model.Cliente;
import com.commerce.ecommerce.model.Rol;
import com.commerce.ecommerce.model.Usuario;
import com.commerce.ecommerce.service.ClienteService;
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
@RequestMapping("/cliente")
public class ClienteRest {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;

    @GetMapping(path = "/read")
    private ResponseEntity<List<Cliente>> getAllAsistencia(){
        return ResponseEntity.ok(clienteService.findAll());
    }

    @PostMapping(path = "/crear")
    private ResponseEntity<Cliente> store(@RequestBody ClienteDTO c){
        try {

            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNombre(c.getNombre());
            nuevoCliente.setApellido(c.getApellido());
            nuevoCliente.setCi(c.getCi());
            nuevoCliente.setEdad(c.getEdad());
            nuevoCliente.setGenero(c.getGenero());
            nuevoCliente.setTelefono(c.getTelefono());
            nuevoCliente.setDireccion(c.getDireccion());

            Cliente nuevo = clienteService.save(nuevoCliente);
            return ResponseEntity.created(new URI("/cliente/crear/" + nuevo.getId())).body(nuevo);


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(path = "/crearUsuario")
    private ResponseEntity<Cliente> storeUsuario(@RequestBody ClienteUsuarioDTO c){
        try {

            Rol rol = rolService.findById(c.getId_rol()).orElseThrow(() -> new Exception("Rol no encontrado"));

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setEmail(c.getEmail());
            nuevoUsuario.setUsername(c.getUsername());
            nuevoUsuario.setPassword(passwordEncoder.encode(c.getPassword()));
            nuevoUsuario.setId_rol(rol);

            nuevoUsuario = usuarioService.save(nuevoUsuario);

            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNombre(c.getNombre());
            nuevoCliente.setApellido(c.getApellido());
            nuevoCliente.setCi(c.getCi());
            nuevoCliente.setEdad(c.getEdad());
            nuevoCliente.setGenero(c.getGenero());
            nuevoCliente.setTelefono(c.getTelefono());
            nuevoCliente.setDireccion(c.getDireccion());
            nuevoCliente.setId_usuario(nuevoUsuario);

            Cliente nuevo = clienteService.save(nuevoCliente);
            return ResponseEntity.created(new URI("/cliente/crear/" + nuevo.getId())).body(nuevo);


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<Cliente> get(@PathVariable Long id){
        try{
            Cliente cliente = clienteService.findById(id).orElseThrow(() -> new Exception("no encontrado"));
            return ResponseEntity.ok(cliente);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (clienteService.existsById(id)) {
                clienteService.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody ClienteDTO actualizado) {
        try {
            Optional<Cliente> modificado = clienteService.findById(id);

            if (modificado.isPresent()) {



                Cliente cliente = modificado.get();
                cliente.setNombre(actualizado.getNombre());
                cliente.setEdad(actualizado.getEdad());
                cliente.setCi(actualizado.getCi());
                cliente.setGenero(actualizado.getGenero());
                cliente.setDireccion(actualizado.getDireccion());
                cliente.setApellido(actualizado.getApellido());
                cliente.setTelefono(actualizado.getTelefono());

                // Guardar el rol actualizado
               clienteService.save(cliente);

                return ResponseEntity.ok(cliente); // Retorna el rol actualizado con 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 si el rol no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Retorna 500 si hay un error
        }
    }
}
