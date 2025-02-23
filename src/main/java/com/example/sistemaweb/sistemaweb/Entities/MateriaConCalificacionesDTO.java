package com.example.sistemaweb.sistemaweb.Entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class MateriaConCalificacionesDTO {
    private String nombreMateria;
    private List<CalificacionDTO> calificaciones;

    // Constructor, getters y setters

}

