package com.produccion.backend.model;

import com.produccion.backend.enums.RolGeneral;
import com.produccion.backend.enums.RolProduccion;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String correo;

    private String password;

    @Enumerated(EnumType.STRING)
    private RolGeneral rolGeneral;

    @Enumerated(EnumType.STRING)
    private RolProduccion rolProduccion;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
}

