package com.example.sistemaweb.sistemaweb.Repositories;

import com.example.sistemaweb.sistemaweb.Entities.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;




public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findByMateriaDefectoClave(String clave);

    //imprimir todas las materias

    @Query("SELECT m FROM Materia m WHERE m.materiaDefecto.clave = :clave AND m.periodo.idPeriodo = :idPeriodo AND m.profesor.idProfesor = :idProfesor")
    Optional<Materia> findByClaveAndPeriodoAndProfesor(@Param("clave") String clave, @Param("idPeriodo") Long idPeriodo, @Param("idProfesor") int idProfesor);



    // Obtener materias por ID de grupo
    @Query(value = "SELECT m.* FROM materia m INNER JOIN materia_grupo gm ON m.id_materia = gm.id_materia WHERE gm.id_grupo = :idGrupo", nativeQuery = true)
    List<Materia> findMateriasByGrupoId(@Param("idGrupo") Long idGrupo);
    
}