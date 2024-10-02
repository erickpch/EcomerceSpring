package com.commerce.ecommerce.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table (name = "permisos")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String permiso;
}
