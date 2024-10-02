package com.commerce.ecommerce.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "metodo_pagos")
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;
    private String descripcion;
}
