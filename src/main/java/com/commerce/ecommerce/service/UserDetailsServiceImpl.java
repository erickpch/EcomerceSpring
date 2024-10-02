package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Rol;
import com.commerce.ecommerce.model.RolPermiso;
import com.commerce.ecommerce.model.Usuario;
import com.commerce.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Buscar al usuario por nombre de usuario
        Usuario userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        // Obtener el rol del usuario
        Rol userRole = userEntity.getId_rol();  // Obtiene el rol único del usuario



        Collection<? extends GrantedAuthority> authorities = userEntity.getId_rol().getRol_permiso_id().stream().map(permition->
                        new SimpleGrantedAuthority("ROLE_".concat(permition.getId_permiso().getPermiso())))
                        .collect(Collectors.toSet());


        //GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userRole.getNombre());

        // Crear una lista de autoridades (en este caso, solo una autoridad)
        //Collection<GrantedAuthority> authorities = Collections.singleton(authority);

        // Retornar el objeto UserDetails con las credenciales y autoridad del usuario
        return new User(userEntity.getUsername(), userEntity.getPassword(), true, true, true, true, authorities);
    }
}
