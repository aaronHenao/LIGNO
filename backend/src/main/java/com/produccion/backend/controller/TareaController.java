package com.produccion.backend.controller;

import com.produccion.backend.dto.ActualizarTareaRequest;
import com.produccion.backend.model.Tarea;
import com.produccion.backend.security.JwtService;
import com.produccion.backend.service.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;
    private final JwtService jwtService;

    @GetMapping("/mis-tareas")
    public ResponseEntity<List<Tarea>> obtenerMisTareas(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Quita "Bearer "
        String correo = jwtService.extractUsername(token);
        List<Tarea> tareas = tareaService.obtenerTareasUsuario(correo);
        return ResponseEntity.ok(tareas);
    }

    @PutMapping("/actualizar-estado")
    public ResponseEntity<Tarea> actualizarEstadoTarea(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ActualizarTareaRequest request) {

        String token = authHeader.substring(7);
        String correo = jwtService.extractUsername(token);
        Tarea tareaActualizada = tareaService.actualizarEstadoTarea(correo, request.tareaId(), request.nuevoEstado());
        return ResponseEntity.ok(tareaActualizada);
    }

}
