// === 4. UsuarioRepository.java ===
package com.example.sistemaweb.sistemaweb.Repositories;



import com.example.sistemaweb.sistemaweb.Entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
