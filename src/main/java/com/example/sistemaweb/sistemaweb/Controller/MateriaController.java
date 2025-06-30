package com.example.sistemaweb.sistemaweb.Controller;

import com.example.sistemaweb.sistemaweb.Entities.*;
import com.example.sistemaweb.sistemaweb.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {

   
    /* 
    @PostMapping("/asignar")
    public String asignarMateriasAGrupo(@RequestParam int idGrupo, @RequestBody List<Long> idMaterias) {
        try {
            asignarMateriasService.asignarMateriasAGrupo(idGrupo, idMaterias);
            return "Materias asignadas correctamente al grupo con ID " + idGrupo;
        } catch (Exception e) {
            return "Error al asignar materias: " + e.getMessage();
        }
    }

    // Endpoint para guardar una lista de materias
    @PostMapping("/guardar")
    public List<MateriaDTO> guardarMaterias(@RequestBody List<Long> idMaterias) {
        try {
            return asignarMateriasService.guardarMaterias(idMaterias);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar las materias: " + e.getMessage());
        }
    }
    */


    @Autowired
    private GrupoService grupoService;

  
    // Endpoint para obtener los grupos por semestre
    @GetMapping("/grupos/{idSemestre}")
    public List<Grupo> obtenerGruposPorSemestre(@PathVariable long idSemestre) {
        return grupoService.obtenerGruposPorSemestre(idSemestre);
    }
     @Autowired
    private MateriaService materiaService;

    


    @GetMapping("/por-grupo/{idGrupo}")
    public ResponseEntity<List<Materia>> obtenerMateriasPorGrupo(@PathVariable Long idGrupo) {
        

        long startTime = System.currentTimeMillis();
        List<Materia> materias = materiaService.obtenerMateriasPorGrupo(idGrupo);
        long endTime = System.currentTimeMillis();
        System.out.println("Tiempo de respuesta: " + (endTime - startTime) + "ms");

       // startTime = System.currentTimeMillis();
        System.out.println(materias);
        return ResponseEntity.ok(materias);
    }

    

}
