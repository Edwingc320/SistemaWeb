package com.example.sistemaweb.sistemaweb.Repositories;

import com.example.sistemaweb.sistemaweb.Entities.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {
    boolean existsByIdProfesor(int idProfesor);  // Cambiar a int


    
}
