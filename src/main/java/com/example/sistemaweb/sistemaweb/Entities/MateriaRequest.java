package com.example.sistemaweb.sistemaweb.Entities;

public class MateriaRequest {

    private String clave;
    private Periodo periodo;
    private Profesor profesor;

    // Getters y setters

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}
