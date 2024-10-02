package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Producto;
import com.commerce.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> findById(Long idProducto) {
        return productoRepository.findById(idProducto);
    }


    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return productoRepository.existsById(id);
    }
}
