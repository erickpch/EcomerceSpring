package com.commerce.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;
    private int nit;
    private String direccion;
    private String correo;
    private boolean anulado;
    private double iva;

    @ManyToOne
    @JoinColumn(name = "id_orden")
    private  Orden id_orden;
}
