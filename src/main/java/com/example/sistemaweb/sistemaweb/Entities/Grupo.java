package com.example.sistemaweb.sistemaweb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Grupo")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupo;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idSemestre", nullable = false)
    private Semestre semestre;

    @ManyToOne
    @JoinColumn(name = "idPeriodo", nullable = false)
    private Periodo periodo;

    // Constructor sin idGrupo ya que es autogenerado
    public Grupo(String nombre, Semestre semestre, Periodo periodo) {
        this.nombre = nombre;
        this.semestre = semestre;
        this.periodo = periodo;
    }

    public Grupo(Long idGrupo, String nombre, Semestre semestre, Periodo periodo) {
        this.idGrupo = idGrupo;
        this.nombre = nombre;
        this.semestre = semestre;
        this.periodo = periodo;
    }

    public Grupo() {
    }

    public Long getId() {
        return this.idGrupo;
    }

    @Override
    public String toString() {
        return "Grupo{" +
            "idGrupo=" + idGrupo +
            ", nombre='" + nombre + '\'' +
            ", semestre=" + semestre.getNombre() + // Acceso a nombre de semestre
            ", periodo=" + periodo.getAnio() + "-" + periodo.getSemestre() + // Acceso a año y semestre
            '}';
    }
}


/* 

package com.proyecto.integrador.proyecto501.Entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "Grupo")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupo;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idSemestre", nullable = false)
    private Semestre semestre;

    @ManyToOne
    @JoinColumn(name = "idPeriodo", nullable = false)
    private Periodo periodo;




    public Grupo(Long idGrupo, String nombre, Semestre semestre, Periodo periodo) {
        this.idGrupo = idGrupo;
        this.nombre = nombre;
        this.semestre = semestre;
        this.periodo = periodo;
    }



    public Long getId() {
        return this.idGrupo;
    }


    @Override
    public String toString() {
        return "Grupo{" +
            "idGrupo=" + idGrupo +
            ", nombre='" + nombre + '\'' +
            ", semestre=" + semestre.getNombre() + // Acceso a nombre de semestre
            ", periodo=" + periodo.getAnio() + "-" + periodo.getSemestre() + // Acceso a año y semestre
            '}';
    }

    
}
*/