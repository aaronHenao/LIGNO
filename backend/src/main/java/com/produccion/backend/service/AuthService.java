package com.produccion.backend.service;

import com.produccion.backend.dto.AuthRequest;
import com.produccion.backend.dto.AuthResponse;
import com.produccion.backend.dto.RegisterRequest;
import com.produccion.backend.enums.RolGeneral;
import com.produccion.backend.model.Empresa;
import com.produccion.backend.model.Usuario;
import com.produccion.backend.repository.EmpresaRepository;
import com.produccion.backend.repository.UsuarioRepository;
import com.produccion.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final EmpresaRepository empresaRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        Empresa empresa = empresaRepo.findById(request.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setCorreo(request.correo());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRolGeneral(request.rolGeneral());
        usuario.setRolProduccion(request.rolProduccion());
        usuario.setEmpresa(empresa);

        usuarioRepo.save(usuario);

        String token = jwtService.generateToken(usuario.getCorreo());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        Usuario usuario = usuarioRepo.findByCorreo(request.correo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.password(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtService.generateToken(usuario.getCorreo());
        return new AuthResponse(token);
    }
}
