

// === 5. UsuarioService.java ===
package com.example.sistemaweb.sistemaweb.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.*;


import com.example.sistemaweb.sistemaweb.Entities.Usuario;
import com.example.sistemaweb.sistemaweb.Repositories.UsuarioRepository;

import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles("USER") // Cambia si tienes roles reales
                .build();
    }

}