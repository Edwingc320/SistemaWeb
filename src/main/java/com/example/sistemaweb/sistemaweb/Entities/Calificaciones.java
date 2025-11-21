
package com.example.sistemaweb.sistemaweb.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
//@Table(name = "Calificaciones")

@Table(name = "Calificaciones2", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_alumno", "id_materia"})
})
public class Calificaciones {

 


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCalificacion;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;

    @Column(name = "corte1")
    private BigDecimal corte1;

    @Column(name = "corte2")
    private BigDecimal corte2;

    @Column(name = "corte3")
    private BigDecimal corte3;

    @Column(name = "calificacion_ordinaria")
    private BigDecimal calificacionOrdinaria;

    @Column(name = "desempeño", length = 255)
    private String desempeno;

    @Column(name = "recuperacion1")
    private BigDecimal recuperacion1;

    @Column(name = "recuperacion2")
    private BigDecimal recuperacion2;

    @Column(name = "recuperacion3")
    private BigDecimal recuperacion3;

    @Column(name = "calificacion_ordinariar")
    private BigDecimal calificacionOrdinariaR;

    @Column(name = "desempeño1", length = 255)
    private String desempeno1;


    @Column(name = "acreditado")
    private boolean acreditado = false;

    @Column(name = "comentario", length = 255)
    private String comentario = null;


     // Getters y Setters
     public Boolean getAcreditado() { // Asegúrate de que este método existe
        return acreditado;
    }

    public void setAcreditado(Boolean acreditado) {
        this.acreditado = acreditado;
    }
    
}
