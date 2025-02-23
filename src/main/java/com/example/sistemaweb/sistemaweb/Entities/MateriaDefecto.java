package com.example.sistemaweb.sistemaweb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "materia_defecto")
public class MateriaDefecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia_defecto")
    private Long idMateriaDefecto;

    @Column(nullable = false, unique = true, length = 20)
    private String clave;

    @Column(nullable = false, length = 100)
    private String nombre;

    // Relación ManyToOne con Semestre
    @ManyToOne
    @JoinColumn(name = "id_semestre", referencedColumnName = "id_semestre")
    private Semestre semestre;

    // Constructor con parámetros
    public MateriaDefecto(Long idMateriaDefecto, String clave, String nombre, Semestre semestre) {
        this.idMateriaDefecto = idMateriaDefecto;
        this.clave = clave;
        this.nombre = nombre;
        this.semestre = semestre;
    }

    @Override
    public String toString() {
        return "MateriaDefecto{idMateriaDefecto=" + idMateriaDefecto + ", clave='" + clave + "', nombre='" + nombre + "', semestre=" + semestre + "}";
    }
}
