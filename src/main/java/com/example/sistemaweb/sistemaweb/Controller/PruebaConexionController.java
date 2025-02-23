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
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemaweb.sistemaweb.Entities.*;
import com.example.sistemaweb.sistemaweb.Repositories.*;
import com.example.sistemaweb.sistemaweb.Services.*;

@RestController
public class PruebaConexionController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaGrupoRepository materiaGrupoRepository;


    @Autowired
    private GrupoService grupoService;

    @GetMapping("/probar-conexion")
    public String probarConexion() {
        long total = alumnoRepository.count();
        Long semestre = 1L;
        List<Grupo> obtenerGrupos = grupoService.obtenerGruposPorSemestre(semestre);


        
        List<Materia> materia = materiaRepository.findAll();
        List<GrupoMateria> materia_grupo = materiaGrupoRepository.findAll();


        //return "✅ Alumnos registrados: " + total;
        return "MATERIAS: " + materia_grupo ;
        //return "GRUPOS_SEMESTRE: " + obtenerGrupos;
 

        
        
        
    
        
    }

    @GetMapping("/probar-conexion2")
    public void testAsignarMateriasAGrupo() {
        int idGrupo = 1; // ID estático para el grupo
        List<Long> idMaterias = List.of(1L, 2L, 3L); // IDs estáticos de materias
    
        for (Long idMateria : idMaterias) {
            try {
                System.out.println("Probando asignación: Materia " + idMateria + ", Grupo " + idGrupo);
                materiaGrupoRepository.asignarMateriaAGrupo(idMateria.intValue(), idGrupo);
                System.out.println("Asignación exitosa: Materia " + idMateria + ", Grupo " + idGrupo);
            } catch (Exception e) {
                System.err.println("Fallo al asignar Materia " + idMateria + " al Grupo " + idGrupo);
                e.printStackTrace();
            }
        }
    }
        

}
