package com.example.sistemaweb.sistemaweb.Entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "profesor")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private int idProfesor;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "apellido_materno", length = 100)
    private String apellidoMaterno;

    @Column(name = "apellido_paterno", length = 100)
    private String apellidoPaterno;

    @Column(name = "clave", unique = true, nullable = false, length = 20)
    private String clave;

    public Profesor(int idProfesor, String nombre, String apellidoMaterno, String apellidoPaterno, String clave) {
        this.idProfesor = idProfesor;
        this.nombre = nombre;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.clave = clave;
    }

    public Profesor() {
    }

    @Override
    public String toString() {
        return "Profesor{idProfesor=" + idProfesor + ", nombre='" + nombre + "', apellidoMaterno='" + apellidoMaterno + "', apellidoPaterno='" + apellidoPaterno + "', clave='" + clave + "'}";
    }
}
