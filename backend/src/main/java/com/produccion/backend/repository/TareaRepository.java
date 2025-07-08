package com.produccion.backend.repository;

import com.produccion.backend.model.Tarea;
import com.produccion.backend.enums.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

    long countByUsuarioIdAndEstadoIn(Long usuarioId, List<EstadoTarea> estados);

    List<Tarea> findByUsuarioId(Long usuarioId);
}
