package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Permiso;
import com.commerce.ecommerce.model.Rol;
import com.commerce.ecommerce.model.RolPermiso;
import com.commerce.ecommerce.repository.RolPermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolPermisoService {
    @Autowired
    private RolPermisoRepository rolPermisoRepository;

    public List<RolPermiso> findAll() {
        return rolPermisoRepository.findAll();
    }

    public RolPermiso save(RolPermiso rolPermiso) {
        return rolPermisoRepository.save(rolPermiso);
    }

    public Optional<RolPermiso> findById(Long idRolPermiso) {
        return rolPermisoRepository.findById(idRolPermiso);
    }


    public void deleteById(Long id) {
        rolPermisoRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return rolPermisoRepository.existsById(id);
    }

    public Optional<RolPermiso> findByIdRolAndIdPermiso(Long idPermiso, Long idRol) {
        return rolPermisoRepository.findByIdPermisoAndIdRol(idPermiso, idRol);
    }
}
