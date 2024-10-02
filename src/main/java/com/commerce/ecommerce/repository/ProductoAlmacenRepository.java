package com.commerce.ecommerce.repository;


import com.commerce.ecommerce.model.ProductoAlmacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoAlmacenRepository extends JpaRepository<ProductoAlmacen,Long> {

    @Query("SELECT COUNT(c) FROM ProductoAlmacen c WHERE c.id_almacen.id = :idAlmacen AND c.id_producto.id = :idProducto")
    Long verificarProducto(@Param("idAlmacen") Long idAlmacen, @Param("idProducto") Long idProducto);

    @Query("SELECT c FROM ProductoAlmacen c WHERE c.id_almacen.id = :idAlmacen AND c.id_producto.id = :idProducto")
    Optional<ProductoAlmacen> traerProducto(@Param("idAlmacen") Long idAlmacen, @Param("idProducto") Long idProducto);


}
