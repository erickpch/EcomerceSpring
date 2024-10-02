package com.commerce.ecommerce.DTO;

import lombok.Data;

@Data
public class ClienteUsuarioDTO {
    private int ci;
    private String nombre;
    private String apellido;
    private int telefono;
    private String direccion;
    private String genero;
    private int edad;


    private String email;
    private String username;
    private String password;
    private Long id_rol;
}
