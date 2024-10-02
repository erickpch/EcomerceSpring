package com.commerce.ecommerce.DTO;

import lombok.Data;

@Data
public class FacturaDTO {
    private String nombre;
    private int nit;
    private String direccion;
    private String correo;
    private Long id_orden;
}
