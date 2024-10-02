package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.DTO.Ecommerce;
import com.commerce.ecommerce.EcommerceApplication;
import com.commerce.ecommerce.model.*;
import com.commerce.ecommerce.service.AlmacenService;
import com.commerce.ecommerce.service.CajaUsuarioService;
import com.commerce.ecommerce.service.ProductoAlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/producto_almacen")
public class ProductoAlmacenRest {
    @Autowired
    ProductoAlmacenService productoAlmacenService = new ProductoAlmacenService();
    @Autowired
    CajaUsuarioService cajaUsuarioService= new CajaUsuarioService();
    @Autowired
    AlmacenService almacenService= new AlmacenService();

    @GetMapping("listar/{id}")
    public ResponseEntity<List<Ecommerce>> listar (@PathVariable Long id){
        try {
            LocalDate fechaActual = LocalDate.now();
            CajaUsuario caja = cajaUsuarioService.CajaAbierta(id,fechaActual).orElseThrow();
            Sucursal sucursal = caja.getId_caja().getId_sucursal();
            Almacen almacen = almacenService.findByIdSucursal(sucursal.getId()).orElseThrow(null);

            List<ProductoAlmacen> productoAlmacen = productoAlmacenService.findAll();
            List<Ecommerce> elegidos = new ArrayList<>();

            productoAlmacen.forEach(p->{
                if(p.getId_almacen() == almacen){
                    Ecommerce nuevo = new Ecommerce();
                    nuevo.setDescripcion(p.getId_producto().getDescripcion());
                    nuevo.setNombre(p.getId_producto().getNombre());
                    nuevo.setFoto(p.getId_producto().getFoto());
                    nuevo.setId_categoria(p.getId_producto().getId_categoria().getId());
                    nuevo.setColor(p.getId_producto().getColor());
                    nuevo.setTalla(p.getId_producto().getTalla());
                    nuevo.setPrecio(p.getId_producto().getPrecio());
                    nuevo.setCantidad(p.getCantidad());
                    elegidos.add(nuevo);
                }
            });
            return ResponseEntity.ok(elegidos);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }

    }
}
