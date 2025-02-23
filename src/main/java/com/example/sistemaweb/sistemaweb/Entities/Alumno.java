package com.example.sistemaweb.sistemaweb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Alumno")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlumno;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "apellidoMaterno", length = 100)
    private String apellidoMaterno;

    @Column(name = "apellidoPaterno", length = 100)
    private String apellidoPaterno;

    @Column(name = "matricula", unique = true, nullable = false, length = 20)
    private String matricula;

    public Alumno(Long idAlumno, String nombre, String apellidoMaterno, String apellidoPaterno, String matricula) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.matricula = matricula;
    }

    // Método agregado para evitar el error
    public Long getId() {
        return this.idAlumno;
    }
}
