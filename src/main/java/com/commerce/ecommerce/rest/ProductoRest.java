package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.DTO.Ecommerce;
import com.commerce.ecommerce.DTO.ProductoDTO;
import com.commerce.ecommerce.model.Categoria;
import com.commerce.ecommerce.model.Producto;
import com.commerce.ecommerce.model.ProductoAlmacen;
import com.commerce.ecommerce.service.CategoriaService;
import com.commerce.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@PreAuthorize("hasRole('producto')")
@RestController
@RequestMapping("/producto")
public class ProductoRest {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;
    private static final String UPLOAD_DIR = "uploads/images/";

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listar (){
        return ResponseEntity.ok(productoService.findAll());
    }

    @PostMapping("/crear")
    public ResponseEntity<Producto> uploadImage( @RequestParam("nombre") String nombre,
                                                 @RequestParam("precio") Double precio,
                                                 @RequestParam("descripcion") String descripcion,
                                                 @RequestParam("id_categoria") Long idCategoria,
                                                 @RequestParam("talla") String talla,
                                                 @RequestParam("color") String color,
                                                 @RequestPart("foto") MultipartFile foto) {

        try {


            Categoria categoria = categoriaService.findById(idCategoria).orElseThrow();
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setDescripcion(descripcion);
            producto.setId_categoria(categoria);
            producto.setTalla(talla);
            producto.setColor(color);
            producto.setFoto(foto.getBytes()); // Guardar la imagen como un array de bytes

            Producto productoGuardado = productoService.save(producto);

            return ResponseEntity.ok(productoGuardado);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        try {
            Optional<Producto> productoExistente = productoService.findById(id);

            if (productoExistente.isPresent()) {
                return ResponseEntity.ok(productoExistente.get()); //
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/ecommerce")
    public ResponseEntity<List<Ecommerce>> traerTotal(){
        List<Ecommerce> lista = new ArrayList<>();
        try {
            List<Producto> productos = productoService.findAll();
            productos.forEach(p->{
                Ecommerce nuevo = new Ecommerce();
                nuevo.setDescripcion(p.getDescripcion());
                nuevo.setNombre(p.getNombre());
                nuevo.setFoto(p.getFoto());
                nuevo.setId_categoria(p.getId_categoria().getId());
                nuevo.setColor(p.getColor());
                nuevo.setTalla(p.getTalla());
                nuevo.setPrecio(p.getPrecio());
                int cantidad = 0;
                List<ProductoAlmacen> lista_almacen = p.getId_producto_almacen();
                int i = 0;
                while(i<lista_almacen.size()){
                    cantidad+= lista_almacen.get(i).getCantidad();
                    i++;
                }
                nuevo.setCantidad(cantidad);
                lista.add(nuevo);

            });
            return ResponseEntity.ok(lista);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto( @PathVariable("id") Long id,
                                                    @RequestParam("nombre") String nombre,
                                                    @RequestParam("precio") Double precio,
                                                    @RequestParam("descripcion") String descripcion,
                                                    @RequestParam("id_categoria") Long idCategoria,
                                                    @RequestParam("talla") String talla,
                                                    @RequestParam("color") String color,
                                                    @RequestPart("foto") MultipartFile foto) {

        try {
            Optional<Producto> productoExistente = productoService.findById(id);
            Categoria categoria = categoriaService.findById(idCategoria).orElseThrow();
            if (productoExistente.isPresent()) {
                Producto producto = productoExistente.get();


                producto.setNombre(nombre);
                producto.setPrecio(precio);
                producto.setDescripcion(descripcion);
                producto.setId_categoria(categoria);
                producto.setTalla(talla);
                producto.setColor(color);


                MultipartFile nuevaImagen = foto;
                if (nuevaImagen != null && !nuevaImagen.isEmpty()) {
                    producto.setFoto(nuevaImagen.getBytes());
                }

                Producto productoActualizado = productoService.save(producto);

                return ResponseEntity.ok(productoActualizado);
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 si el producto no existe
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Retorna 500 en caso de error
        }
    }
}
