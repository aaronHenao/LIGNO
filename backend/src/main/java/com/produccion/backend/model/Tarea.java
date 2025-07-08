package com.produccion.backend.model;

import com.produccion.backend.enums.EstadoTarea;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private EtapaProduccion etapa;

    @ManyToOne
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private EstadoTarea estado;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
