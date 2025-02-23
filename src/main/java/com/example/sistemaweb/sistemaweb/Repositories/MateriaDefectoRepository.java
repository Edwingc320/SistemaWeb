package com.example.sistemaweb.sistemaweb.Repositories;

import com.example.sistemaweb.sistemaweb.Entities.MateriaDefecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/* 
public interface MateriaDefectoRepository extends JpaRepository<MateriaDefecto, Long> {

    // Método para obtener las materias por semestre
    List<MateriaDefecto> findBySemestreIdSemestre(Long idSemestre);
}
*/

@Repository
public interface MateriaDefectoRepository extends JpaRepository<MateriaDefecto, Long> {
    // Método correcto para obtener materias por el id del semestre
    List<MateriaDefecto> findBySemestre_IdSemestre(Long idSemestre);


    //buscar materiaDefecto por id

    Optional<MateriaDefecto> findByIdMateriaDefecto(Long idMateriaDefecto);


    //Metodo buscar por clave materia 
    Optional<MateriaDefecto> findByClave(String clave);

    boolean existsByClave(String clave);


    
}

