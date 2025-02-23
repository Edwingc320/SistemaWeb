package com.example.sistemaweb.sistemaweb.Entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter


@Embeddable
public class MateriaGrupoId implements Serializable {

    private Long idMateria;
    private Long idGrupo;

    // Default constructor
    public MateriaGrupoId() {}

    // Constructor that takes idMateria and idGrupo
    public MateriaGrupoId(Long idMateria, Long idGrupo) {
        this.idMateria = idMateria;
        this.idGrupo = idGrupo;
    }

    // Getters and setters
    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    // Override equals and hashCode to ensure proper behavior in collections and comparisons
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MateriaGrupoId that = (MateriaGrupoId) o;
        return idMateria.equals(that.idMateria) && idGrupo.equals(that.idGrupo);
    }

    @Override
    public int hashCode() {
        return 31 * idMateria.hashCode() + idGrupo.hashCode();
    }
}

