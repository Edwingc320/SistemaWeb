package com.example.sistemaweb.sistemaweb.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Materia")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMateria;

    @ManyToOne
    @JoinColumn(name = "clave", referencedColumnName = "clave", nullable = false)
    private MateriaDefecto materiaDefecto;

    @ManyToOne
    @JoinColumn(name = "id_periodo", nullable = false)
    private Periodo periodo;

    @ManyToOne
    @JoinColumn(name = "id_profesor", nullable = false)
    private Profesor profesor;

    // Constructor con todas las relaciones
    public Materia(MateriaDefecto materiaDefecto, Periodo periodo, Profesor profesor) {
        this.materiaDefecto = materiaDefecto;
        this.periodo = periodo;
        this.profesor = profesor;
    }

    // Método agregado para evitar el error
    public Long getId() {
        return this.idMateria;
    }

    @Override
    public String toString() {
        return "Materia [idMateria=" + idMateria + ", materiaDefecto=" + materiaDefecto + ", periodo=" + periodo
                + ", profesor=" + profesor + "]";
    }


}
