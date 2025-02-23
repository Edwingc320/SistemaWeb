package com.example.sistemaweb.sistemaweb.Services;

import com.example.sistemaweb.sistemaweb.Entities.Grupo;
import com.example.sistemaweb.sistemaweb.Entities.Periodo;
import com.example.sistemaweb.sistemaweb.Entities.Semestre;
import com.example.sistemaweb.sistemaweb.Repositories.SemestreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemestreService {

    // Inyectar JdbcTemplate de Spring Boot
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final SemestreRepository semestreRepository;

    @Autowired
    public SemestreService(SemestreRepository semestreRepository) {
        this.semestreRepository = semestreRepository;
    }

        

    // Obtener lista de semestres
    public List<Semestre> obtenerSemestres() {
        String sql = "SELECT * FROM semestre";

        // Usar JdbcTemplate para ejecutar la consulta y mapear los resultados
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Semestre semestre = new Semestre();
            semestre.setIdSemestre(rs.getLong("id_semestre"));
            semestre.setNombre(rs.getString("nombre"));
            System.out.println("Semestre: " + semestre);
            return semestre;
        });
    }

    
   
}
