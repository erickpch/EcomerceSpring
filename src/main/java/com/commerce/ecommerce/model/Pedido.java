package com.commerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_almacen")
    private Almacen id_almacen;

    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedor id_proveedor;

    @JsonIgnore
    @OneToMany(mappedBy = "id_pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> id_detalle_pedido;

}
