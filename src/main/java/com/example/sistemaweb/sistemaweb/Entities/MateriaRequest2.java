package com.example.sistemaweb.sistemaweb.Entities;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MateriaRequest2 {

    private int idGrupo;
    private List<Integer> idsMaterias;

    // Getters y Setters
}
