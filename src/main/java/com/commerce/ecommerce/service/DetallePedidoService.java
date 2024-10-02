package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.DetallePedido;
import com.commerce.ecommerce.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetallePedidoService {
    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    public DetallePedido save(DetallePedido detalleNuevo) {
        return detallePedidoRepository.save(detalleNuevo);
    }

    public boolean existsById(Long id) {
        return detallePedidoRepository.existsById(id);
    }

    public void deleteById(Long id) {
        detallePedidoRepository.deleteById(id);
    }

    public Optional<DetallePedido> findById(Long id) {
        return detallePedidoRepository.findById(id);
    }
}
