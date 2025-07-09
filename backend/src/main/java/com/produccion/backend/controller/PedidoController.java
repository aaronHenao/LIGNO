package com.produccion.backend.controller;

import com.produccion.backend.dto.CrearPedidoRequest;
import com.produccion.backend.dto.PedidoResumenDTO;
import com.produccion.backend.model.Pedido;
import com.produccion.backend.service.PedidoService;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List; 

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody CrearPedidoRequest request) {
        Pedido pedido = pedidoService.crearPedido(request);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/resumen")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PedidoResumenDTO>> obtenerResumenPedidos() {
        return ResponseEntity.ok(pedidoService.obtenerResumenPedidos());
    }

}
