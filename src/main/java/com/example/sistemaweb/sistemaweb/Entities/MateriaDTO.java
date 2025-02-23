package com.example.sistemaweb.sistemaweb.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class MateriaDTO {
    private Long id;
    private String clave;
    private Long idPeriodo;
    private Long idProfesor;

    
    
}