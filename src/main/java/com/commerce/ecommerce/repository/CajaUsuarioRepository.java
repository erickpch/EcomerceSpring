package com.commerce.ecommerce.repository;

import com.commerce.ecommerce.model.CajaUsuario;
import com.commerce.ecommerce.model.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface CajaUsuarioRepository extends JpaRepository<CajaUsuario, Long> {

    @Query("SELECT c FROM CajaUsuario c WHERE c.id_usuario.id = :idUsuario AND c.fecha = :fecha")
    Optional<CajaUsuario> findByIdUsuarioFecha(@Param("idUsuario") Long idUsuario, @Param("fecha") LocalDate fecha);

    @Query("SELECT c FROM CajaUsuario c WHERE c.id_usuario.id = :idUsuario AND c.fecha = :fecha")
    Optional<CajaUsuario> CajaAbierta(@Param("idUsuario") Long idUsuario, @Param("fecha") LocalDate fecha);

}
