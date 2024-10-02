package com.commerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;
    private String descripcion;
    private double precio;
    private byte[] foto;
    private String talla;
    private String color;



    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria id_categoria;

    @JsonIgnore
    @OneToMany(mappedBy = "id_producto", cascade = CascadeType.ALL)
    private List<ProductoAlmacen> id_producto_almacen;
}
