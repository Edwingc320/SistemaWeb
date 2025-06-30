package com.example.sistemaweb.sistemaweb.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.example.sistemaweb.sistemaweb.Entities.*;
import com.example.sistemaweb.sistemaweb.Services.*;


@Controller
public class CalificacionesController {

    @Autowired
    private CalificacionesService calificacionesService;

  


    @Autowired
    private SemestreService semestreService;

    @Autowired
    private MateriaService materiaService;

   

    @GetMapping("/data")
    public String showData() {
        return "cargarExcel";  // Asegúrate de tener una vista para cargar el archivo
    }
   
    /* 
     @GetMapping("/por-grupo/{idMateria}")
    public ResponseEntity<List<CalificacionDTO>> obtenerCalificacionesPorGrupo(@PathVariable Long idMateria) {
        List<CalificacionDTO> calificaciones = calificacionesService.obtenerCalificacionesPorGrupo(idMateria);
        return ResponseEntity.ok(calificaciones);
    }
    */

    @GetMapping("/por-materia/{idMateria}")
    public ResponseEntity<List<CalificacionDTO>> obtenerCalificacionesPorMateria(@PathVariable Long idMateria) {
        List<CalificacionDTO> calificaciones = calificacionesService.obtenerCalificacionesPorMateria(idMateria);
        return ResponseEntity.ok(calificaciones);
    }

        
    /*
    @GetMapping("/calificaciones")
    public String mostrarCalificaciones(Model model, Long idMateria, Long idGrupo) {
       
        model.addAttribute("semestres", semestreService.obtenerSemestres());
        List<Materia> materias = materiaService.obtenerMateriasPorGrupo(idGrupo);


        List<CalificacionDTO> calificaciones = calificacionesService.obtenerCalificacionesPorMateria(idMateria);

        model.addAttribute("calificaciones", calificaciones);



        // Obtener el promedio general por alumno y convertirlo a Map: { idAlumno -> promedio }
       // Map<Long, BigDecimal> promedioGeneral = obtenerPromedioGeneralPorAlumno();
        //model.addAttribute("promedioGeneral", promedioGeneral);





        return "grupoCalificaciones"; // Nombre de la vista HTML
       // return "calificaciones"; // Nombre de la vista HTML
    }
    */

    @GetMapping("/calificaciones")
        public String mostrarCalificaciones(Model model, Long idMateria, Long idGrupo) {
            model.addAttribute("semestres", semestreService.obtenerSemestres());
            List<Materia> materias = materiaService.obtenerMateriasPorGrupo(idGrupo);
            List<CalificacionDTO> calificaciones = calificacionesService.obtenerCalificacionesPorMateria(idMateria);
            model.addAttribute("calificaciones", calificaciones);

            // Obtener el promedio general por alumno para la materia y grupo
            //Map<Long, BigDecimal> promedioPorAlumno = calificacionesService.obtenerPromedioPorMateriaYGrupo(idMateria, idGrupo);
            //model.addAttribute("promedioPorAlumno", promedioPorAlumno);

            return "grupoCalificaciones"; // Nombre de la vista HTML
        }


        
        @GetMapping("/calificacionesIndividual")
        public String mostrarCalificacionesIndividual(Model model, Long idMateria, Long idGrupo) {
            model.addAttribute("semestres", semestreService.obtenerSemestres());
            List<Materia> materias = materiaService.obtenerMateriasPorGrupo(idGrupo);
            List<CalificacionDTO> calificaciones = calificacionesService.obtenerCalificacionesPorMateria(idMateria);
            model.addAttribute("calificaciones", calificaciones);

            // Obtener el promedio general por alumno para la materia y grupo
            //Map<Long, BigDecimal> promedioPorAlumno = calificacionesService.obtenerPromedioPorMateriaYGrupo(idMateria, idGrupo);
            //model.addAttribute("promedioPorAlumno", promedioPorAlumno);

           // return "calificaciones"; // Nombre de la vista HTML
            //return "Proyecto/gestionGrupos"; // Nombre de la vista HTML
           
            return "Proyecto/gestionarGrupo"; // Nombre de la vista HTML

        }


        

    /* 
    @GetMapping("/por-materia/{idMateria}")
    public ResponseEntity<List<CalificacionDTO>> obtenerCalificacionesPorMateria(@PathVariable Long idMateria) {
        List<CalificacionDTO> calificaciones = calificacionesService.obtenerCalificacionesPorMateria(idMateria);
        return ResponseEntity.ok(calificaciones);
    }
    */

    /* 
    @GetMapping("/calificacione11")
    public String obtenerPromedioGeneralPorAlumno(Model model) {
        List<Object[]> resultados = calificacionesRepository.findPromedioGeneralPorAlumno();
        
        Map<String, BigDecimal> promedioGeneral = new HashMap<>();
        
        for (Object[] resultado : resultados) {
            Long idAlumno = (Long) resultado[0];  
            Double promedio = (Double) resultado[1];  
            BigDecimal promedioDecimal = BigDecimal.valueOf(promedio);
            String idAlumnoString = idAlumno.toString();
            promedioGeneral.put(idAlumnoString, promedioDecimal);
        }
        
        model.addAttribute("promedioGeneral", promedioGeneral); // Pasa los datos al modelo
        return "calificaciones11";  // Asegúrate de que esta vista existe
    }
    */
    

    




    @GetMapping("/materias-y-calificaciones/{idGrupo}")
    public ResponseEntity<Map<String, Object>> obtenerMateriasYCalificacionesPorGrupo(@PathVariable Long idGrupo) {
        // Obtener las materias para el grupo
        
        List<Materia> materias = materiaService.obtenerMateriasPorGrupo(idGrupo);
        
        // Obtener las calificaciones para cada materia del grupo
        Map<String, Object> response = new HashMap<>();
        
        List<CalificacionDTO> calificaciones = new ArrayList<>();
        for (Materia materia : materias) {
            List<CalificacionDTO> calificacionesMateria = calificacionesService.obtenerCalificacionesPorMateria(materia.getIdMateria());
            calificaciones.addAll(calificacionesMateria);
        }

        response.put("materias", materias);
        response.put("calificaciones", calificaciones);
        
        return ResponseEntity.ok(response);
    }



    /* 

    @PostMapping("/uploadExcel")
    public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) {
        try {
            calificacionesService.cargarAlumnosDesdeExcelCompleto(file);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Hubo un error al cargar el archivo");
        }
        return "resultado"; // Vista donde mostrarás los alumnos cargados
    }
    */

    /* 
    @PostMapping("/uploadExcel")
    public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) {
        try {
            calificacionesService.cargarAlumnosDesdeExcelCompleto(file);
            model.addAttribute("error", false);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", true);
            model.addAttribute("message", e.getMessage());
        }
        return "resultado"; // Vista donde mostrarás los alumnos cargados
    }
    */


    


}
