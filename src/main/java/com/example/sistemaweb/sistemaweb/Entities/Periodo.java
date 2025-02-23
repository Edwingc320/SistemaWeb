package com.example.sistemaweb.sistemaweb.Entities;


import jakarta.persistence.*;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "Periodo")
public class Periodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeriodo;

    

    @Column(name = "anio", nullable = false)  // Cambié "año" por "anio"
    private int anio;


    @Column(name = "semestre", nullable = false, length = 1)
    private char semestre;  // 'A' or 'B'



    public Periodo(Long idPeriodo, int anio, char semestre) {
        this.idPeriodo = idPeriodo;
        this.anio = anio;
        this.semestre = semestre;
    }

    // Constructor adicional para tu consulta
    public Periodo(int anio, char semestre) {
        this.anio = anio;
        this.semestre = semestre;
    }


    public Periodo() {
    }

   

    

    @Override
    public String toString() {
        return "Periodo{idPeriodo=" + idPeriodo + ", anio=" + anio + ", semestre='" + semestre + "'}";
    }

}

