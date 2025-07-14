package com.example.sistemaweb.sistemaweb.Controller;


import com.example.sistemaweb.sistemaweb.Entities.Usuario;
import com.example.sistemaweb.sistemaweb.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 1️⃣ Mostrar formulario de alta de usuario
     *    GET /usuario/nuevo
     */
    @GetMapping("/crearUsuario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "Proyecto/crearUsuario"; // Nombre de la vista Thymeleaf
    }

    /**
     * 2️⃣ Procesar formulario y guardar usuario
     *    POST /usuario/guardar
     */
    @PostMapping("/guardar")
    public String guardarUsuario(
            @ModelAttribute Usuario usuario,
            Model model) {

        // Cifrar la contraseña antes de guardar
        String rawPassword = usuario.getPassword();
        usuario.setPassword(passwordEncoder.encode(rawPassword));

        usuarioRepo.save(usuario);

        model.addAttribute("success", "Usuario '" + usuario.getUsername() + "' creado exitosamente.");
        model.addAttribute("usuario", new Usuario());  // limpiar el formulario
        return "Proyecto/crearUsuario";
    }
}

