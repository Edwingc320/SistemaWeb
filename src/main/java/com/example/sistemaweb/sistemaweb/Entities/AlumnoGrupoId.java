package com.example.sistemaweb.sistemaweb.Entities;



import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AlumnoGrupoId implements Serializable {
    
    @Column(name = "id_alumno")  // Nombre de la columna en la DB
    private Long idAlumno;
    
    @Column(name = "id_grupo")  // Nombre de la columna en la DB
    private Long idGrupo;

    public AlumnoGrupoId(Long idAlumno, Long idGrupo) {
        this.idAlumno = idAlumno;
        this.idGrupo = idGrupo;
    }
}
