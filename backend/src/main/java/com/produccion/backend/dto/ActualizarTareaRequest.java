package com.produccion.backend.dto;

import com.produccion.backend.enums.EstadoTarea;

public record ActualizarTareaRequest(
    Long tareaId,
    EstadoTarea nuevoEstado
) {}
