package com.produccion.backend.dto;

import java.util.List;

public record CrearPedidoRequest(
    String codigo,
    String especificacion,
    Long empresaId,
    List<Long> etapasIds
) {}
