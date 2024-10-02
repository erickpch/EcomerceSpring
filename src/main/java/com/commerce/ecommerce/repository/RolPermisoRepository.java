package com.commerce.ecommerce.repository;

import com.commerce.ecommerce.model.Permiso;
import com.commerce.ecommerce.model.Rol;
import com.commerce.ecommerce.model.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {

    @Query("SELECT rp FROM RolPermiso rp WHERE rp.id_permiso.id = :idPermiso AND rp.id_rol.id = :idRol")
    Optional<RolPermiso> findByIdPermisoAndIdRol(@Param("idPermiso") Long idPermiso, @Param("idRol") Long idRol);

}
