package com.example.sistemaweb.sistemaweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.sistemaweb.sistemaweb.Entities.GrupoMateria;

@Repository
public interface MateriaGrupoRepository extends JpaRepository<GrupoMateria, Integer> {

    @Transactional
    @Modifying


    @Query(value = "INSERT INTO materia_grupo (id_materia, id_grupo) VALUES (:idMateria, :idGrupo)\r\n" + //
                "", nativeQuery = true)
    void asignarMateriaAGrupo(int idMateria, int idGrupo);


    //buscar materia por id grupo
    @Query(value = "SELECT * FROM materia_grupo WHERE id_grupo = :idGrupo", nativeQuery = true) void materiasGrupo(int idGrupo);

    
}
