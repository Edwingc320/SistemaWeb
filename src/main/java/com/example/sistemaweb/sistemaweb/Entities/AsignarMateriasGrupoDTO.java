package com.example.sistemaweb.sistemaweb.Entities;

import java.util.List;

public class AsignarMateriasGrupoDTO {
    private int idGrupo;
    private List<Integer> idMaterias;

    // Getters y setters
    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public List<Integer> getIdMaterias() {
        return idMaterias;
    }

    public void setIdMaterias(List<Integer> idMaterias) {
        this.idMaterias = idMaterias;
    }
}
