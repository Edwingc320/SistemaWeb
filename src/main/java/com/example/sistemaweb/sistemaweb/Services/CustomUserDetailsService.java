package com.example.sistemaweb.sistemaweb.Services;

import com.example.sistemaweb.sistemaweb.Entities.Usuario;
import com.example.sistemaweb.sistemaweb.Repositories.UsuarioRepository;
import com.example.sistemaweb.sistemaweb.Services.UsuarioService;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Usuario u = repo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario"));


        return User.builder()
                .username(u.getUsername())
                .password(u.getPassword())
                .roles(u.getRol())  // ADMIN o MAESTRO
                .build();
    }
}
