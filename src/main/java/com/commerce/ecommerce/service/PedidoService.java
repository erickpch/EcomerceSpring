package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.Pedido;
import com.commerce.ecommerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> finById(Long id){
        return pedidoRepository.findById(id);
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }
}
