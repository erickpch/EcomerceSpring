package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Almacen;
import com.commerce.ecommerce.model.Producto;
import com.commerce.ecommerce.model.ProductoAlmacen;

import com.commerce.ecommerce.repository.ProductoAlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoAlmacenService {
    @Autowired
    ProductoAlmacenRepository productoAlmacenRepository;

    public boolean existe(Long id_almacen, Long id_producto){
        Long cantidad = productoAlmacenRepository.verificarProducto(id_almacen,id_producto);
        if (cantidad>0){
            return true;
        }
        else {
            return false;
        }
    }

    public Optional<ProductoAlmacen> findById(Long id) {
        return productoAlmacenRepository.findById(id);
    }

    public Optional<ProductoAlmacen> traerProducto(Long id_almacen, Long id_producto ){
        return productoAlmacenRepository.traerProducto(id_almacen,id_producto);
    }

    public ProductoAlmacen save(ProductoAlmacen productoAlmacen) {
        return productoAlmacenRepository.save(productoAlmacen);
    }


    public List<ProductoAlmacen> findAll() {
        return productoAlmacenRepository.findAll();
    }
}
