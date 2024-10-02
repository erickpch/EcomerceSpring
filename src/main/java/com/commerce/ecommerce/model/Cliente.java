package com.commerce.ecommerce.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int ci;
    private String nombre;
    private String apellido;
    private int telefono;
    private String direccion;
    private String genero;
    private int edad;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = true)
    private Usuario id_usuario;
}
