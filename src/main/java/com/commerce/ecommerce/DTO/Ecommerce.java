package com.commerce.ecommerce.DTO;

import lombok.Data;

@Data
public class Ecommerce {
    private String nombre;
    private Double precio;
    private String descripcion;
    private Long id_categoria;
    private byte[] foto;
    private String talla;
    private String color;
    private int cantidad;
}
