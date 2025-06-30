package com.example.sistemaweb.sistemaweb.Controller;


import com.example.sistemaweb.sistemaweb.Entities.*;
import com.example.sistemaweb.sistemaweb.Repositories.*;
import com.example.sistemaweb.sistemaweb.Services.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class AlumnoController {


    @Autowired
    private CalificacionesService calificacionesService;


    


    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    
    //CARGAR ALUMNOS DESDE EXCEL

    /* 
    @PostMapping("/alumno_grupo")
    public String alumno_grupo(@RequestParam("file") MultipartFile file, 
    @RequestParam("idGrupo") Long idGrupo, 
    @RequestParam("idMateria") Long idMateria, Model model) {
        try {
            Grupo grupo = grupoRepository.findById(idGrupo).orElseThrow(() -> new Exception("Grupo no encontrado"));
            Materia materia = materiaRepository.findById(idMateria).orElseThrow(() -> new Exception("Materia no encontrada"));
            calificacionesService.cargarAlumnosDesdeExcelCompletoREs(file, grupo, materia);
            //alumnoService.cargarAlumnosDesdeExcel(file, grupo);
            model.addAttribute("success", "Alumnos cargados y asignados al grupo correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Hubo un error al cargar el archivo: " + e.getMessage());
        }
        return "pruebaGrupoSemestre"; // Regresa a la misma vista
    }
    */


    @PostMapping("/alumno_grupo")
    public String alumno_grupo(@RequestParam("file") MultipartFile file, 
    @RequestParam("idGrupo") Long idGrupo, 
    @RequestParam("idMateria") Long idMateria, Model model) {
        try {
            Grupo grupo = grupoRepository.findById(idGrupo).orElseThrow(() -> new Exception("Grupo no encontrado"));
            Materia materia = materiaRepository.findById(idMateria).orElseThrow(() -> new Exception("Materia no encontrada"));
            calificacionesService.cargarAlumnosDesdeExcelCompletoREs(file, grupo, materia);
            //alumnoService.cargarAlumnosDesdeExcel(file, grupo);
            model.addAttribute("success", "Alumnos cargados y asignados al grupo correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Hubo un error al cargar el archivo: " + e.getMessage());
        }
        return "Proyecto/cargarCalificaciones"; // Regresa a la misma vista
    }
    
    


    



    
}
