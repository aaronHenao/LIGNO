package com.produccion.backend.service;

import com.produccion.backend.enums.EstadoTarea;
import com.produccion.backend.model.Tarea;
import com.produccion.backend.model.Usuario;
import com.produccion.backend.repository.TareaRepository;
import com.produccion.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TareaService {

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Tarea> obtenerTareasUsuario(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<EstadoTarea> estados = List.of(EstadoTarea.PENDIENTE, EstadoTarea.EN_PROCESO);
        return tareaRepository.findByUsuarioAndEstadoIn(usuario, estados);
    }

    public long contarTareasUsuario(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<EstadoTarea> estados = List.of(EstadoTarea.PENDIENTE, EstadoTarea.EN_PROCESO);
        return tareaRepository.countByUsuarioIdAndEstadoIn(usuario.getId(), estados);
    }
    
    public Tarea actualizarEstadoTarea(String correo, Long tareaId, EstadoTarea nuevoEstado) {
    Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

    if (!tarea.getUsuario().getId().equals(usuario.getId())) {
        throw new RuntimeException("No puedes modificar una tarea que no es tuya");
    }

    tarea.setEstado(nuevoEstado);
    return tareaRepository.save(tarea);
    }
}


