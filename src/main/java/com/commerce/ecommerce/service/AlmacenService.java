package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Almacen;
import com.commerce.ecommerce.model.Sucursal;
import com.commerce.ecommerce.repository.AlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlmacenService {
    @Autowired
    private AlmacenRepository almacenRepository;

    public List<Almacen> findAll() {
        return almacenRepository.findAll();
    }

    public Almacen save(Almacen almacen) {
        return almacenRepository.save(almacen);
    }

    public Optional<Almacen> findById(Long idAlmacen) {
        return almacenRepository.findById(idAlmacen);
    }


    public void deleteById(Long id) {
        almacenRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return almacenRepository.existsById(id);
    }

    public Optional<Almacen> findByIdSucursal(Long id) {
        return almacenRepository.findByIdSucursal(id);
    }
}
