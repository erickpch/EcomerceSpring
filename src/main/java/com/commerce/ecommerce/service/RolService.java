package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Rol;
import com.commerce.ecommerce.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    public Optional<Rol> findById(Long idRol) {
        return rolRepository.findById(idRol);
    }


    public void deleteById(Long id) {
        rolRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return rolRepository.existsById(id);
    }
}
