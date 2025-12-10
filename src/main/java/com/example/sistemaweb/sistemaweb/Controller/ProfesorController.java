package com.example.sistemaweb.sistemaweb.Controller;

import com.example.sistemaweb.sistemaweb.Entities.Profesor;
import com.example.sistemaweb.sistemaweb.Services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profesor")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @PostMapping("/agregarProfesor")
    public ResponseEntity<String> agregarProfesor(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidoPaterno") String apellidoPaterno,
            @RequestParam("apellidoMaterno") String apellidoMaterno,
            @RequestParam("clave") String clave) {
        
        // Se crea una instancia de Profesor con los datos recibidos
        Profesor profesor = new Profesor();
        profesor.setNombre(nombre);
        profesor.setApellidoPaterno(apellidoPaterno);
        profesor.setApellidoMaterno(apellidoMaterno);
        profesor.setClave(clave);
        
        Profesor profesorGuardado = profesorService.saveProfesor(profesor);
        
        return ResponseEntity.ok("Profesor creado con ID: " + profesorGuardado.getIdProfesor());
    }

    
}