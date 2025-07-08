package com.produccion.backend.repository;

import com.produccion.backend.model.Usuario;
import com.produccion.backend.enums.RolProduccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findByEmpresaIdAndRolProduccion(Long empresaId, RolProduccion rolProduccion);
}

