package com.example.sistemaweb.sistemaweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaweb.sistemaweb.Entities.Alumno;
import com.example.sistemaweb.sistemaweb.Entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    
    // Buscar alumno por matrícula
    Optional<Alumno> findByMatricula(String matricula);

    // Verificar si existe un alumno con una matrícula
    boolean existsByMatricula(String matricula);

    // Devuelve todos los alumnos cuyo nombre contiene (ignore case) la cadena dada
    List<Alumno> findByNombreContainingIgnoreCase(String nombre);

}


