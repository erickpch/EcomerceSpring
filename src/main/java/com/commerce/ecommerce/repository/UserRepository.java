package com.commerce.ecommerce.repository;

import com.commerce.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query ("select u from Usuario u where u.username = ?1")
    Optional<Usuario> getName(String username);
}