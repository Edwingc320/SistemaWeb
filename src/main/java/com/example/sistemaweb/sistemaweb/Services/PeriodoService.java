package com.example.sistemaweb.sistemaweb.Services;

import com.example.sistemaweb.sistemaweb.Entities.Grupo;
import com.example.sistemaweb.sistemaweb.Entities.Periodo;
import com.example.sistemaweb.sistemaweb.Entities.Semestre;
import com.example.sistemaweb.sistemaweb.Repositories.PeriodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  PeriodoService {

    // Inyectar JdbcTemplate de Spring Boot
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private  PeriodoRepository periodoRepository;

  
    // Obtener lista de periodos
    public List<Periodo> obtenerPeriodoID() {
        String sql = "SELECT * FROM periodo";

        // Usar JdbcTemplate para ejecutar la consulta
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Periodo periodo = new Periodo();
                periodo.setIdPeriodo(rs.getLong("id_periodo"));
                periodo.setAnio(rs.getInt("anio"));
                periodo.setSemestre(rs.getString("semestre").charAt(0));
                System.out.println("Periodo: " + periodo);
                return periodo;
        });
    }

    // Obtener periodo por ID
    
    public Optional<Periodo> findById(Integer id) {
        return periodoRepository.findById(id);
    }


    // Guardar un nuevo periodo
    public Periodo savePeriodo(Periodo periodo) {
        return periodoRepository.save(periodo);
    }

    // Obtener todos los periodos
    public List<Periodo> getAllPeriodos() {
        return periodoRepository.findAll();
    }



    // Actualizar un periodo existente
    public Periodo updatePeriodo(Periodo periodo) {
        return periodoRepository.save(periodo);
    }

    // Eliminar un periodo por su ID
    public void deletePeriodo(Integer id) {
        periodoRepository.deleteById(id);
    }

    

    

    
}
