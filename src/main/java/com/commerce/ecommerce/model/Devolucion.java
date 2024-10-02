package com.commerce.ecommerce.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "devoluciones")
public class Devolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int cantidad;
    private LocalDate fecha;
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "id_producto_almacen")
    private OrdenProducto id_orden_producto;

}
