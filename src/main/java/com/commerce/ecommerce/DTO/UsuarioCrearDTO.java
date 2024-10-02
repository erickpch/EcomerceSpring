package com.commerce.ecommerce.DTO;

import lombok.Data;

@Data
public class UsuarioCrearDTO {
    private String email;
    private String username;
    private String password;
    private Long id_rol;
}
