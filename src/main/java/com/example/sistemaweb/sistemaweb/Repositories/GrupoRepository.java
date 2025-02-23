package com.example.sistemaweb.sistemaweb.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sistemaweb.sistemaweb.Entities.Grupo;

import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    // Aquí puedes agregar métodos personalizados si los necesitas
    Grupo findByNombre(String nombre); // Método para encontrar un grupo por su nombre

    //grupo por semestre
    List<Grupo> findBySemestre_IdSemestre(Long idSemestre);
}



