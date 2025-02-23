package com.example.sistemaweb.sistemaweb.Entities;

import java.util.List;

public class ResponseMessage {

    private String mensaje;
    private List<Integer> idsMaterias;

    public ResponseMessage(String mensaje, List<Integer> idsMaterias) {
        this.mensaje = mensaje;
        this.idsMaterias = idsMaterias;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Integer> getIdsMaterias() {
        return idsMaterias;
    }

    public void setIdsMaterias(List<Integer> idsMaterias) {
        this.idsMaterias = idsMaterias;
    }
}
