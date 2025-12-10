package com.example.sistemaweb.sistemaweb.Controller;

import com.example.sistemaweb.sistemaweb.Entities.LoginRequest;
import com.example.sistemaweb.sistemaweb.Entities.AuthResponse;
import com.example.sistemaweb.sistemaweb.Entities.Usuario;
import com.example.sistemaweb.sistemaweb.Services.UsuarioService;
import com.example.sistemaweb.sistemaweb.Seguridad.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UsuarioService usuarioService, JwtService jwtService,
                          AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/loginApi")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (!auth.isAuthenticated()) {
            return ResponseEntity.status(401)
                    .body(Map.of("mensaje", "Credenciales inválidas"));
        }

        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (!usuario.isActivo()) {
            return ResponseEntity.status(403).body(Map.of("mensaje", "Usuario inactivo"));
        }

        String token = jwtService.generarToken(usuario);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", usuario.getUsername(),
                "rol", usuario.getRol(),
                "activo", usuario.isActivo()
        ));
    }
}
