package com.example.sistemaweb.sistemaweb.Controller;

import com.example.sistemaweb.sistemaweb.Entities.*;
import com.example.sistemaweb.sistemaweb.Repositories.*;
import com.example.sistemaweb.sistemaweb.Services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MateriaDefectoController {
    
    private final MateriaDefectoService materiaDefectoService;
    private final SemestreService semestreService;


    @Autowired
    private MateriaService materiaService;
    
    @Autowired
    public MateriaDefectoController(MateriaDefectoService materiaDefectoService, SemestreService semestreService) {
        this.materiaDefectoService = materiaDefectoService;
        this.semestreService = semestreService;
    }

    
    

    //@GetMapping("/materias")
    /* 
    public String obtenerMateriasPorSemestre(@RequestParam(required = false) Long idSemestre, Model model) {
        // Obtener los semestres disponibles
        model.addAttribute("semestres", semestreService.obtenerSemestres());

        if (idSemestre != null && idSemestre > 0) {
            // Obtener las materias para el semestre seleccionado
            List<MateriaDefecto> materias = materiaDefectoService.obtenerMateriasPorSemestre(idSemestre);
            model.addAttribute("materias", materias);
        }

        return "materias_por_semestre"; // Vista que se debe devolver
        
    }


    */

    
        

}
