package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Devolucion;
import com.commerce.ecommerce.repository.DevolucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DevolucionService {
    @Autowired
    private DevolucionRepository devolucionRepository;

    public List<Devolucion> findAll() {
        return devolucionRepository.findAll();
    }

    public Devolucion save(Devolucion devolucion) {
        return devolucionRepository.save(devolucion);
    }

    public Optional<Devolucion> findById(Long idDevolucion) {
        return devolucionRepository.findById(idDevolucion);
    }


    public void deleteById(Long id) {
        devolucionRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return devolucionRepository.existsById(id);
    }
}
