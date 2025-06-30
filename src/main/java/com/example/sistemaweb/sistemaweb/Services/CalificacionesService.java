package com.example.sistemaweb.sistemaweb.Services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sistemaweb.sistemaweb.Entities.*;
import com.example.sistemaweb.sistemaweb.Repositories.*;

@Service
public class CalificacionesService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private AlumnoGrupoRepository alumnoGrupoRepository;

    @Autowired
    private GrupoService grupoService;


    @Autowired
    private MateriaRepository materiaRepository;


    @Autowired
    private MateriaService materiaService;


    @Autowired
    private CalificacionesRepository calificacionesRepository;

     /* 
    public void cargarAlumnosDesdeExcelBY(MultipartFile file, Grupo grupo) {
            try {
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);
    
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        String matricula = getCellValueAsString(row.getCell(1));
                        String estudiante = getCellValueAsString(row.getCell(2));
                        
    
                        int spaces = getSpaces(estudiante);
                        String[] partes = estudiante.trim().split(" ");
    
                        String apellidoPaterno = "";
                        String apellidoMaterno = "";
                        String nombres = "";
    
                        if (spaces == 1) {
                            apellidoPaterno = partes[0];
                            apellidoMaterno = partes[1];
                        } else if (spaces == 2) {
                            apellidoPaterno = partes[0];
                            apellidoMaterno = partes[1];
                            nombres = partes[2];
                        } else if (spaces == 3) {
                            apellidoPaterno = partes[0];
                            apellidoMaterno = partes[1];
                            nombres = partes[2] + " " + partes[3];
                        } else {
                            apellidoPaterno = partes[0];
                            apellidoMaterno = partes.length > 2 ? partes[1] : "";
                            nombres = String.join(" ", Arrays.copyOfRange(partes, 2, partes.length));
                        }
    
                        Alumno alumno;
                        if (!alumnoRepository.existsByMatricula(matricula)) {
                            alumno = new Alumno();
                            alumno.setMatricula(matricula);
                            alumno.setNombre(nombres);
                            alumno.setApellidoPaterno(apellidoPaterno);
                            alumno.setApellidoMaterno(apellidoMaterno);
                            alumno = alumnoRepository.save(alumno);
                            System.out.println("Alumno agregado correctamente: " + matricula);
                        } else {
                            alumno = alumnoRepository.findByMatricula(matricula).orElse(null);

                            System.out.println("El alumno con matrícula " + matricula + " ya está registrado.");
                        }

                        System.out.println("ID Alumno: " + alumno.getIdAlumno());
                        System.out.println("ID Grupo: " + grupo.getIdGrupo());

    
                        if (alumnoGrupoRepository.existsByAlumno_IdAlumnoAndGrupo_IdGrupo(alumno.getIdAlumno(), grupo.getIdGrupo())) {
                            System.out.println("La relación ya existe.");
                        } else {
                            System.out.println("La relación no existe, se creará.");
                            grupoService.asignarGrupoAAlumno(alumno.getIdAlumno(), grupo.getIdGrupo());
                        }
                        
                        
                        

                            
                            
                    }
                }
    
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       
        */
        
       

         









        /* 
        public void cargarAlumnosDesdeExcelCompleto(MultipartFile file) {
            try {
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);
        
                // Iterar desde la fila 1 para omitir encabezado
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        // Leer "No.", "MATRÍCULA" y "ESTUDIANTE"
                        String no = getCellValueAsString(row.getCell(0));  // Columna 0
                        String matricula = getCellValueAsString(row.getCell(1)); // Columna 1
                        String estudiante = getCellValueAsString(row.getCell(2)); // Columna 2


                        
        
                        // Leer valores adicionales
                        String corteI = getCellValueAsString(row.getCell(3)); // Columna 3
                        String corteII = getCellValueAsString(row.getCell(4)); // Columna 4
                        String corteIII = getCellValueAsString(row.getCell(5)); // Columna 5
                        String calFinal = getCellValueAsString(row.getCell(6)); // Columna 6
                        String letraFinal = getCellValueAsString(row.getCell(7)); // Columna 7
                        String recI = getCellValueAsString(row.getCell(8)); // Columna 8
                        String recII = getCellValueAsString(row.getCell(9)); // Columna 9
                        String recIII = getCellValueAsString(row.getCell(10)); // Columna 10
                        String calRecuperacion = getCellValueAsString(row.getCell(11)); // Columna 11
                        String letraRecuperacion = getCellValueAsString(row.getCell(12)); // Columna 12
        
                        // Procesar los datos o almacenarlos
                        System.out.println("Fila " + (i + 1) + ":");
                        System.out.println("No.: " + no);
                        System.out.println("Matrícula: " + matricula);
                        System.out.println("Estudiante: " + estudiante);
                        System.out.println("Corte I: " + corteI);
                        System.out.println("Corte II: " + corteII);
                        System.out.println("Corte III: " + corteIII);
                        System.out.println("Calificación Final: " + calFinal);
                        System.out.println("Letra Final: " + letraFinal);
                        System.out.println("Recuperación I: " + recI);
                        System.out.println("Recuperación II: " + recII);
                        System.out.println("Recuperación III: " + recIII);
                        System.out.println("Calificación Recuperación: " + calRecuperacion);
                        System.out.println("Letra Recuperación: " + letraRecuperacion);
                        System.out.println("-------------------------");
                    }
                }
        
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

      */

        



    ////////////////////////////////////////////////AQUI///////////////////////////////////////



    /* 

      public void cargarAlumnosDesdeExcelCompletoREs(MultipartFile file,Grupo grupo, Materia materia) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
    
            // Iterar desde la fila 1 para omitir encabezado
            for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    // Leer "No.", "MATRÍCULA" y "ESTUDIANTE"
                    String no = getCellValueAsString(row.getCell(0));  // Columna 0
                    String matricula = getCellValueAsString(row.getCell(1)); // Columna 1
                    String estudiante = getCellValueAsString(row.getCell(2)); // Columna 2

                    int spaces = getSpaces(estudiante);
                    String[] partes = estudiante.trim().split(" ");

                    String apellidoPaterno = "";
                    String apellidoMaterno = "";
                    String nombres = "";

                    if (spaces == 1) {
                        apellidoPaterno = partes[0];
                        apellidoMaterno = partes[1];
                    } else if (spaces == 2) {
                        apellidoPaterno = partes[0];
                        apellidoMaterno = partes[1];
                        nombres = partes[2];
                    } else if (spaces == 3) {
                        apellidoPaterno = partes[0];
                        apellidoMaterno = partes[1];
                        nombres = partes[2] + " " + partes[3];
                    } else {
                        apellidoPaterno = partes[0];
                        apellidoMaterno = partes.length > 2 ? partes[1] : "";
                        nombres = String.join(" ", Arrays.copyOfRange(partes, 2, partes.length));
                    }

                    Alumno alumno;
                    if (!alumnoRepository.existsByMatricula(matricula)) {
                        alumno = new Alumno();
                        alumno.setMatricula(matricula);
                        alumno.setNombre(nombres);
                        alumno.setApellidoPaterno(apellidoPaterno);
                        alumno.setApellidoMaterno(apellidoMaterno);
                        alumno = alumnoRepository.save(alumno);
                        System.out.println("Alumno agregado correctamente: " + matricula);
                    } else {
                        alumno = alumnoRepository.findByMatricula(matricula).orElse(null);

                        System.out.println("El alumno con matrícula " + matricula + " ya está registrado.");
                    }

                    System.out.println("ID Alumno: " + alumno.getIdAlumno());
                    System.out.println("ID Grupo: " + grupo.getIdGrupo());


                    if (alumnoGrupoRepository.existsByAlumno_IdAlumnoAndGrupo_IdGrupo(alumno.getIdAlumno(), grupo.getIdGrupo())) {
                        System.out.println("La relación ya existe.");
                    } else {
                        System.out.println("La relación no existe, se creará.");
                        grupoService.asignarGrupoAAlumno(alumno.getIdAlumno(), grupo.getIdGrupo());
                    }
                    
    

                        BigDecimal corteI = parseBigDecimal(getCellValueAsString(row.getCell(3)));
                        BigDecimal corteII = parseBigDecimal(getCellValueAsString(row.getCell(4)));
                        BigDecimal corteIII = parseBigDecimal(getCellValueAsString(row.getCell(5)));
                        BigDecimal calFinal = parseBigDecimal(getCellValueAsString(row.getCell(6)));

                       

                        String letraFinal = getCellValueAsString(row.getCell(7)); // Columna 7

                        BigDecimal recI = parseBigDecimal(getCellValueAsString(row.getCell(8)));
                        BigDecimal recII = parseBigDecimal(getCellValueAsString(row.getCell(9)));
                        BigDecimal recIII = parseBigDecimal(getCellValueAsString(row.getCell(10)));
                        BigDecimal calRecuperacion = parseBigDecimal(getCellValueAsString(row.getCell(11)));

                      

                    
                        String letraRecuperacion = getCellValueAsString(row.getCell(12)); // Columna 12

   



                    // Verificar si ya existe un registro de calificaciones para este alumno y materia
                    Optional<Calificaciones> calificacionExistente = calificacionesRepository.findByAlumno_IdAlumnoAndMateria_IdMateria(alumno.getIdAlumno(), materia.getIdMateria());
    
                    if (calificacionExistente.isPresent()) {
                        // Actualizar solo los valores que aún no han sido ingresados
                        Calificaciones calificacion = calificacionExistente.get();
                        if (calificacion.getCorte1() == null) calificacion.setCorte1(corteI);
                        if (calificacion.getCorte2() == null) calificacion.setCorte2(corteII);
                        if (calificacion.getCorte3() == null) calificacion.setCorte3(corteIII);

                        // Actualiza siempre la calificación final (sin condición de null)
                        System.out.println(calFinal);

                       // if (calificacion.getCalificacionOrdinaria() == null) calificacion.setCalificacionOrdinaria(calFinal);
                        calificacion.setCalificacionOrdinaria(calFinal);

                        //if (calificacion.getCalificacionOrdinaria() == null) calificacion.setCalificacionOrdinaria(calFinal);
                        if (calificacion.getDesempeno() == null) calificacion.setDesempeno(letraFinal);
                        if (calificacion.getRecuperacion1() == null) calificacion.setRecuperacion1(recI);
                        if (calificacion.getRecuperacion2() == null) calificacion.setRecuperacion2(recII);
                        if (calificacion.getRecuperacion3() == null) calificacion.setRecuperacion3(recIII);
                        if (calificacion.getCalificacionOrdinariaR() == null) calificacion.setCalificacionOrdinariaR(calRecuperacion);
                        if (calificacion.getDesempeno1() == null) calificacion.setDesempeno1(letraRecuperacion);
    



                        
                        calificacionesRepository.save(calificacion);





                        System.out.println("Calificaciones actualizadas para alumno: " + matricula);
                    } else {
                        // Crear nueva calificación si no existía
                        Calificaciones nuevaCalificacion = new Calificaciones();
                        nuevaCalificacion.setAlumno(alumno);
                        nuevaCalificacion.setMateria(materia);
                        nuevaCalificacion.setCorte1(corteI);
                        nuevaCalificacion.setCorte2(corteII);
                        nuevaCalificacion.setCorte3(corteIII);
                         // Actualiza siempre la calificación final (sin condición de null)
                         System.out.println(calFinal);
                         nuevaCalificacion.setCalificacionOrdinaria(calFinal);
                       // nuevaCalificacion.setCalificacionOrdinaria(calFinal);
                        nuevaCalificacion.setDesempeno(letraFinal);
                        nuevaCalificacion.setRecuperacion1(recI);
                        nuevaCalificacion.setRecuperacion2(recII);
                        nuevaCalificacion.setRecuperacion3(recIII);
                        nuevaCalificacion.setCalificacionOrdinariaR(calRecuperacion);
                        nuevaCalificacion.setDesempeno1(letraRecuperacion);
    
                        calificacionesRepository.save(nuevaCalificacion);
                        System.out.println("Nueva calificación registrada para alumno: " + matricula);
                    }
                }
            }
    
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





                 
      
       

        

        private BigDecimal parseBigDecimal(String value) {
            try {
                return new BigDecimal(value.trim());
            } catch (NumberFormatException e) {
                // Manejo de error
                return BigDecimal.ZERO; // O algún valor por defecto
            }
        }


        private String getCellValueAsString(Cell cell) {
            if (cell == null) return "NA"; // Si la celda es nula, devolver "NA"
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim(); // Devolver texto
                case NUMERIC:
                    return String.format("%.0f", cell.getNumericCellValue());
                    //return String.valueOf(cell.getNumericCellValue()); // Devolver número como texto
                case BLANK:
                    //return "NA"; // Si está en blanco, devolver "NA"
                    return ""; // Si está en blanco, devolver vacio
                default:
                    return "NA"; // Para cualquier otro caso, devolver "NA"
            }
        }
        
        */
        //////////////////////////////////////////////////////////NO////////////////////////////////////////////
        
        
    // Lista de partículas que se consideran parte de un apellido compuesto
    private final List<String> particles = Arrays.asList("DE", "DEL", "LA", "LAS", "LOS", "Y");

    // Lista de nombres comunes (en mayúsculas) para detectar el orden "Nombres, Apellidos"
    private final List<String> commonNames = Arrays.asList(
            "JUAN", "CARLOS", "MIGUEL", "LUIS", "JOSÉ", "MARÍA", "ANA", "JONATHAN", "ANGEL", "PEDRO"
    );

    public void cargarAlumnosDesdeExcelCompletoREs(MultipartFile file, Grupo grupo, Materia materia) {
    try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
        Sheet sheet = workbook.getSheetAt(0);

        // Predicate para detectar celdas vacías o nulas
        Predicate<Cell> isEmpty = cell ->
            cell == null ||
            cell.getCellType() == CellType.BLANK ||
            getCellValueAsString(cell).trim().isEmpty();

        int lastRow = sheet.getLastRowNum();
        // Iterar desde la fila 2 (suponiendo que las dos primeras filas son encabezados)
        for (int i = 2; i <= lastRow; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                // Si la fila no existe, asumimos fin de los datos
                break;
            }

            // Celdas clave: columna 1 = matrícula, columna 2 = estudiante
            Cell celMatricula = row.getCell(1);
            Cell celEstudiante = row.getCell(2);

            // Si faltan matrícula o nombre, salta o detén el bucle
            if (isEmpty.test(celMatricula) || isEmpty.test(celEstudiante)) {
                break;  // o usa 'continue;' para ignorar solo esta fila y seguir
            }

            // Obtener valores
            String no          = getCellValueAsString(row.getCell(0));
            String matricula   = getCellValueAsString(celMatricula).trim();
            String estudiante  = getCellValueAsString(celEstudiante).trim();

            // ----------------------------------------------------------------
            // Lógica de separación de nombre y apellidos (igual que antes)
            String apePatFinal = "";
            String apeMatFinal = "";
            String nombreFinal = "";
            String[] tokens    = estudiante.split("\\s+");

            if (tokens.length == 4) {
                if (tokens[1].equalsIgnoreCase("DE")) {
                    apePatFinal  = tokens[0];
                    apeMatFinal  = tokens[1] + " " + tokens[2];
                    nombreFinal  = tokens[3];
                } else if (tokens[2].equalsIgnoreCase("DE")) {
                    nombreFinal  = tokens[0];
                    apePatFinal  = tokens[1];
                    apeMatFinal  = tokens[2] + " " + tokens[3];
                } else {
                    if (commonNames.contains(tokens[0].toUpperCase())) {
                        nombreFinal = tokens[0];
                        apePatFinal = tokens[1];
                        apeMatFinal = tokens[2] + " " + tokens[3];
                    } else {
                        apePatFinal = tokens[0];
                        apeMatFinal = tokens[1];
                        nombreFinal = tokens[2] + " " + tokens[3];
                    }
                }
            } else {
                int index = 0;
                StringBuilder apellidoPaterno = new StringBuilder();
                StringBuilder apellidoMaterno = new StringBuilder();
                StringBuilder nombres         = new StringBuilder();
                // Primer apellido
                if (tokens.length > 0) {
                    apellidoPaterno.append(tokens[index++]);
                    while (index < tokens.length - 1 && particles.contains(tokens[index].toUpperCase())) {
                        apellidoPaterno.append(" ").append(tokens[index++]);
                        if (index < tokens.length - 1) {
                            apellidoPaterno.append(" ").append(tokens[index++]);
                        }
                    }
                }
                // Segundo apellido
                if (index < tokens.length - 1) {
                    apellidoMaterno.append(tokens[index++]);
                    while (index < tokens.length - 1 && particles.contains(tokens[index].toUpperCase())) {
                        apellidoMaterno.append(" ").append(tokens[index++]);
                        if (index < tokens.length - 1) {
                            apellidoMaterno.append(" ").append(tokens[index++]);
                        }
                    }
                }
                // Nombres restantes
                while (index < tokens.length) {
                    nombres.append(tokens[index++]).append(" ");
                }
                apePatFinal = apellidoPaterno.toString().trim();
                apeMatFinal = apellidoMaterno.toString().trim();
                nombreFinal = nombres.toString().trim();
            }
            // ----------------------------------------------------------------

            // Crear o actualizar Alumno
            Alumno alumno;
            if (!alumnoRepository.existsByMatricula(matricula)) {
                alumno = new Alumno();
                alumno.setMatricula(matricula);
                alumno.setNombre(nombreFinal);
                alumno.setApellidoPaterno(apePatFinal);
                alumno.setApellidoMaterno(apeMatFinal);
                alumno = alumnoRepository.save(alumno);
                System.out.println("Alumno agregado: " + matricula);
            } else {
                alumno = alumnoRepository.findByMatricula(matricula).orElse(null);
                System.out.println("Ya registrado: " + matricula);
            }

            // Asignar al grupo si no existe la relación
            if (!alumnoGrupoRepository
                    .existsByAlumno_IdAlumnoAndGrupo_IdGrupo(alumno.getIdAlumno(), grupo.getIdGrupo())) {
                grupoService.asignarGrupoAAlumno(alumno.getIdAlumno(), grupo.getIdGrupo());
                System.out.println("Asignado al grupo: " + grupo.getIdGrupo());
            }

            // Leer y parsear calificaciones
            BigDecimal corteI      = parseBigDecimal(getCellValueAsString(row.getCell(3)));
            BigDecimal corteII     = parseBigDecimal(getCellValueAsString(row.getCell(4)));
            BigDecimal corteIII    = parseBigDecimal(getCellValueAsString(row.getCell(5)));
            BigDecimal calFinal    = parseBigDecimal(getCellValueAsString(row.getCell(6)));
            String letraFinal      = getCellValueAsString(row.getCell(7));
            BigDecimal recI        = parseBigDecimal(getCellValueAsString(row.getCell(8)));
            BigDecimal recII       = parseBigDecimal(getCellValueAsString(row.getCell(9)));
            BigDecimal recIII      = parseBigDecimal(getCellValueAsString(row.getCell(10)));
            BigDecimal calRecup    = parseBigDecimal(getCellValueAsString(row.getCell(11)));
            String letraRecup      = getCellValueAsString(row.getCell(12));

            // Guardar o actualizar Calificaciones
            Optional<Calificaciones> optCal = calificacionesRepository
                    .findByAlumno_IdAlumnoAndMateria_IdMateria(alumno.getIdAlumno(), materia.getIdMateria());

            if (optCal.isPresent()) {
                Calificaciones cal = optCal.get();
                if (cal.getCorte1() == null)          cal.setCorte1(corteI);
                if (cal.getCorte2() == null)          cal.setCorte2(corteII);
                if (cal.getCorte3() == null)          cal.setCorte3(corteIII);
                cal.setCalificacionOrdinaria(calFinal);
                if (cal.getDesempeno() == null)       cal.setDesempeno(letraFinal);
                if (cal.getRecuperacion1() == null)   cal.setRecuperacion1(recI);
                if (cal.getRecuperacion2() == null)   cal.setRecuperacion2(recII);
                if (cal.getRecuperacion3() == null)   cal.setRecuperacion3(recIII);
                if (cal.getCalificacionOrdinariaR() == null) cal.setCalificacionOrdinariaR(calRecup);
                if (cal.getDesempeno1() == null)      cal.setDesempeno1(letraRecup);
                calificacionesRepository.save(cal);
                System.out.println("Calificaciones actualizadas: " + matricula);
            } else {
                Calificaciones nuevo = new Calificaciones();
                nuevo.setAlumno(alumno);
                nuevo.setMateria(materia);
                nuevo.setCorte1(corteI);
                nuevo.setCorte2(corteII);
                nuevo.setCorte3(corteIII);
                nuevo.setCalificacionOrdinaria(calFinal);
                nuevo.setDesempeno(letraFinal);
                nuevo.setRecuperacion1(recI);
                nuevo.setRecuperacion2(recII);
                nuevo.setRecuperacion3(recIII);
                nuevo.setCalificacionOrdinariaR(calRecup);
                nuevo.setDesempeno1(letraRecup);
                calificacionesRepository.save(nuevo);
                System.out.println("Nueva calificación registrada: " + matricula);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private BigDecimal parseBigDecimal(String value) {
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "NA";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.format("%.0f", cell.getNumericCellValue());
            case BLANK:
                return "";
            default:
                return "NA";
        }
    }
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        /* 
        private String getCellValueAsString(Cell cell) {
            if (cell == null) {
                return "";
            }
    
            switch (cell.getCellType()) {
                case NUMERIC:
                    return String.format("%.0f", cell.getNumericCellValue());
                case STRING:
                    return cell.getStringCellValue();
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    return cell.getCellFormula();
                default:
                    return "";
            }
        }
        */
           
        
        private int getSpaces(String s) {
            String[] _name = s.trim().split(" ");
            return _name.length - 1;
        }
        

        ///prueba
        /// 
        /// 
        /// 
        /// 
        public List<CalificacionDTO> obtenerCalificacionesPorMateria(Long idMateria) {
            // Llama directamente al método del repositorio que ya devuelve una lista de CalificacionDTO
            return calificacionesRepository.findByMateria_IdMateria(idMateria);
        }


        //promedio general por alumno
        public Map<Long, BigDecimal> obtenerPromedioPorMateriaYGrupo(Long idMateria, Long idGrupo) {
            List<Object[]> resultados = calificacionesRepository.findPromedioPorMateriaYGrupo(idMateria, idGrupo);
            Map<Long, BigDecimal> promedioPorAlumno = new HashMap<>();
        
            for (Object[] resultado : resultados) {
                Long idAlumno = (Long) resultado[0];
                Double promedio = (Double) resultado[1];
                BigDecimal promedioDecimal = BigDecimal.valueOf(promedio);
                promedioPorAlumno.put(idAlumno, promedioDecimal);
            }
        
            return promedioPorAlumno;
        }
        

      
        /* 
        public List<CalificacionDTO> obtenerCalificacionesPorGrupo(Long idGrupo) {
            // Llama directamente al método del repositorio que devuelve una lista de CalificacionDTO por grupo
            return calificacionesRepository.findByGrupo_IdGrupo(idGrupo);
        }
        */

  
        /* 
        public List<CalificacionDTO> obtenerCalificacionesPorGrupo(Long idMateria) {
            return calificacionesRepository.findByMateria_IdMateria(idMateria).stream()
                .map(calif -> {
                    // Obtener los nombres del alumno y la materia
                    String nombreAlumno = calif.getAlumno().getNombre();
                    String nombreMateria = Optional.ofNullable(calif.getMateria().getMateriaDefecto())
                                                   .map(materiaDefecto -> materiaDefecto.getNombre())
                                                   .orElse("Materia no disponible");
        
                    System.out.println("Nombre del alumno: " + nombreAlumno + ", Nombre de la materia: " + nombreMateria);
                    
                    return new CalificacionDTO(
                        calif.getIdCalificacion(),
                        calif.getAlumno().getIdAlumno(),
                        calif.getMateria().getIdMateria(),
                        calif.getCorte1(),
                        calif.getCorte2(),
                        calif.getCorte3(),
                        calif.getDesempeno(),
                        calif.getCalificacionOrdinaria(),
                        calif.getRecuperacion1(),
                        calif.getRecuperacion2(),
                        calif.getRecuperacion3(),
                        calif.getCalificacionOrdinariaR(),
                        calif.getAcreditado(),
                        calif.getDesempeno1(),
                        calif.getComentario(),
                        nombreAlumno,  // Se usa la variable en lugar de llamar de nuevo a `calif.getAlumno().getNombre()`
                        nombreMateria
                    );

                })
                .collect(Collectors.toList());
        }
        
        / */

        
        
        

        
}
