package com.example.sistemaweb.sistemaweb.Controller;

import com.example.sistemaweb.sistemaweb.Entities.*;
import com.example.sistemaweb.sistemaweb.Repositories.*;
import com.example.sistemaweb.sistemaweb.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Controller
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private PeriodoService periodoService;

    @Autowired
    private ProfesorService profesorService;


    @Autowired
    private DatosTemporalesService datosTemporalesService;

    @Autowired
    private AlumnoGrupoRepository alumnoGrupoRepository;

    @Autowired
    private MateriaDefectoService materiaDefectoService;


   

    @Autowired
    private MateriaDefectoRepository materiaDefectoRepository;

    @Autowired
    private MateriaRepository materiaRepository;


    @Autowired
    private PeriodoRepository periodoRepository;



    @Autowired
    private ProfesorRepository profesorRepository;



    @Autowired
    private MateriaService materiaService;

    @Autowired
    private MateriaGrupoService materiaGrupoService;



    @Autowired
    private SemestreService semestreService;

    // Método para cargar los grupos por semestre dinámicamente (AJAX)

    @Autowired
    private asignarMateriasAGrupo asignarMateriasAGrupoService;

    // Endpoint para agregar un grupo con materias
    /* 
    @PostMapping("/agregarGrupo")
    public String agregarGrupo(@RequestParam String nombreGrupo,
                               @RequestParam int semestre,
                               @RequestParam int periodo,
                               @RequestBody List<MateriaDTO> materias) {

        try {
            // Agregar grupo a la base de datos (puedes agregar la lógica de guardar el grupo aquí)
            // Lógica de agregar materias al grupo
            asignarMateriasAGrupoService.asignarMateriasAGrupo(semestre, materias.stream().map(MateriaDTO::getId).collect(Collectors.toList()));
            
            return "Grupo creado y materias asignadas correctamente.";
        } catch (Exception e) {
            return "Error al crear el grupo: " + e.getMessage();
        }
    }

    */

    @PostMapping("/guardarMaterias")
        public List<MateriaDTO> guardarMaterias(@RequestBody List<Integer> idMaterias) {
            // Convertir la lista de Integer a Long
            List<Long> idMateriasLong = idMaterias.stream()
                                                .map(Long::valueOf)
                                                .collect(Collectors.toList());
            return asignarMateriasAGrupoService.guardarMaterias(idMateriasLong);
        }

  
        
    @GetMapping("/api/gruposPorSemestre")
    ///@GetMapping("/gruposPorSemestre/{idSemestre}")
    @ResponseBody
    public ResponseEntity<List<Grupo>> obtenerGruposPorSemestre(@RequestParam Long idSemestre) {
        System.out.println("idSemestre recibido: " + idSemestre); // Validar qué llega
        List<Grupo> grupos = grupoService.obtenerGruposPorSemestre(idSemestre);
        return ResponseEntity.ok(grupos);
    }

    
    @GetMapping("/pruebaGrupoSemestre")
    public String pruebaGrupoSemestre(Model model) {
        model.addAttribute("semestres", semestreService.obtenerSemestres());
        return "pruebaGrupoSemestre";  // Asegúrate de tener una vista para mostrar los grupos8
        
    }
    
    @GetMapping("/calificacionesGrupo")
    public String calificacionesGrupo(Model model) {
        model.addAttribute("semestres", semestreService.obtenerSemestres());
        return "Proyecto/cargarCalificaciones";  // Asegúrate de tener una vista para mostrar los grupos8
        
    }
    



    // Método para cargar materias por semestre (AJAX)
    @GetMapping("/materiasPorSemestre")
    @ResponseBody
    public ResponseEntity<List<MateriaDefecto>> obtenerMateriasPorSemestre(@RequestParam Long idSemestre) {
        List<MateriaDefecto> materias = materiaDefectoService.obtenerMateriasPorSemestre(idSemestre);
        return ResponseEntity.ok(materias);
    }

    // Mostrar la página principal con semestres, periodos y profesores
    @GetMapping("//materia/agregar-multiples")
    public String showIndexG(Model model) {
        model.addAttribute("semestres", semestreService.obtenerSemestres());
        model.addAttribute("periodos", periodoService.obtenerPeriodoID());
        model.addAttribute("profesores", profesorService.obtenerProfesores());
        return "agregarMateria";
        //return "indexGrupo";
    }


    @GetMapping("/indexGrupo")
    public String showIndexGrupo(Model model) {
        model.addAttribute("semestres", semestreService.obtenerSemestres());
        model.addAttribute("periodos", periodoService.obtenerPeriodoID());
        model.addAttribute("profesores", profesorService.obtenerProfesores());
        //return "crear_grupo2";
        return "Proyecto/añadirGrupos";
        //return "agregarMateria";
        //return "indexGrupo";
        //return "CREAR_GRUPO";
    }


   

    // Crear el grupo y cargar los alumnos desde un archivo Excel
    /* 
    @PostMapping("/grupo/agregar")
    public ResponseEntity<String> agregarGrupo(@RequestParam("nombreGrupo") String nombreGrupo,
                                                @RequestParam("semestre") int semestreId,
                                                @RequestParam("periodo") int periodoId,
                                                @RequestParam("file") MultipartFile file) {
        try {
            Grupo grupo = grupoService.agregarGrupo(nombreGrupo, semestreId, periodoId);
            alumnoService.cargarAlumnosDesdeExcel(file, grupo);

            System.out.println(grupo.getIdGrupo (  )  );
            return ResponseEntity.ok("Grupo creado exitosamente con ID: " + grupo.getIdGrupo() + " y alumnos agregados.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al crear el grupo o cargar los alumnos.");
        }
    }
    */

    // Crear el grupo 
    /* 
    @PostMapping("/grupo/agregarGrupo")
    public ResponseEntity<String> agregarGrupo(@RequestParam("nombreGrupo") String nombreGrupo,
                                                @RequestParam("semestre") int semestreId,
                                                @RequestParam("periodo") int periodoId) {
        try {
            Grupo grupo = grupoService.agregarGrupo(nombreGrupo, semestreId, periodoId);

            //return ResponseEntity.ok("Grupo creado exitosamente con ID: " + grupo.getIdGrupo() + " ");
            return ResponseEntity.ok("Grupo creado exitosamente: " );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al crear el grupo ");
        }
    }
        */








    



    // cargar materias al grupo
    public String agregarMateria(String claveMateriaDefecto, int idPeriodo, int idProfesor) {
        if (materiaDefectoRepository.existsByClave(claveMateriaDefecto) && 
            periodoRepository.existsById(idPeriodo) &&
            profesorRepository.existsByIdProfesor(idProfesor)) {  // Usar int en lugar de Long
    
            // Crear la nueva materia
            //materiaRepository.agregarMateria(claveMateriaDefecto, idPeriodo, idProfesor);
    
            return "Materias agregadas correctamente";
        } else {
            return "Error: No se pudo agregar la materia, datos inválidos.";
        }
    }
    





    

    //mostrar grupos
    @GetMapping("/grupo/listar")
    public String listarGrupos(Model model) {
        List<Grupo> grupos = grupoService.obtenerNombreGrupoPeriodo();
        model.addAttribute("grupos", grupos);

        return "listarGrupos";  // Asegúrate de que este nombre coincida con la vista que existe en /src/main/resources/templates
    }

    @GetMapping("/api/profesores")
    @ResponseBody
    public List<Profesor> obtenerProfesores() {
        return profesorService.obtenerProfesores();
    }


    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("semestres", semestreService.obtenerSemestres());
        model.addAttribute("grupos", grupoService.obtenerGruposPorSemestre(null));
        model.addAttribute("profesores", profesorService.obtenerProfesores());
        model.addAttribute("periodos", periodoService.obtenerPeriodoID());
        

        return "crear_grupo2"; // Nombre de la vista que se debe renderizar
    }

    /* 
    @PostMapping("/agregarMaterias")
    public ResponseEntity<?> agregarMaterias(@RequestBody List<MateriaRequest> materiasRequest) {
        for (MateriaRequest materiaRequest : materiasRequest) {
            // Buscar la materiaDefecto por clave (String)
            Optional<MateriaDefecto> materiaDefectoOptional = materiaDefectoRepository.findByClave(materiaRequest.getClave());
            Optional<Periodo> periodoOptional = periodoRepository.findById(materiaRequest.getPeriodo().getIdPeriodo().intValue());

            Optional<Profesor> profesorOptional = profesorRepository.findById(materiaRequest.getProfesor().getIdProfesor());

            // Verificar si todos los objetos existen
            if (materiaDefectoOptional.isPresent() && periodoOptional.isPresent() && profesorOptional.isPresent()) {
                // Crear y guardar la nueva materia
                MateriaDefecto materiaDefecto = materiaDefectoOptional.get();
                Periodo periodo = periodoOptional.get();
                Profesor profesor = profesorOptional.get();

                Materia nuevaMateria = new Materia(materiaDefecto, periodo, profesor);
                materiaRepository.save(nuevaMateria);
                System.out.println("Materias agregadas correctamente");
            } else {

                return ResponseEntity.badRequest().body("Error: MateriaDefecto, Periodo o Profesor no encontrado");
            }
        }
        return ResponseEntity.ok().body("{\"mensaje\": \"Materias agregadas correctamente\"}");

    }
    
    */
    @Autowired
    private GrupoMateriaService grupoMateriaService;

    
    @Autowired
    private MateriaGrupoRepository materiaGrupoRepository;


   

    
    
     int IDGRUPO0;
     
    @PostMapping("/grupo/agregarGrupo")
    public ResponseEntity<String> agregarGrupo(@RequestParam("nombreGrupo") String nombreGrupo,
                                                @RequestParam("semestre") int semestreId,
                                                @RequestParam("periodo") int periodoId) {
        try {
            // Crear el grupo
            Grupo grupo = grupoService.agregarGrupo(nombreGrupo, semestreId, periodoId);
             // Obtener el ID del grupo creado
        Long idGrupo = grupo.getIdGrupo();
        IDGRUPO0 = idGrupo.intValue(); // Convertimos de Long a int
            // Guardar el ID del grupo y las materias en el servicio
        // Llamar al método agregarMaterias y pasar el ID del grupo

            // Devolver el ID del grupo creado
            System.out.println("Grupo creado exitosamente con ID: " + grupo.getIdGrupo());
            idGrupo = grupo.getIdGrupo();
            //return ResponseEntity.ok("Grupo creado exitosamente con ID: " + grupo.getIdGrupo());
            return ResponseEntity.ok("Grupo creado exitosamente con ID: " + grupo.getIdGrupo());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al crear el grupo.");
        }
    }
    
    List<Long> idsMaterias = new ArrayList<>();
    @PostMapping("/agregarMaterias")
    public ResponseEntity<?> agregarMaterias(@RequestBody List<MateriaRequest> materiasRequest) {
        idsMaterias.clear(); // Limpiar la lista de IDs de materias
        for (MateriaRequest materiaRequest : materiasRequest) {
            // Buscar la materia por clave, periodo y profesor
            Optional<Materia> materiaExistente = materiaRepository.findByClaveAndPeriodoAndProfesor(
                    materiaRequest.getClave(),
                    materiaRequest.getPeriodo().getIdPeriodo(),
                    materiaRequest.getProfesor().getIdProfesor()
            );

            Materia materia;
            if (materiaExistente.isPresent()) {
                // Si la materia ya existe, usarla
                materia = materiaExistente.get();
                System.out.println("Materia existente encontrada: " + materia.getIdMateria());
            } else {
                // Crear una nueva materia
                Optional<MateriaDefecto> materiaDefectoOptional = materiaDefectoRepository.findByClave(materiaRequest.getClave());
                Optional<Periodo> periodoOptional = periodoRepository.findById(materiaRequest.getPeriodo().getIdPeriodo().intValue());
                Optional<Profesor> profesorOptional = profesorRepository.findById(materiaRequest.getProfesor().getIdProfesor());

                // Verificar existencia de materiaDefecto, periodo y profesor
                if (materiaDefectoOptional.isPresent() && periodoOptional.isPresent() && profesorOptional.isPresent()) {
                    MateriaDefecto materiaDefecto = materiaDefectoOptional.get();
                    Periodo periodo = periodoOptional.get();
                    Profesor profesor = profesorOptional.get();

                    materia = new Materia(materiaDefecto, periodo, profesor);
                    materiaRepository.save(materia);
                    System.out.println("Materia creada: " + materia.getIdMateria());
                } else {
                    return ResponseEntity.badRequest().body("Error: Datos incompletos para crear la materia.");
                }
            }

            // Agregar el ID de la materia (existente o nueva) a la lista
            idsMaterias.add(materia.getIdMateria());
        }

        return ResponseEntity.ok().body("{\"mensaje\": \"Materias agregadas correctamente\", \"idsMaterias\": " + idsMaterias + "}");
    }
/* 
    List<Long> idsMaterias = new ArrayList<>();
    @PostMapping("/agregarMaterias")
    public ResponseEntity<?> agregarMaterias(@RequestBody List<MateriaRequest> materiasRequest) {

        
        // Lista para almacenar los IDs de las materias creadas
        idsMaterias.clear();
        for (MateriaRequest materiaRequest : materiasRequest) {
            // Buscar la materiaDefecto, periodo y profesor
            Optional<MateriaDefecto> materiaDefectoOptional = materiaDefectoRepository.findByClave(materiaRequest.getClave());
            Optional<Periodo> periodoOptional = periodoRepository.findById(materiaRequest.getPeriodo().getIdPeriodo().intValue());
            Optional<Profesor> profesorOptional = profesorRepository.findById(materiaRequest.getProfesor().getIdProfesor());

            // Verificar si todos los objetos existen
            if (materiaDefectoOptional.isPresent() && periodoOptional.isPresent() && profesorOptional.isPresent()) {
                // Crear y guardar la nueva materia
                MateriaDefecto materiaDefecto = materiaDefectoOptional.get();
                Periodo periodo = periodoOptional.get();
                Profesor profesor = profesorOptional.get();

                Materia nuevaMateria = new Materia(materiaDefecto, periodo, profesor);
                materiaRepository.save(nuevaMateria);

                // Agregar el ID de la nueva materia a la lista
                idsMaterias.add(nuevaMateria.getIdMateria()); // Suponiendo que el ID de la materia es 'idMateria'


                
                System.out.println(idsMaterias);
                //return ResponseEntity.ok().body("{\"mensaje\": \"Materias agregadas correctamente\", \"idsMaterias\": " + idsMaterias + "}");
            } else {
                return ResponseEntity.badRequest().body("Error: MateriaDefecto, Periodo o Profesor no encontrado.");
            }
        }

        

        

        // Devolver los IDs de las materias creadas
        return ResponseEntity.ok().body("{\"mensaje\": \"Materias agregadas correctamente\", \"idsMaterias\": " + idsMaterias + "}");
    }

 
    */


    @GetMapping("/probar-conexion3")
    public String testAsignarMateriasAGrupo(Model model) {
        try {
            for (Long idMateria : idsMaterias) {
                try {
                    System.out.println("Probando asignación: Materia " + idMateria + ", Grupo " + IDGRUPO0);
                    materiaGrupoRepository.asignarMateriaAGrupo(idMateria.intValue(), IDGRUPO0);
                    System.out.println("Asignación exitosa: Materia " + idMateria + ", Grupo " + IDGRUPO0);
                } catch (Exception e) {
                    System.err.println("Error al asignar Materia " + idMateria + ": " + e.getMessage());
                }
            }
            // Supongamos que "probar-conexion3" es el nombre de la plantilla que quieres renderizar
            model.addAttribute("mensaje", "Asignaciones realizadas correctamente.");
            return "index";
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            // Redirigir a una página de error específica
            model.addAttribute("error", "Ha ocurrido un error procesando la solicitud.");
            return "index";
        }
    }

    
    
/* 
    @PostMapping("/grupo/agregarGrupoYAsignarMaterias")
    public ResponseEntity<String> agregarGrupoYAsignarMaterias(@RequestParam("nombreGrupo") String nombreGrupo,
                                                                @RequestParam("semestre") int semestreId,
                                                                @RequestParam("periodo") int periodoId,
                                                                @RequestBody List<MateriaRequest> materiasRequest) {
        try {
            // Crear el grupo
            Grupo grupo = grupoService.agregarGrupo(nombreGrupo, semestreId, periodoId);
            
            // Obtener el ID del grupo creado
            Long idGrupo = grupo.getIdGrupo();
    
            // Lista para almacenar los IDs de las materias creadas
            List<Long> idsMaterias = new ArrayList<>();
    
            // Asignar las materias al grupo
            for (MateriaRequest materiaRequest : materiasRequest) {
                // Buscar la materiaDefecto, periodo y profesor
                Optional<MateriaDefecto> materiaDefectoOptional = materiaDefectoRepository.findByClave(materiaRequest.getClave());
                Optional<Periodo> periodoOptional = periodoRepository.findById(materiaRequest.getPeriodo().getIdPeriodo().intValue());
                Optional<Profesor> profesorOptional = profesorRepository.findById(materiaRequest.getProfesor().getIdProfesor());
    
                // Verificar si todos los objetos existen
                if (materiaDefectoOptional.isPresent() && periodoOptional.isPresent() && profesorOptional.isPresent()) {
                    // Crear y guardar la nueva materia
                    MateriaDefecto materiaDefecto = materiaDefectoOptional.get();
                    Periodo periodo = periodoOptional.get();
                    Profesor profesor = profesorOptional.get();
    
                    Materia nuevaMateria = new Materia(materiaDefecto, periodo, profesor);
                    materiaRepository.save(nuevaMateria);
    
                    // Agregar el ID de la nueva materia a la lista
                    idsMaterias.add(nuevaMateria.getIdMateria());
                } else {
                    return ResponseEntity.badRequest().body("Error: MateriaDefecto, Periodo o Profesor no encontrado.");
                }
            }
    
            // Asignar las materias al grupo
            for (Long idMateria : idsMaterias) {
                grupoMateriaService.asignarMateriaAGrupo(idMateria, idGrupo);
            }
    
            // Devolver respuesta exitosa
            return ResponseEntity.ok("Grupo creado y materias asignadas exitosamente con ID: " + idGrupo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al crear el grupo o asignar las materias.");
        }
    }
    

    
**/
    



}


//
//
////
/// /
/// /
/// /
/// /
/// /
/// //
/// /
/// /
/// /
/// /
/// 
/// 
//
//
////
/// /
/// /
/// /
/// /
/// /
/// //
/// /
/// /
/// /
/// /
/// 
/// 
/// 
//
//
////
/// /
/// /
/// /
/// /
/// /
/// //
/// /
/// /
/// /
/// /
/// 
/// 
/// 


/* 
package com.proyecto.integrador.proyecto501.Controller;

import com.proyecto.integrador.proyecto501.Entities.Alumno;
import com.proyecto.integrador.proyecto501.Entities.AlumnoGrupo;
import com.proyecto.integrador.proyecto501.Entities.Grupo;
import com.proyecto.integrador.proyecto501.Services.MateriaDefectoService;
import com.proyecto.integrador.proyecto501.Services.MateriaService;
import com.proyecto.integrador.proyecto501.Services.SemestreService;
import com.proyecto.integrador.proyecto501.Entities.MateriaDefecto;
import com.proyecto.integrador.proyecto501.Repositories.AlumnoGrupoRepository;
import com.proyecto.integrador.proyecto501.Services.AlumnoService;
import com.proyecto.integrador.proyecto501.Services.GrupoService;
import com.proyecto.integrador.proyecto501.Services.MateriaDefectoService;
import com.proyecto.integrador.proyecto501.Services.MateriaService;
import com.proyecto.integrador.proyecto501.Services.PeriodoService;
import com.proyecto.integrador.proyecto501.Services.SemestreService;
import com.proyecto.integrador.proyecto501.Services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    
    //@Autowired
   // private MateriaService materiaService;

    @Autowired 
    private AlumnoService alumnoService;

    @Autowired
    private PeriodoService periodoService;

    //@Autowired
    ///private SemestreService semestreService;

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private AlumnoGrupoRepository alumnoGrupoRepository;

    

    // Método para agregar alumnos a un grupo
    public void agregarAlumnoAGrupo(Alumno alumno, Grupo grupo) {
        if (!alumnoGrupoRepository.existsByAlumno_IdAlumnoAndGrupo_IdGrupo(alumno.getIdAlumno(), grupo.getIdGrupo())) {
            AlumnoGrupo alumnoGrupo = new AlumnoGrupo();
            alumnoGrupo.setAlumno(alumno);
            alumnoGrupo.setGrupo(grupo);
            alumnoGrupoRepository.save(alumnoGrupo);
        }
    }

    // Mostrar la página principal con semestres, periodos y profesores
    @GetMapping("/indexGrupo")
    public String showIndexGrupo(Model model) {
        model.addAttribute("semestres", semestreService.obtenerSemestres());
        model.addAttribute("periodos", periodoService.obtenerPeriodoID());
        model.addAttribute("profesores", profesorService.obtenerProfesores());
        return "indexGrupo";
    }


    ///Materias por grupo seleccionado
    @GetMapping("/grupoMateria")
    public String grupoMateria(Model model){
        return "Materia_Grupo";
    } 

    //mostrar grupos
    @GetMapping("/grupo/listar")
    public String listarGrupos(Model model) {
        List<Grupo> grupos = grupoService.obtenerNombreGrupoPeriodo();
        model.addAttribute("grupos", grupos);
        return "listarGrupos";  // Asegúrate de que este nombre coincida con la vista que existe en /src/main/resources/templates
    }

    // Crear el grupo, leer el archivo Excel y asignar los alumnos al grupo
    @PostMapping("/grupo/agregar")
    public ResponseEntity<String> agregarGrupo(
            @RequestParam("nombreGrupo") String nombreGrupo,
            @RequestParam("semestre") int semestreId,
            @RequestParam("periodo") int periodoId,
            @RequestParam("file") MultipartFile file) {
        try {
            // Crear el grupo
            Grupo grupo = grupoService.agregarGrupo(nombreGrupo, semestreId, periodoId);

            // Leer el archivo Excel y registrar alumnos en el grupo
            alumnoService.cargarAlumnosDesdeExcel(file, grupo);

            // Mensaje de confirmación
            String mensaje = "Grupo creado exitosamente con ID: " + grupo.getIdGrupo() + " y alumnos agregados.";
            return ResponseEntity.ok(mensaje);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al crear el grupo o cargar los alumnos.");
        }
    }









    ///MATERIAS
    /// 
    /// 
    private final MateriaDefectoService materiaDefectoService;
    private final SemestreService semestreService;

    
    @Autowired
    public GrupoController(MateriaDefectoService materiaDefectoService, SemestreService semestreService) {
        this.materiaDefectoService = materiaDefectoService;
        this.semestreService = semestreService;
    }

    




    // Obtener materias por semestre
    @GetMapping("/materias")
    public String obtenerMateriasPorSemestre(@RequestParam(required = false) Long idSemestre, Model model) {
        // Obtener los semestres disponibles
        model.addAttribute("semestres", semestreService.obtenerSemestres());

        if (idSemestre != null && idSemestre > 0) {
            // Obtener las materias para el semestre seleccionado
            List<MateriaDefecto> materias = materiaDefectoService.obtenerMateriasPorSemestre(idSemestre);
            if (materias.isEmpty()) {
                System.out.println("No hay materias para este semestre");
            }
            model.addAttribute("materias", materias);
        } else {
            System.out.println("No se ha seleccionado un semestre válido");
        }

        return "indexGrupo"; // Vista que se debe devolver
    }


    
    @GetMapping("/materiasPorSemestre")
    public ResponseEntity<List<MateriaDefecto>> obtenerMateriasPorSemestre(@RequestParam Long idSemestre) {
        List<MateriaDefecto> materias = materiaDefectoService.obtenerMateriasPorSemestre(idSemestre);
        return ResponseEntity.ok(materias);
    }





    ///GRUPO Y MATERIAS
    /// 
    /// 


// Método para cargar la página inicial con los semestres
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        // Obtener la lista de semestres y agregarla al modelo
        model.addAttribute("semestres", semestreService.obtenerSemestres());
        return "Materia_grupo"; // Nombre del archivo HTML
    }

    // Endpoint para obtener los grupos por semestre de forma dinámica
    @GetMapping("/api/gruposPorSemestre")
    @ResponseBody
    public List<Grupo> obtenerGruposPorSemestre(@RequestParam("semestreId") Long semestreId) {
        // Obtener los grupos asociados al semestre
        return grupoService.obtenerGruposPorSemestre(semestreId);
    }

    // Endpoint para procesar la selección del semestre y grupo
    @PostMapping("/api/seleccionar")
    @ResponseBody
    public Map<String, String> seleccionarGrupo(@RequestParam("semestre") Long semestreId,
                                                @RequestParam("grupo") Long grupoId) {
        Map<String, String> respuesta = new HashMap<>();

        // Verificar que los datos sean válidos
        if (semestreId == null || grupoId == null) {
            respuesta.put("mensaje", "Debe seleccionar un semestre y un grupo válidos.");
            respuesta.put("estado", "error");
            return respuesta;
        }

        // Procesar la selección
        respuesta.put("mensaje", "Semestre y grupo seleccionados correctamente.");
        respuesta.put("estado", "exito");
        return respuesta;
    }


}







 

    /* 
    @PostMapping("/grupo/agregar")
    public String agregarGrupo(@RequestParam("nombreGrupo") String nombreGrupo,
                            @RequestParam("semestre") int semestreId,
                            @RequestParam("periodo") int periodoId,
                            RedirectAttributes redirectAttributes) {
        // Llamar al servicio para agregar el grupo               
        grupoService.agregarGrupo(nombreGrupo, semestreId, periodoId);
        
        // Agregar mensaje de éxito al redireccionar
        redirectAttributes.addFlashAttribute("message", "Grupo creado exitosamente.");
        
        return "redirect:/grupo/listar";  // Redirigir a la página de listar grupos
    }
    */

    /* 
    @GetMapping("/grupo/listar")
    public String listarGrupos(Model model) {
        List<Grupo> grupos = grupoService.obtenerNombreGrupoPeriodo();
        model.addAttribute("grupos", grupos);
        return "listarGrupos";  // Asegúrate de que este nombre coincida con la vista que existe en /src/main/resources/templates
    }
    */


    




 

    

 

    


  

    
    


    

