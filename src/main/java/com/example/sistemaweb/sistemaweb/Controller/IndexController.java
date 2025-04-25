package com.example.sistemaweb.sistemaweb.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sistemaweb.sistemaweb.Entities.*;
import com.example.sistemaweb.sistemaweb.Repositories.*;
import com.example.sistemaweb.sistemaweb.Services.*;

@Controller
public class IndexController {


    

    @Autowired
    private GrupoService grupoService;

    @GetMapping("/go")
    public String showIndex() {
        grupoService.obtenerNombreGrupoPeriodo();
        return "go";
    }

    
    @GetMapping("/añadirGrupos")
    public String añadirGrupos() {
        return "Proyecto/añadirGrupos";  // Esto le dice a Spring Boot que debe usar añadirGrupos.html desde templates
    }

    @GetMapping("/añadirPeriodo")
    public String añadirPeriodo() {
        return "Proyecto/añadirPeriodo";  // Esto le dice a Spring Boot que debe usar añadirGrupos.html desde templates
    }

    @GetMapping("/cargarCalificaciones")
    public String cargarCalificaciones() {
        return "Proyecto/cargarCalificaciones";  // Esto le dice a Spring Boot que debe usar añadirGrupos.html desde templates
    }

    @GetMapping("/gestionGrupos")
    public String gestionGrupos() {
        return "Proyecto/gestionGrupos";  // Esto le dice a Spring Boot que debe usar añadirGrupos.html desde templates
    }

    @GetMapping("/indexF")
    public String indexF() {
        return "Proyecto/index";  // Esto le dice a Spring Boot que debe usar añadirGrupos.html desde templates
    }

    @GetMapping("/lay")
    public String lay() {
        return "pages/home";  // Esto le dice a Spring Boot que debe usar añadirGrupos.html desde templates
    }

    @GetMapping("/agregarProfesores")
    public String agregarProfesores() {
        return "Proyecto/agregarProfesor";  // Esto le dice a Spring Boot que debe usar añadirGrupos.html desde templates
    }


    
}
