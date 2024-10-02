package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Orden;
import com.commerce.ecommerce.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {
    @Autowired
    private OrdenRepository ordenRepository;

    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    public Optional<Orden> findById(Long idOrden) {
        return ordenRepository.findById(idOrden);
    }


    public void deleteById(Long id) {
        ordenRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return ordenRepository.existsById(id);
    }
}
