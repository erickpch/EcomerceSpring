package com.commerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table (name = "cajas")
public class Caja {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int numero;

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal id_sucursal;

    @JsonIgnore
    @OneToMany(mappedBy = "id_caja", cascade = CascadeType.ALL)
    private List<CajaUsuario> id_caja_usuario;
}
