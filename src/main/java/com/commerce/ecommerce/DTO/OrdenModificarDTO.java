package com.commerce.ecommerce.DTO;

import lombok.Data;

import java.util.List;
@Data
public class OrdenModificarDTO {

    private double descuento;
    private Long id_caja_usuario;
    private Long id_metodo_pago;
    private Long id_cliente;
    private List<DetalleModificarOrdenDTO> lista_ordenes;
}
