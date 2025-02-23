package com.example.sistemaweb.sistemaweb.Controller;

import com.example.sistemaweb.sistemaweb.Entities.Periodo;
import com.example.sistemaweb.sistemaweb.Services.PeriodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/periodo")
public class PeriodoController {

    @Autowired
    private PeriodoService periodoService;

    @PostMapping("/agregarPeriodo")
    public ResponseEntity<String> agregarPeriodo(@RequestParam("anio") int anio,
                                                 @RequestParam("semestre") String semestreStr) {
        // Validar que el semestre sea 'A' o 'B'
        if (semestreStr == null || semestreStr.isEmpty()) {
            return ResponseEntity.badRequest().body("El semestre es requerido.");
        }
        char semestre = semestreStr.charAt(0);
        if (semestre != 'A' && semestre != 'B') {
            return ResponseEntity.badRequest().body("El semestre debe ser 'A' o 'B'.");
        }
        
        // Crear y guardar el periodo
        Periodo periodo = new Periodo(anio, semestre);
        Periodo periodoGuardado = periodoService.savePeriodo(periodo);
        
        return ResponseEntity.ok("Periodo creado con ID: " + periodoGuardado.getIdPeriodo());
    }
}

