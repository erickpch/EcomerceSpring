package com.commerce.ecommerce.DTO;

import lombok.Data;

@Data
public class DetallePedidoModificarDTO {
    private Long id_producto;
    private int cantidad;
    private Long id;
    private Long id_caja_usuario;
}
