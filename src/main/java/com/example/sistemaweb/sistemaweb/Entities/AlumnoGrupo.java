package com.example.sistemaweb.sistemaweb.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Alumno_Grupo")
public class AlumnoGrupo {

    @EmbeddedId
    private AlumnoGrupoId id;

    @ManyToOne
    @MapsId("idAlumno")  // Mapea idAlumno de AlumnoGrupoId
    @JoinColumn(name = "id_alumno", nullable = false)  // Aseguramos que coincida con la columna en la DB
    private Alumno alumno;

    @ManyToOne
    @MapsId("idGrupo")  // Mapea idGrupo de AlumnoGrupoId
    @JoinColumn(name = "id_grupo", nullable = false)  // Aseguramos que coincida con la columna en la DB
    private Grupo grupo;

    public AlumnoGrupo(Alumno alumno, Grupo grupo) {
        this.alumno = alumno;
        this.grupo = grupo;
        this.id = new AlumnoGrupoId(alumno.getIdAlumno(), grupo.getIdGrupo());  // Asegúrate de usar getIdAlumno() y getIdGrupo()
    }
}
