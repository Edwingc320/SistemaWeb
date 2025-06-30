package com.example.sistemaweb.sistemaweb.Controller;


import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.example.sistemaweb.sistemaweb.Services.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class IndexController {


    

    @Autowired
    private GrupoService grupoService;

    @GetMapping("/go")
    public String showIndex() {
        grupoService.obtenerNombreGrupoPeriodo();
        return "go";
    }


    @GetMapping("/login")
    public String login() {
        return "login"; // busca templates/Proyecto/login.html
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
        return "index";  // Esto le dice a Spring Boot que debe usar añadirGrupos.html desde templates
    }
    @GetMapping("/")
    public String index() {
        return "Proyecto/index";  // Esto le dice a Spring Boot que debe usar añadirGrupos.html desde templates
    }
    /* 

     @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> attrs = new DefaultErrorAttributes()
            .getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        model.addAllAttributes(attrs);
        return "proyecto/index";
    }
        */


    @GetMapping("/lay")
    public String lay() {
        return "pages/home";  // Esto le dice a Spring Boot que debe usar añadirGrupos.html desde templates
    }

    @GetMapping("/agregarProfesores")
    public String agregarProfesores() {
        return "Proyecto/agregarProfesor";  // Esto le dice a Spring Boot que debe usar añadirGrupos.html desde templates
    }


    
}
