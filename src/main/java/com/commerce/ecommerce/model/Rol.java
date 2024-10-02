package com.commerce.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "id_rol", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RolPermiso> rol_permiso_id;
}
