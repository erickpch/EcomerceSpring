package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.MetodoPago;
import com.commerce.ecommerce.repository.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService {
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPago> findAll() {
        return metodoPagoRepository.findAll();
    }

    public MetodoPago save(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    public Optional<MetodoPago> findById(Long idMetodoPago) {
        return metodoPagoRepository.findById(idMetodoPago);
    }


    public void deleteById(Long id) {
        metodoPagoRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return metodoPagoRepository.existsById(id);
    }
}
