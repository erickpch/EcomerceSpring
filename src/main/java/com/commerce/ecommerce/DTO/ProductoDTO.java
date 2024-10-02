package com.commerce.ecommerce.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductoDTO {
    private String nombre;
    private Double precio;
    private String descripcion;
    private Long id_categoria;
    private byte[] foto;
    private String talla;
    private String color;
}
