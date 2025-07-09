package com.produccion.backend.service;

import com.produccion.backend.dto.CrearPedidoRequest;
import com.produccion.backend.dto.PedidoResumenDTO;
import com.produccion.backend.dto.TareaResumenDTO;
import com.produccion.backend.enums.EstadoPedido;
import com.produccion.backend.enums.EstadoTarea;
import com.produccion.backend.model.*;
import com.produccion.backend.repository.*;
import com.produccion.backend.enums.RolProduccion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.produccion.backend.model.Pedido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final EmpresaRepository empresaRepo;
    private final EtapaProduccionRepository etapaRepo;
    private final TareaRepository tareaRepo;
    private final UsuarioRepository usuarioRepo;

    public Pedido crearPedido(CrearPedidoRequest request) {
        Empresa empresa = empresaRepo.findById(request.empresaId())
            .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        List<EtapaProduccion> etapas = etapaRepo.findAllById(request.etapasIds());

        Pedido pedido = new Pedido();
        pedido.setCodigo(request.codigo());
        pedido.setFecha(LocalDate.now());
        pedido.setEspecificacion(request.especificacion());
        pedido.setEmpresa(empresa);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setEtapas(etapas);
        pedido = pedidoRepo.save(pedido);

        // Generar tareas por etapa
        for (EtapaProduccion etapa : etapas) {
            RolProduccion rol = obtenerRolProduccion(etapa.getNombre());
            Usuario trabajadorAsignado = buscarTrabajadorConMenosCarga(empresa.getId(), rol);

            Tarea tarea = new Tarea();
            tarea.setPedido(pedido);
            tarea.setEtapa(etapa);
            tarea.setUsuario(trabajadorAsignado);
            tarea.setEstado(EstadoTarea.PENDIENTE);
            tarea.setFechaInicio(null);
            tarea.setFechaFin(null);

            tareaRepo.save(tarea);
        }

        return pedido;
    }

    public List<PedidoResumenDTO> obtenerResumenPedidos() {
        List<Pedido> pedidos = pedidoRepo.findAll();

        return pedidos.stream()
            .map(pedido -> new PedidoResumenDTO(
                pedido.getId(),
                pedido.getEspecificacion(),
                pedido.getTareas().stream()
                    .map(t -> new TareaResumenDTO(t.getId(), t.getTipo(), t.getEstado()))
                    .toList()
            ))
            .toList();
    }

    private Usuario buscarTrabajadorConMenosCarga(Long empresaId, RolProduccion rol) {
        List<Usuario> candidatos = usuarioRepo.findByEmpresaIdAndRolProduccion(empresaId, rol);
        if (candidatos.isEmpty()) {
            throw new RuntimeException("No hay trabajadores para la etapa: " + rol);
        }

        return candidatos.stream()
            .min(Comparator.comparingLong(u -> tareaRepo.countByUsuarioIdAndEstadoIn(
                u.getId(), List.of(EstadoTarea.PENDIENTE, EstadoTarea.EN_PROCESO))))
            .orElseThrow();
    }

    private RolProduccion obtenerRolProduccion(String nombreEtapa) {
        try {
            return RolProduccion.valueOf(nombreEtapa.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Etapa no reconocida: " + nombreEtapa);
        }
    }

}
