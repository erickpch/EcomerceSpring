package com.commerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name = "caja_usuarios")
public class CajaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate fecha;
    private LocalTime hora_inicio;
    private LocalTime hora_final;
    private Double monto;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario id_usuario;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_caja")
    private Caja id_caja;

    @JsonIgnore
    @OneToMany
    private List<Orden> ordenes;


}
