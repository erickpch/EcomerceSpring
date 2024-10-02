package com.commerce.ecommerce.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PedidoModificarDTO {
    private Long id_proveedor;
    private Long id_almacen;
    List<DetallePedidoModificarDTO> listaProductos;
}
