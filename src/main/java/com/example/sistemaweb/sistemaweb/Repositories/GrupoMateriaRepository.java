package com.example.sistemaweb.sistemaweb.Repositories;

// Repositorio GrupoMateriaRepository.java

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaweb.sistemaweb.Entities.GrupoMateria;
import com.example.sistemaweb.sistemaweb.Entities.MateriaGrupoId;

@Repository
public interface GrupoMateriaRepository extends JpaRepository<GrupoMateria, MateriaGrupoId> {
    // Aquí puedes agregar métodos personalizados si es necesario
}
