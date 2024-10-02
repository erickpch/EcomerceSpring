package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Permiso;
import com.commerce.ecommerce.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoService {
    @Autowired
    private PermisoRepository permisoRepository;

    public List<Permiso> findAll() {
        return permisoRepository.findAll();
    }

    public Permiso save(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public Optional<Permiso> findById(Long idPermiso) {
        return permisoRepository.findById(idPermiso);
    }


    public void deleteById(Long id) {
        permisoRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return permisoRepository.existsById(id);
    }
}