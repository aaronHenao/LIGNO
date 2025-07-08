package com.produccion.backend.model;

import com.produccion.backend.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @Lob
    private String especificacion;

    @ManyToOne
    private Empresa empresa;

    @ManyToMany
    @JoinTable(
        name = "pedido_etapas",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "etapa_id")
    )
    private List<EtapaProduccion> etapas;
}
