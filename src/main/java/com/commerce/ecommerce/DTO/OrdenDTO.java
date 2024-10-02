package com.commerce.ecommerce.DTO;

import lombok.Data;

import java.util.List;


@Data
public class OrdenDTO {

    private double descuento;
    private Long id_usuario;
    private Long id_metodo_pago;
    private Long id_cliente;
    private List<DetalleOrdenDTO> lista_ordenes;
}
