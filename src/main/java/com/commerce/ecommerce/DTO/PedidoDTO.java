package com.commerce.ecommerce.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PedidoDTO {

    private Long id_proveedor;
    private Long id_almacen;
    List<DetallePedidoDTO> listaProductos;
}
