package com.produccion.backend.repository;

import com.produccion.backend.model.Tarea;
import com.produccion.backend.model.Usuario;
import com.produccion.backend.enums.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByUsuarioAndEstadoIn(Usuario usuario, List<EstadoTarea> estados);
    long countByUsuarioIdAndEstadoIn(Long usuarioId, List<EstadoTarea> estados);
}
