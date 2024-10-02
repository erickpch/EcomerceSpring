package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.model.Categoria;
import com.commerce.ecommerce.service.CategoriaService;

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
@RequestMapping("/categoria")
public class CategoriaRest {
    @Autowired
    private CategoriaService categoriaService;
        @GetMapping(path = "/read")
    private ResponseEntity<List<Categoria>> getAllAsistencia(){
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<Categoria> getAsistencia(@PathVariable Long id){
        try{
            Categoria categoria = categoriaService.findById(id).orElseThrow(() -> new Exception("Rol no encontrado"));
            return ResponseEntity.ok(categoria);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/crear")
    private ResponseEntity<Categoria> store(@RequestBody Categoria categoria){
        try {
            Categoria nuevo = categoriaService.save(categoria);
            return ResponseEntity.created(new URI("/categoria/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (categoriaService.existsById(id)) {
                categoriaService.deleteById(id); // Elimina el si existe
                return ResponseEntity.ok().build(); // Respuesta 200 OK
            } else {
                return ResponseEntity.notFound().build(); // Respuesta 404 Not Found si no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Respuesta 500 si ocurre algún error inesperado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoriaActualizado) {
        try {
            Optional<Categoria> categoriaExistente = categoriaService.findById(id);

            if (categoriaExistente.isPresent()) {
                Categoria categoria = categoriaExistente.get();
                categoria.setNombre(categoriaActualizado.getNombre());
                categoria.setDescripcion(categoriaActualizado.getDescripcion());

                categoriaService.save(categoria);

                return ResponseEntity.ok(categoria);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
