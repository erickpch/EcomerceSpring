package com.commerce.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_pedidos")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto id_producto;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido id_pedido;
}
