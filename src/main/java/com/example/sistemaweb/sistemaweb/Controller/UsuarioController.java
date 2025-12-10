package com.example.sistemaweb.sistemaweb.Controller;

import com.example.sistemaweb.sistemaweb.Entities.Usuario;
import com.example.sistemaweb.sistemaweb.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }


    @GetMapping("/Usuarios")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());               // Para el formulario de crear
        model.addAttribute("usuarios", usuarioService.findAll());   // Para la tabla
        return "Proyecto/Usuarios";
    }

    

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario, Model model) {
        usuarioService.crearUsuario(usuario);
        model.addAttribute("success", "Usuario '" + usuario.getUsername() + "' creado exitosamente.");
        model.addAttribute("usuario", new Usuario());
        return "Proyecto/crearUsuario";
    }

    @PostMapping("/actualizar/{id}")
public ResponseEntity<String> actualizarUsuario(
        @PathVariable Long id,
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String rol,
        @RequestParam String activo) {  // Recibimos string

    Usuario usuario = new Usuario();
    usuario.setUsername(username);

    if(password != null && !password.isEmpty()) {
        usuario.setPassword(password);
    }

    usuario.setRol(rol);
    usuario.setActivo(Boolean.parseBoolean(activo)); // Convertimos string a boolean

    usuarioService.actualizarUsuario(id, usuario);
    return ResponseEntity.ok("Usuario actualizado");
}




    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuario/listar";
    }
}
