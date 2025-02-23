package com.example.sistemaweb.sistemaweb.Services;

import com.example.sistemaweb.sistemaweb.Entities.Periodo;
import com.example.sistemaweb.sistemaweb.Entities.Profesor;
import com.example.sistemaweb.sistemaweb.Repositories.ProfesorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  ProfesorService {

    // Inyectar JdbcTemplate de Spring Boot
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProfesorRepository profesorRepository;

  
    // Obtener lista de profesores
    public List<Profesor> obtenerProfesores() {
        String sql = "SELECT * FROM profesor";

        // Usar JdbcTemplate para ejecutar la consulta
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Profesor profesor = new Profesor();
            profesor.setIdProfesor(rs.getInt("id_profesor"));
            profesor.setNombre(rs.getString("nombre"));
            profesor.setApellidoPaterno(rs.getString("apellido_paterno"));
            profesor.setApellidoMaterno(rs.getString("apellido_materno"));
            profesor.setClave(rs.getString("clave"));
            System.out.println("Profesor: " + profesor);
            return profesor;
        });
    }


    // Guardar un nuevo profesor
    public Profesor saveProfesor(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    // Obtener todos los profesores
    public List<Profesor> getAllProfesores() {
        return profesorRepository.findAll();
    }

    // Obtener un profesor por su ID
    public Optional<Profesor> getProfesorById(int id) {
        return profesorRepository.findById(id);
    }

    // Eliminar un profesor por su ID
    public void deleteProfesor(int id) {
        profesorRepository.deleteById(id);
    }


    /* 
    public Profesor obtenerProfesorPorId(long idProfesor) {
        String sql = "SELECT * FROM profesor WHERE id_profesor = ?";
        
        // Ejecutar la consulta con el parámetro idProfesor
        return jdbcTemplate.queryForObject(sql, new Object[]{idProfesor}, (rs, rowNum) -> {
            Profesor profesor = new Profesor();
            profesor.setIdProfesor(rs.getLong("id_profesor"));
            profesor.setNombre(rs.getString("nombre"));
            profesor.setApellidoPaterno(rs.getString("apellido_paterno"));
            profesor.setApellidoMaterno(rs.getString("apellido_materno"));
            profesor.setClave(rs.getString("clave"));
            return profesor;
        });
    }
    
    public Optional<Profesor> findById(Long id) {
        return profesorRepository.findById(id);

        
    }
    */

    
}
