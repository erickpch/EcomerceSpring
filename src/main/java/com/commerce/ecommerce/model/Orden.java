package com.commerce.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name = "ordenes")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int numero_orden;
    private double total;
    private double descuento;
    private double total_descuento;
    private boolean anulado;
    private LocalDate fecha;
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name = "id_caja_usuarios")
    private CajaUsuario id_caja_usuario;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente id_cliente;

    @ManyToOne
    @JoinColumn(name = "id_metodo_pago")
    private  MetodoPago id_metodo_pago;

    // Relación con la tabla de asociación
    @OneToMany(mappedBy = "id_orden", cascade = CascadeType.ALL)
    private List<OrdenProducto> id_orden_productos;

}
