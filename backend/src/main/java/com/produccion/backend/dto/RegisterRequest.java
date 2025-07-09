package com.produccion.backend.dto;

import com.produccion.backend.enums.RolGeneral;
import com.produccion.backend.enums.RolProduccion;

public record RegisterRequest(
    String nombre,
    String correo,
    String password,
    Long empresaId,
    RolGeneral rolGeneral,
    RolProduccion rolProduccion
) {}
