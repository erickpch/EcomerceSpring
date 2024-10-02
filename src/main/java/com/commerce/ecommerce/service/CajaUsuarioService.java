package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.CajaUsuario;
import com.commerce.ecommerce.repository.CajaUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CajaUsuarioService {
    @Autowired
    private CajaUsuarioRepository cajaUsuarioRepository;

    public List<CajaUsuario> findAll() {
        return cajaUsuarioRepository.findAll();
    }

    public CajaUsuario save(CajaUsuario cajaUsuario) {
        return cajaUsuarioRepository.save(cajaUsuario);
    }

    public Optional<CajaUsuario> findById(Long idCajaUsuario) {
        return cajaUsuarioRepository.findById(idCajaUsuario);
    }


    public void deleteById(Long id) {
        cajaUsuarioRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return cajaUsuarioRepository.existsById(id);
    }

    public Optional<CajaUsuario> findByIdUsuarioFecha(Long idUsuario, LocalDate fechaActual) {
        return  cajaUsuarioRepository.findByIdUsuarioFecha(idUsuario,fechaActual);
    }

    public Optional<CajaUsuario> CajaAbierta(Long id, LocalDate fechaActual) {
        return  cajaUsuarioRepository.CajaAbierta(id,fechaActual);
    }
}
