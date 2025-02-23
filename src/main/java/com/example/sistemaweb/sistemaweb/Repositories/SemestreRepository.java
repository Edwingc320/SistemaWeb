package com.example.sistemaweb.sistemaweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sistemaweb.sistemaweb.Entities.Semestre;

public interface SemestreRepository extends JpaRepository<Semestre, Integer> {
    // Aquí puedes agregar consultas personalizadas relacionadas con el semestre
    
}
