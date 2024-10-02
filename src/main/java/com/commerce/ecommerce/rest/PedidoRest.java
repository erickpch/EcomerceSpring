package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.DTO.PedidoDTO;
import com.commerce.ecommerce.DTO.PedidoModificarDTO;
import com.commerce.ecommerce.model.*;
import com.commerce.ecommerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/pedido")
public class PedidoRest {
    @Autowired
    PedidoService pedidoService;
    @Autowired
    DetallePedidoService detallePedidoService;
    @Autowired
    AlmacenService almacenService;
    @Autowired
    ProductoAlmacenService productoAlmacenService;
    @Autowired
    ProveedorService proveedorService;
    @Autowired
    ProductoService productoService;

    @GetMapping(path = "/read")
    private ResponseEntity<List<Pedido>> get(){
        return ResponseEntity.ok().body(pedidoService.findAll());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<Pedido> get(@PathVariable Long id){
        return ResponseEntity.ok().body(pedidoService.findById(id).orElseThrow());
    }


    @PostMapping(path = "/crear")
    private ResponseEntity<List<DetallePedido>> store(@RequestBody PedidoDTO c){
        try {
            Almacen almacen = almacenService.findById(c.getId_almacen()).orElseThrow(null);
            Proveedor proveedor = proveedorService.findById(c.getId_proveedor()).orElseThrow(null);
            LocalDate fechaActual = LocalDate.now();

            Pedido pedido = new Pedido();
            pedido.setId_almacen(almacen);
            pedido.setId_proveedor(proveedor);
            pedido.setFecha(fechaActual);

            pedido = pedidoService.save(pedido);

            List<DetallePedido> listaProducto = new ArrayList<DetallePedido>();

            Pedido finalPedido= pedidoService.finById(pedido.getId()).orElseThrow();

            c.getListaProductos().forEach(p->{
                Producto producto = productoService.findById(p.getId_producto()).orElseThrow(null);


                DetallePedido detalleNuevo = new DetallePedido();
                detalleNuevo.setCantidad(p.getCantidad());
                detalleNuevo.setId_pedido(finalPedido);
                detalleNuevo.setId_producto(producto);
                detalleNuevo= detallePedidoService.save(detalleNuevo);
                listaProducto.add(detalleNuevo);
                if(productoAlmacenService.existe(c.getId_almacen(), producto.getId())){

                    ProductoAlmacen productoAlmacen = productoAlmacenService.traerProducto(c.getId_almacen(),producto.getId()).orElseThrow();
                    productoAlmacen.setCantidad(productoAlmacen.getCantidad()+p.getCantidad());
                    productoAlmacenService.save(productoAlmacen);

                }else{
                    ProductoAlmacen productoAlmacen = new ProductoAlmacen();
                    productoAlmacen.setCantidad(p.getCantidad());
                    productoAlmacen.setId_producto(producto);
                    productoAlmacen.setId_almacen(almacen);
                    productoAlmacenService.save(productoAlmacen);


                }
            });

            return ResponseEntity.ok(listaProducto);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PutMapping(path = "/{id}")
    private ResponseEntity<List<DetallePedido>> update(@PathVariable Long id, @RequestBody PedidoModificarDTO c) {
        try {
            // Buscar el pedido existente por su ID
            Pedido pedidoExistente = pedidoService.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

            // Actualizar el almacen y proveedor del pedido si es necesario
            Almacen almacen = almacenService.findById(c.getId_almacen()).orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
            Proveedor proveedor = proveedorService.findById(c.getId_proveedor()).orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

            pedidoExistente.setId_almacen(almacen);
            pedidoExistente.setId_proveedor(proveedor);
            pedidoExistente = pedidoService.save(pedidoExistente);

            List<DetallePedido> listaProducto = new ArrayList<>();
            Pedido pedido = pedidoExistente;


            c.getListaProductos().forEach(p -> {
                System.out.println("----------------------------------");
                System.out.println(p.getId_producto());
                DetallePedido detalle = detallePedidoService.findById(p.getId()).orElseThrow();
                ProductoAlmacen productoAlmacen = productoAlmacenService.traerProducto(c.getId_almacen(), p.getId_producto()).orElseThrow(() -> new RuntimeException("Producto no encontrado en el almacén"));
                productoAlmacen.setCantidad(productoAlmacen.getCantidad() - detalle.getCantidad());
                if(productoAlmacen.getCantidad()<0){
                    throw new IllegalArgumentException("La cantidad de producto en el almacén no puede ser menor a 0");
                }

                if (detallePedidoService.existsById(p.getId())) {
                    detallePedidoService.deleteById(p.getId());
                }
            });

            c.getListaProductos().forEach(p -> {
                Producto producto = productoService.findById(p.getId_producto()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                DetallePedido detalleNuevo = new DetallePedido();
                detalleNuevo.setCantidad(p.getCantidad());
                detalleNuevo.setId_pedido(pedido);
                detalleNuevo.setId_producto(producto);
                detalleNuevo = detallePedidoService.save(detalleNuevo);
                listaProducto.add(detalleNuevo);


                if (productoAlmacenService.existe(c.getId_almacen(), producto.getId())) {
                    ProductoAlmacen productoAlmacen = productoAlmacenService.traerProducto(c.getId_almacen(), producto.getId()).orElseThrow(() -> new RuntimeException("Producto no encontrado en el almacén"));
                    productoAlmacen.setCantidad(productoAlmacen.getCantidad() + p.getCantidad());
                    productoAlmacenService.save(productoAlmacen);
                } else {
                    ProductoAlmacen productoAlmacen = new ProductoAlmacen();
                    productoAlmacen.setCantidad(p.getCantidad());
                    productoAlmacen.setId_producto(producto);
                    productoAlmacen.setId_almacen(almacen);
                    productoAlmacenService.save(productoAlmacen);
                }
            });

            return ResponseEntity.ok(listaProducto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            Pedido pedidoExistente = pedidoService.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
            pedidoExistente.getId_detalle_pedido().forEach(p -> {

                DetallePedido detalle = detallePedidoService.findById(p.getId()).orElseThrow();
                ProductoAlmacen productoAlmacen = productoAlmacenService.traerProducto(pedidoExistente.getId_almacen().getId(), p.getId_producto().getId()).orElseThrow(() -> new RuntimeException("Producto no encontrado en el almacén"));
                productoAlmacen.setCantidad(productoAlmacen.getCantidad() - detalle.getCantidad());
                if(productoAlmacen.getCantidad()<0){
                    throw new IllegalArgumentException("La cantidad de producto en el almacén no puede ser menor a 0");
                }

                if (detallePedidoService.existsById(id)) {
                    detallePedidoService.deleteById(id);
                }
            });
            pedidoService.deleteById(pedidoExistente.getId());
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
