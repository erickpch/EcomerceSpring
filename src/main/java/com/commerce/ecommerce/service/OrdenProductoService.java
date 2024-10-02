package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.OrdenProducto;
import com.commerce.ecommerce.repository.OrdenProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenProductoService {
    @Autowired
    private OrdenProductoRepository ordenProductoRepository;

    public List<OrdenProducto> findAll() {
        return ordenProductoRepository.findAll();
    }

    public OrdenProducto save(OrdenProducto ordenProducto) {
        return ordenProductoRepository.save(ordenProducto);
    }

    public Optional<OrdenProducto> findById(Long idOrdenProducto) {
        return ordenProductoRepository.findById(idOrdenProducto);
    }


    public void deleteById(Long id) {
        ordenProductoRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return ordenProductoRepository.existsById(id);
    }
}
