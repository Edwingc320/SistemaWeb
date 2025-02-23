package com.example.sistemaweb.sistemaweb.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Materia_Grupo")  // Fixed typo in table name
public class GrupoMateria {

    @EmbeddedId
    private MateriaGrupoId id;

    @ManyToOne
    @MapsId("idMateria")  
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;

    @ManyToOne
    @MapsId("idGrupo")  
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    public GrupoMateria(Materia materia, Grupo grupo) {
        this.materia = materia;
        this.grupo = grupo;
        this.id = new MateriaGrupoId(materia.getIdMateria(), grupo.getIdGrupo());  // Corrected to use MateriaGrupoId
    }
}

