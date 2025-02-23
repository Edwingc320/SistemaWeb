package com.example.sistemaweb.sistemaweb.Repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sistemaweb.sistemaweb.Entities.*;
import com.example.sistemaweb.sistemaweb.Repositories.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CalificacionesRepository extends JpaRepository<Calificaciones, Long> {


   @Query("SELECT new com.example.sistemaweb.sistemaweb.Entities.CalificacionDTO(" +
   "c.idCalificacion, c.alumno.idAlumno, c.materia.idMateria, " +
   "COALESCE(c.corte1, 0), COALESCE(c.corte2, 0), COALESCE(c.corte3, 0), " +
   "COALESCE(c.desempeno, 'No disponible'), " +
   "COALESCE(c.calificacionOrdinaria, 0), COALESCE(c.recuperacion1, 0), " +
   "COALESCE(c.recuperacion2, 0), COALESCE(c.recuperacion3, 0), " +
   "COALESCE(c.calificacionOrdinariaR, 0), " +
   "COALESCE(c.acreditado, false), " +
   "COALESCE(c.desempeno1, 'No disponible'), COALESCE(c.comentario, 'No disponible'), " +
   "c.alumno.nombre, " +
   "c.alumno.apellidoPaterno, " +
   "c.alumno.apellidoMaterno, " +
   "c.alumno.matricula, " +
   "COALESCE(c.materia.materiaDefecto.nombre, 'Materia no disponible')) " +
   "FROM Calificaciones c WHERE c.materia.idMateria = :idMateria")
   List<CalificacionDTO> findByMateria_IdMateria(@Param("idMateria") Long idMateria);


   //CALIFICACIONES POR ALUMNO
   @Query("SELECT c FROM Calificaciones c WHERE c.alumno.idAlumno = :idAlumno AND c.materia.idMateria = :idMateria")
   Optional<Calificaciones> findByAlumno_IdAlumnoAndMateria_IdMateria(@Param("idAlumno") Long idAlumno, @Param("idMateria") Long idMateria);

   ///////CALIFICACION FINAL POR MATERIAS y alumnos
   /// 
   //@Query("SELECT c FROM Calificaciones c WHERE c.alumno.idAlumno = :idAlumno AND c.materia.idMateria = :idMateria"+
   
   @Query("SELECT c.alumno.idAlumno, " +
   "AVG(CASE WHEN COALESCE(c.calificacionOrdinaria, 0) > 0 THEN c.calificacionOrdinaria " +
   "         ELSE c.calificacionOrdinariaR END) " +
   "FROM Calificaciones c " +
   "GROUP BY c.alumno.idAlumno")
   //List<Object[]> findPromedioGeneralPorAlumno();
   List<Object[]> findPromedioPorMateriaYGrupo(@Param("idMateria") Long idMateria, 
                                                @Param("idGrupo") Long idGrupo);


   

}



   //public List<Calificaciones> findByMateria_IdMateria(Long idMateria);
   /* 
   @Query("SELECT new com.proyecto.integrador.proyecto501.Entities.CalificacionDTO(" +
   "c.idCalificacion, c.alumno.idAlumno, c.materia.idMateria, " +
   "c.corte1, c.corte2, c.corte3, c.desempeno, c.calificacionOrdinaria, " +
   "c.recuperacion1, c.recuperacion2, c.recuperacion3, c.calificacionOrdinariaR, " +
   "c.acreditado, c.desempeno1, c.comentario, c.alumno.nombre, " +
   "COALESCE(c.materia.materiaDefecto.nombre, 'Materia no disponible')) " +
   "FROM Calificaciones c WHERE c.materia.idMateria = :idMateria")
List<CalificacionDTO> findByMateria_IdMateria(@Param("idMateria") Long idMateria);
*/







/* 

@Query("SELECT new com.proyecto.integrador.proyecto501.Entities.CalificacionDTO(" +
       "c.idCalificacion, c.alumno.idAlumno, c.materia.idMateria, " +
       "COALESCE(c.corte1, 0), COALESCE(c.corte2, 0), COALESCE(c.corte3, 0), " +
       "COALESCE(c.desempeno, 'No disponible'), " +
       "COALESCE(c.calificacionOrdinaria, 0), COALESCE(c.recuperacion1, 0), " +
       "COALESCE(c.recuperacion2, 0), COALESCE(c.recuperacion3, 0), " +
       "COALESCE(c.calificacionOrdinariaR, 0), " +
       "COALESCE(c.acreditado, false), " +
       "COALESCE(c.desempeno1, 'No disponible'), COALESCE(c.comentario, 'No disponible'), " +
       "c.alumno.nombre, " +
       "COALESCE(c.materia.materiaDefecto.nombre, 'Materia no disponible')) " +
       "FROM Calificaciones c WHERE c.materia.idMateria = :idMateria")
List<CalificacionDTO> findByMateria_IdMateria(@Param("idMateria") Long idMateria);
*/