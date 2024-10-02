package com.commerce.ecommerce.DTO;

import java.time.LocalDate;

import lombok.Data;
@Data
public class DevolucionDTO {
    private int cantidad;
    private LocalDate fecha;
    private String motivo;
    private Long id_rol;
}
