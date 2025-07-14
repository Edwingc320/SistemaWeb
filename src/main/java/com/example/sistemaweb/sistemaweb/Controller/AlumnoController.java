package com.example.sistemaweb.sistemaweb.Controller;


import com.example.sistemaweb.sistemaweb.Entities.*;
import com.example.sistemaweb.sistemaweb.Repositories.*;
import com.example.sistemaweb.sistemaweb.Services.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class AlumnoController {


    @Autowired
    private CalificacionesService calificacionesService;


    @Autowired
    private AlumnoRepository alumnoRepo;


    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    // ① Formulario de búsqueda
    @GetMapping("/buscarAlumno")
    public String mostrarFormularioBusqueda() {
        return "Proyecto/modificarAlumno";
    }

   

    // Buscar alumnos por nombre (Ajax)
    @GetMapping("/buscar")
    @ResponseBody
    public List<Alumno> buscarAlumnosPorNombre(@RequestParam("nombre") String nombre) {
        return alumnoRepo.findByNombreContainingIgnoreCase(nombre);
    }

    // Cargar datos de un alumno para modificar (cuando se selecciona un alumno)
    @GetMapping("/modificar/{id}")
    public String cargarDatosAlumno(@PathVariable Long id, Model model) {
        Alumno alumno = alumnoRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado: " + id));
        model.addAttribute("alumno", alumno);
        return "Proyecto/modificarAlumno";
    }

    // Guardar modificación (al presionar el botón guardar)
    @PostMapping("/modificar")
    public String guardarAlumnoModificado(@ModelAttribute Alumno alumno, Model model) {
        alumnoRepo.save(alumno);
        model.addAttribute("success", "Alumno actualizado correctamente.");
        model.addAttribute("alumno", new Alumno()); // Esto limpia el formulario
        return "Proyecto/modificarAlumno";
    }

    @GetMapping("/eliminarAlumno")
    public String mostrarFormularioEliminar() {
        return "Proyecto/eliminarAlumno";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarAlumno(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (alumnoRepo.existsById(id)) {
            alumnoRepo.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Alumno eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Alumno no encontrado.");
        }
        return "Proyecto/eliminarAlumno";
    }

    @GetMapping("/gestionarAlumnos")
    public String mostrarGestionAlumnos() {
        return "Proyecto/gestionarAlumnos";
    }





    
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

    


    /*
    @ResponseBody
    @PostMapping("/alumno_grupo")
    public ResponseEntity<String> alumno_grupo(@RequestParam("file") MultipartFile file, 
                                            @RequestParam("idGrupo") Long idGrupo, 
                                            @RequestParam("idMateria") Long idMateria) {
        try {
            Grupo grupo = grupoRepository.findById(idGrupo).orElseThrow(() -> new Exception("Grupo no encontrado"));
            Materia materia = materiaRepository.findById(idMateria).orElseThrow(() -> new Exception("Materia no encontrada"));
            calificacionesService.cargarAlumnosDesdeExcelCompletoREs(file, grupo, materia);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
         */


    @ResponseBody
@PostMapping("/alumno_grupo")
public ResponseEntity<String> alumno_grupo(
        @RequestParam("file") MultipartFile file,
        @RequestParam("idGrupo") Long idGrupo,
        @RequestParam("idMateria") Long idMateria,
        @RequestParam("sheetName") String sheetName) {
    try {
        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new Exception("Grupo no encontrado"));
        Materia materia = materiaRepository.findById(idMateria)
                .orElseThrow(() -> new Exception("Materia no encontrada"));

        // Ahora pasamos también el nombre de la hoja
        calificacionesService.cargarAlumnosDesdeExcelCompletoREs(file, grupo, materia, sheetName);

        return ResponseEntity.ok("OK");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
    }
}

    


    



    
}
