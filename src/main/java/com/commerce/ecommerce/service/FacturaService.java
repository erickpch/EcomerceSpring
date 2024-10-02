package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Factura;
import com.commerce.ecommerce.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

    public Optional<Factura> findById(Long idFactura) {
        return facturaRepository.findById(idFactura);
    }


    public void deleteById(Long id) {
        facturaRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return facturaRepository.existsById(id);
    }
}
