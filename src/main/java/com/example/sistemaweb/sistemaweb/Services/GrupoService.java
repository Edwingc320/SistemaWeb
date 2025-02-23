package com.example.sistemaweb.sistemaweb.Services;

import com.example.sistemaweb.sistemaweb.Entities.Grupo;
import com.example.sistemaweb.sistemaweb.Entities.Materia;
import com.example.sistemaweb.sistemaweb.Entities.Periodo;
import com.example.sistemaweb.sistemaweb.Entities.Semestre;
import com.example.sistemaweb.sistemaweb.Repositories.GrupoRepository;
import com.example.sistemaweb.sistemaweb.Repositories.MateriaRepository;
import com.example.sistemaweb.sistemaweb.Repositories.PeriodoRepository;
import com.example.sistemaweb.sistemaweb.Repositories.SemestreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {

    // Inyectar JdbcTemplate de Spring Boot
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private PeriodoRepository periodoRepository;

    @Autowired
    private MateriaRepository materiaRepository;


    // Obtener lista de grupos con nombre, semestre y periodo
    public List<Grupo> obtenerNombreGrupoPeriodo() {
        String sql = "SELECT g.id_grupo, g.nombre AS nombre_grupo, " +
                     "       s.nombre AS nombre_semestre, " +
                     "       p.semestre AS tipo_semestre, " +
                     "       p.anio AS anio_periodo " +
                     "FROM Grupo g " +
                     "JOIN Semestre s ON g.id_semestre = s.id_semestre " +
                     "JOIN Periodo p ON g.id_periodo = p.id_periodo";
    
        // Usar JdbcTemplate para ejecutar la consulta
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            // Crear el objeto Grupo
            Grupo grupo = new Grupo(
                rs.getLong("id_grupo"),
                rs.getString("nombre_grupo"),
                new Semestre(rs.getString("nombre_semestre")), // Asignar un objeto Semestre
                new Periodo(rs.getInt("anio_periodo"), rs.getString("tipo_semestre").charAt(0)) // Asignar un objeto Periodo
            );
            
            // Imprimir el objeto Grupo en consola para verificar los resultados
            System.out.println("Grupo: " + grupo); // Aquí es donde se imprimen los resultados
            
            return grupo;
        });
    }

    /// obtenerGruposPorSemestre

    public List<Grupo> obtenerGruposPorSemestre(Long idSemestre) {
        return grupoRepository.findBySemestre_IdSemestre(idSemestre);
    }
    
    

    //AGREGAR GRUPO
        @Transactional
    public Grupo agregarGrupo(String nombreGrupo, int idSemestre, int idPeriodo) {
        // Validar si el semestre existe
        Semestre semestre = semestreRepository.findById(idSemestre)
                .orElseThrow(() -> new IllegalArgumentException("Semestre no encontrado con ID: " + idSemestre));

        // Validar si el periodo existe
        Periodo periodo = periodoRepository.findById(idPeriodo)
                .orElseThrow(() -> new IllegalArgumentException("Periodo no encontrado con ID: " + idPeriodo));

        // Crear nuevo grupo sin pasar el idGrupo
        Grupo grupo = new Grupo(nombreGrupo, semestre, periodo);

        // Guardar grupo en la base de datos
        return grupoRepository.save(grupo);
    }


    //////////////////////

    

    // Asignar grupo a un alumno
    public void asignarGrupoAAlumno(Long idAlumno, Long idGrupo) {
        String sql = "INSERT INTO alumno_grupo (id_alumno, id_grupo) VALUES (?, ?)";

        jdbcTemplate.update(sql, idAlumno, idGrupo);
    }

    // Agregar un nuevo grupo
    public void agregarGrupo(String nombre, Long idSemestre, Long idPeriodo) {
        String sql = "INSERT INTO Grupo (nombre, id_semestre, id_periodo) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, nombre, idSemestre, idPeriodo);
    }



    
}
