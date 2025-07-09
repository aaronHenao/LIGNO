package com.produccion.backend.dto;

import java.util.List;

public record PedidoResumenDTO(
    Long pedidoId,
    String especificacion,
    List<TareaResumenDTO> tareas
) {}
