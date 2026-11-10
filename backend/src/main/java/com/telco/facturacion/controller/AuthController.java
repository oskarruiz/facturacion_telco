package com.telco.facturacion.controller;

import com.telco.facturacion.dto.LoginRequest;
import com.telco.facturacion.dto.LoginResponse;
import com.telco.facturacion.entity.Usuario;
import com.telco.facturacion.repository.UsuarioRepository;
import com.telco.facturacion.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            
            Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            return ResponseEntity.ok(LoginResponse.builder()
                    .token(token)
                    .username(usuario.getUsername())
                    .nombreCompleto(usuario.getNombre() + " " + usuario.getApellido())
                    .rol(usuario.getRol())
                    .mensaje("Login exitoso")
                    .build());
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(LoginResponse.builder()
                            .mensaje("Credenciales inválidas")
                            .build());
        }
    }
}
