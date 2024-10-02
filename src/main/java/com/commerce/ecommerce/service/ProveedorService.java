package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Proveedor;
import com.commerce.ecommerce.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository marcaRepository;

    public List<Proveedor> findAll() {
        return marcaRepository.findAll();
    }

    public Proveedor save(Proveedor marca) {
        return marcaRepository.save(marca);
    }

    public Optional<Proveedor> findById(Long idMarca) {
        return marcaRepository.findById(idMarca);
    }


    public void deleteById(Long id) {
        marcaRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return marcaRepository.existsById(id);
    }
}
