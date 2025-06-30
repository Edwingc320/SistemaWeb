/*package com.example.sistemaweb.sistemaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemawebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemawebApplication.class, args);
	}

}
*/

package com.example.sistemaweb.sistemaweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.sistemaweb.sistemaweb.Entities.Usuario;
import com.example.sistemaweb.sistemaweb.Repositories.UsuarioRepository;

@SpringBootApplication
public class SistemawebApplication implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepo;

    public static void main(String[] args) {
        SpringApplication.run(SistemawebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String username = "master";
        // Solo crea el usuario si no existe
        if (usuarioRepo.findByUsername(username).isEmpty()) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode("servermaster1");

            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(hashedPassword);

            usuarioRepo.save(usuario);
            System.out.println("Usuario '" + username + "' creado exitosamente.");
        }
    }
}
