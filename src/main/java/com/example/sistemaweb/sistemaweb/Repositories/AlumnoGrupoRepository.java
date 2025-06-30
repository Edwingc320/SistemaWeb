/* 
package com.example.sistemaweb.sistemaweb.Repositories;


import com.example.sistemaweb.sistemaweb.Entities.AlumnoGrupo;
import com.example.sistemaweb.sistemaweb.Entities.AlumnoGrupoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoGrupoRepository extends JpaRepository<AlumnoGrupo, Long> {
    boolean existsByAlumno_IdAlumnoAndGrupo_IdGrupo(Long alumnoId, Long grupoId);
}


*/

package com.example.sistemaweb.sistemaweb.Repositories;

import com.example.sistemaweb.sistemaweb.Entities.AlumnoGrupo;
import com.example.sistemaweb.sistemaweb.Entities.AlumnoGrupoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoGrupoRepository 
        extends JpaRepository<AlumnoGrupo, AlumnoGrupoId> {

    // Este método custom seguirá recibiendo dos Longs y buscará dentro del composite key
    boolean existsByAlumno_IdAlumnoAndGrupo_IdGrupo(Long alumnoId, Long grupoId);

}
