package com.commerce.ecommerce.rest;

import com.commerce.ecommerce.service.OrdenProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orden_producto")
public class OrdenProductoRest {
    @Autowired
    private OrdenProductoService ordenProductoService;
}
