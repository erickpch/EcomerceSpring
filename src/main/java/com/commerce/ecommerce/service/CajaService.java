package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Caja;
import com.commerce.ecommerce.repository.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CajaService {
    @Autowired
    private CajaRepository cajaRepository;

    public List<Caja> findAll() {
        return cajaRepository.findAll();
    }

    public Caja save(Caja caja) {
        return cajaRepository.save(caja);
    }

    public Optional<Caja> findById(Long idCaja) {
        return cajaRepository.findById(idCaja);
    }


    public void deleteById(Long id) {
        cajaRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return cajaRepository.existsById(id);
    }
}
