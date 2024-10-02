package com.commerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orden_productos")
public class OrdenProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_orden")
    private Orden id_orden;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_producto_almacen")
    private ProductoAlmacen id_producto_almacen;

    private int cantidad;
    private double precio;
}
