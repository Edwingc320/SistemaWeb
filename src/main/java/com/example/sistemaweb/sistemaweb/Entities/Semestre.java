package com.example.sistemaweb.sistemaweb.Entities;


import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Semestre")
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_semestre")
    private Long idSemestre;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;


    


    public Semestre(Long idSemestre, String nombre) {
        this.idSemestre = idSemestre;
        this.nombre = nombre;
    }


    // Constructor adicional para tu consulta
    public Semestre(String nombre) {
        this.nombre = nombre;
    }
    public Semestre() {
    }



    /* 
    @ManyToOne
    @JoinColumn(name = "id_periodo", nullable = false)
    private Periodo periodo;  // Relación con Periodo

    public Semestre(Long idSemestre, String nombre, Periodo periodo) {
        this.idSemestre = idSemestre;
        this.nombre = nombre;
        this.periodo = periodo;
    }
    */
    @Override
    public String toString() {
        return "Semestre{idSemestre=" + idSemestre + ", nombre='" + nombre + "'}";
    }


}
