package com.produccion.backend.dto;

import com.produccion.backend.enums.EstadoTarea;
import com.produccion.backend.enums.RolProduccion;


public record TareaResumenDTO(
    Long tareaId,
    RolProduccion tipo,
    EstadoTarea estado

) {}

