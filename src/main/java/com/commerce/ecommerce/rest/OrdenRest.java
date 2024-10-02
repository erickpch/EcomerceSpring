package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.DTO.OrdenDTO;
import com.commerce.ecommerce.DTO.OrdenModificarDTO;
import com.commerce.ecommerce.model.*;
import com.commerce.ecommerce.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/orden")
public class OrdenRest {
    @Autowired
    OrdenService ordenService;
    @Autowired
    OrdenProductoService detalleOrdenService;
    @Autowired
    AlmacenService almacenService;
    @Autowired
    ProductoAlmacenService productoAlmacenService;
    @Autowired
    ProductoService productoService;
    @Autowired
    CajaUsuarioService cajaUsuarioService;
    @Autowired
    MetodoPagoService metodoPagoService;
    @Autowired
    ClienteService clienteService;

    @GetMapping(path = "/read")
    private ResponseEntity<List<Orden>> get(){
        return ResponseEntity.ok().body(ordenService.findAll());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<Orden> get(@PathVariable Long id){
        return ResponseEntity.ok().body(ordenService.findById(id).orElseThrow());
    }



    @PostMapping(path = "/crear")
    private ResponseEntity<Orden> store(@RequestBody OrdenDTO c){
        try {

            LocalDate fechaActual = LocalDate.now();
            CajaUsuario caja = cajaUsuarioService.CajaAbierta(c.getId_usuario(),fechaActual).orElseThrow();
            Sucursal sucursal = caja.getId_caja().getId_sucursal();

            Almacen almacen = almacenService.findByIdSucursal(sucursal.getId()).orElseThrow(null);

            MetodoPago metodoPago = metodoPagoService.findById(c.getId_metodo_pago()).orElseThrow();
            Cliente cliente = clienteService.findById(c.getId_cliente()).orElseThrow();

            LocalTime horaActual = LocalTime.now();
            double[] suma = {0.0};

            Orden orden = new Orden();
            orden.setAnulado(false);
            orden.setDescuento(c.getDescuento());
            orden.setHora(horaActual);
            orden.setId_caja_usuario(caja);
            orden.setId_metodo_pago(metodoPago);
            orden.setNumero_orden(0);
            orden.setTotal(0);
            orden.setTotal_descuento(0);
            orden.setFecha(fechaActual);
            orden.setId_cliente(cliente);

            orden = ordenService.save(orden);


            List<OrdenProducto> listaProducto = new ArrayList<OrdenProducto>();

            Orden finalOrden= orden;

            c.getLista_ordenes().forEach(p->{

                Producto producto = productoService.findById(p.getId_producto()).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            System.out.println(almacen.getId()+"--------------------------------"+producto.getId());
                ProductoAlmacen productoAlmacen = productoAlmacenService.traerProducto( almacen.getId(),producto.getId())
                        .orElseThrow(() -> new IllegalArgumentException("ProductoAlmacen no encontrado para el producto y almacén especificados"));


                OrdenProducto detalleNuevo = new OrdenProducto();

                detalleNuevo.setCantidad(p.getCantidad());

                detalleNuevo.setId_orden(finalOrden);
                detalleNuevo.setPrecio(producto.getPrecio());

                detalleNuevo.setId_producto_almacen(productoAlmacen);
                detalleNuevo= detalleOrdenService.save(detalleNuevo);


                listaProducto.add(detalleNuevo);
                productoAlmacen.setCantidad(productoAlmacen.getCantidad()-p.getCantidad());

                if(productoAlmacen.getCantidad()<0){
                    throw new IllegalArgumentException("Limite de productos excedidos");
                }

                productoAlmacenService.save(productoAlmacen);

                suma[0]+= (producto.getPrecio() * p.getCantidad());



            });

            orden.setNumero_orden(orden.getId().intValue());
            orden.setTotal(suma[0]);
            orden.setTotal_descuento(orden.getTotal()-c.getDescuento());
            orden.setId_orden_productos(listaProducto);
            caja.setMonto(caja.getMonto()+orden.getTotal_descuento());
            cajaUsuarioService.save(caja);

            return ResponseEntity.ok(orden);

        }

        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }


    @PutMapping(path = "/{id}")
    private ResponseEntity<Orden> update(@PathVariable Long id, @RequestBody OrdenModificarDTO  c) {
        try {

            Orden ordenExistente = ordenService.findById(id).orElseThrow(() -> new RuntimeException("Orden no encontrado"));
            CajaUsuario cajaUsuario = ordenExistente.getId_caja_usuario();
            Sucursal sucursal = ordenExistente.getId_caja_usuario().getId_caja().getId_sucursal();
            Almacen almacen = almacenService.findByIdSucursal(sucursal.getId()).orElseThrow(null);

            MetodoPago metodoPago = metodoPagoService.findById(c.getId_metodo_pago()).orElseThrow();
            Cliente cliente = clienteService.findById(c.getId_cliente()).orElseThrow();

            double[]suma = {0.0};
            ordenExistente.setAnulado(false);
            ordenExistente.setDescuento(c.getDescuento());
            ordenExistente.setId_metodo_pago(metodoPago);
            ordenExistente.setNumero_orden(0);
            ordenExistente.setId_cliente(cliente);

            List<OrdenProducto> listaProducto = new ArrayList<>();
            Orden orden = ordenExistente;

            System.out.println(orden.getId()+"--------------------------------");
            c.getLista_ordenes().forEach(p -> {


                OrdenProducto detalle = detalleOrdenService.findById(p.getId_producto()).orElseThrow();
                ProductoAlmacen productoAlmacen = detalle.getId_producto_almacen();
                productoAlmacen.setCantidad(productoAlmacen.getCantidad() + detalle.getCantidad());

                if (detalleOrdenService.existsById(productoAlmacen.getId())) {
                    detalleOrdenService.deleteById(productoAlmacen.getId());
                }
            });

            c.getLista_ordenes().forEach(p -> {
                OrdenProducto detalle = detalleOrdenService.findById(p.getId_producto()).orElseThrow();
                Producto producto = detalle.getId_producto_almacen().getId_producto();
                ProductoAlmacen productoAlmacen = productoAlmacenService.traerProducto(almacen.getId(), producto.getId()).orElseThrow(()
                        -> new RuntimeException("Producto no encontrado en el almacén"));
                OrdenProducto detalleNuevo = new OrdenProducto();
                detalleNuevo.setCantidad(p.getCantidad());
                detalleNuevo.setId_orden(ordenExistente);
                detalleNuevo.setPrecio(producto.getPrecio());
                detalleNuevo.setId_producto_almacen(productoAlmacen);
                detalleNuevo= detalleOrdenService.save(detalleNuevo);


                listaProducto.add(detalleNuevo);
                productoAlmacen.setCantidad(productoAlmacen.getCantidad()-p.getCantidad());
                if(productoAlmacen.getCantidad()<0){
                    throw new IllegalArgumentException("Limite de productos excedidos");
                }
                productoAlmacenService.save(productoAlmacen);
                suma[0]+= (producto.getPrecio() * p.getCantidad());
            });

            cajaUsuario.setMonto(cajaUsuario.getMonto()-ordenExistente.getTotal()+suma[0]);
            ordenExistente.setTotal(suma[0]);
            ordenExistente.setTotal_descuento(suma[0]-orden.getDescuento());
            ordenService.save(ordenExistente);

            return ResponseEntity.ok(ordenExistente);

        }  catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", e.getMessage())
                    .build();
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            Orden ordenExistente = ordenService.findById(id).orElseThrow(() -> new RuntimeException("Orden no encontrado"));
            ordenExistente.getId_orden_productos().forEach(p -> {

                OrdenProducto detalle = detalleOrdenService.findById(p.getId()).orElseThrow();
                ProductoAlmacen productoAlmacen = p.getId_producto_almacen();
                productoAlmacen.setCantidad(productoAlmacen.getCantidad() + detalle.getCantidad());

                if (detalleOrdenService.existsById(id)) {
                    detalleOrdenService.deleteById(id);
                }
            });
            ordenService.deleteById(ordenExistente.getId());
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/devolucion/{id}/{cantidad}")
    public ResponseEntity<Void> deleteDetalle(@PathVariable Long id, @PathVariable int cantidad){
        try{
            OrdenProducto ordenProducto = detalleOrdenService.findById(id).orElseThrow();
            ProductoAlmacen productoAlmacen = ordenProducto.getId_producto_almacen();

            productoAlmacen.setCantidad(productoAlmacen.getCantidad()+cantidad);

            ordenProducto.setCantidad(ordenProducto.getCantidad()-cantidad);
            double costo = cantidad * ordenProducto.getPrecio();

            if(ordenProducto.getCantidad()<= 0){
                detalleOrdenService.deleteById(id);
            }

            Orden orden= ordenProducto.getId_orden();
            orden.setTotal(orden.getTotal()-costo);
            orden.getId_caja_usuario().setMonto(orden.getId_caja_usuario().getMonto()-costo);
            orden.setTotal_descuento(orden.getTotal_descuento()-costo);

            ordenService.save(orden);
            detalleOrdenService.save(ordenProducto);
            productoAlmacenService.save(productoAlmacen);

            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
