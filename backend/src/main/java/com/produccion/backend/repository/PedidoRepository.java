package com.produccion.backend.repository;

import com.produccion.backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEmpresaId(Long empresaId);
}
