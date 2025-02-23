package com.example.sistemaweb.sistemaweb.Services;

import com.alibaba.excel.EasyExcel;
import com.example.sistemaweb.sistemaweb.Entities.Alumno;
import com.example.sistemaweb.sistemaweb.Entities.AlumnoGrupo;
import com.example.sistemaweb.sistemaweb.Entities.Grupo;
import com.example.sistemaweb.sistemaweb.Repositories.AlumnoGrupoRepository;
import com.example.sistemaweb.sistemaweb.Repositories.AlumnoRepository;
import com.example.sistemaweb.sistemaweb.Repositories.GrupoRepository;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


import java.io.File;
import java.util.List;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private AlumnoGrupoRepository alumnoGrupoRepository;

    @Autowired
    private GrupoService grupoService;

    






    // Métodos para agregar alumnos desde Excel y asociarlos a grupos

        

        public void agregarAlumnoGrupo(List<Alumno> alumnos, Grupo grupo) {
            for (Alumno alumno : alumnos) {
                // Asociamos cada alumno con el grupo
                AlumnoGrupo alumnoGrupo = new AlumnoGrupo();
                alumnoGrupo.setAlumno(alumno);  // Asocia el alumno
                alumnoGrupo.setGrupo(grupo);    // Asocia el grupo
                alumnoGrupoRepository.save(alumnoGrupo);  // Guarda la relación en la tabla Alumno_Grupo
            }
        }

        public void asociarAlumnoConGrupo(Long idAlumno, Long idGrupo) {
            // Buscar alumno y grupo por sus IDs
            Alumno alumno = alumnoRepository.findById(idAlumno)
                                            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
            Grupo grupo = grupoRepository.findById(idGrupo)
                                        .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

            // Crear la relación en la tabla Alumno_Grupo
            AlumnoGrupo alumnoGrupo = new AlumnoGrupo(alumno, grupo);
            alumnoGrupoRepository.save(alumnoGrupo);
        }

    
        public void cargarAlumnosDesdeExcel(MultipartFile file, Grupo grupo) {
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
    
        private int getSpaces(String s) {
            String[] _name = s.trim().split(" ");
            return _name.length - 1;
        }
    

    /// 
    /// 
}
