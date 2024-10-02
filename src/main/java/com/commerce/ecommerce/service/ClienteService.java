package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Cliente;
import com.commerce.ecommerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(Long idCliente) {
        return clienteRepository.findById(idCliente);
    }


    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return clienteRepository.existsById(id);
    }
}
