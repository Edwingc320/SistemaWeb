package com.example.sistemaweb.sistemaweb.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sistemaweb.sistemaweb.Entities.*;
import com.example.sistemaweb.sistemaweb.Repositories.*;
import com.example.sistemaweb.sistemaweb.Services.*;



// Controlador GrupoMateriaController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materia-grupo")
public class GrupoMateriaController {

    @Autowired
    private GrupoMateriaService grupoMateriaService;

    // Método para crear la relación entre Materia y Grupo
    /* 
    @PostMapping("/asignar")
    public String asignarMateriaAGrupo(@RequestParam("id_materia") Long idMateria,
                                       @RequestParam("id_grupo") Long idGrupo) {
        // Llamar al servicio para asignar la materia al grupo
        grupoMateriaService.asignarMateriaAGrupo(idMateria, idGrupo);
        return "Relación asignada correctamente";
    }
    */
}


