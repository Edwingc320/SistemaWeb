package com.example.sistemaweb.sistemaweb.Entities;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class CalificacionDTO {

    private Long idCalificacion;
    private Long idAlumno;
    private Long idMateria;
    private BigDecimal corte1;
    private BigDecimal corte2;
    private BigDecimal corte3;
    private String desempeno;
    private BigDecimal calificacionOrdinaria;
    private BigDecimal recuperacion1;
    private BigDecimal recuperacion2;
    private BigDecimal recuperacion3;
    private BigDecimal calificacionOrdinariaR;
    private boolean acreditado;
    private String desempeno1;
    private String comentario;

    private String nombreAlumno;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String matricula;
    private String nombreMateria;

    // Constructor actualizado
    public CalificacionDTO(Long idCalificacion, Long idAlumno, Long idMateria,
                    BigDecimal corte1, BigDecimal corte2, BigDecimal corte3, String desempeno,
                    BigDecimal calificacionOrdinaria, BigDecimal recuperacion1, BigDecimal recuperacion2,
                    BigDecimal recuperacion3, BigDecimal calificacionOrdinariaR, Boolean acreditado,
                    String desempeno1, String comentario, String nombreAlumno, String apellidoPaterno, 
                    String apellidoMaterno, String matricula, String nombreMateria) {
        this.idCalificacion = idCalificacion;
        this.idAlumno = idAlumno;
        this.idMateria = idMateria;
        this.corte1 = corte1;
        this.corte2 = corte2;
        this.corte3 = corte3;
        this.desempeno = desempeno;
        this.calificacionOrdinaria = calificacionOrdinaria;
        this.recuperacion1 = recuperacion1;
        this.recuperacion2 = recuperacion2;
        this.recuperacion3 = recuperacion3;
        this.calificacionOrdinariaR = calificacionOrdinariaR;
        this.acreditado = acreditado;
        this.desempeno1 = desempeno1;
        this.comentario = comentario;
        this.nombreAlumno = nombreAlumno;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.matricula = matricula;
        this.nombreMateria = nombreMateria;
    }


    

    @Override
    public String toString() {
        return "CalificacionDTO [idCalificacion=" + idCalificacion + ", idAlumno=" + idAlumno + ", idMateria=" + idMateria +
               ", corte1=" + corte1 + ", corte2=" + corte2 + ", corte3=" + corte3 + ", desempeno=" + desempeno +
               ", calificacionOrdinaria=" + calificacionOrdinaria + ", recuperacion1=" + recuperacion1 +
               ", recuperacion2=" + recuperacion2 + ", recuperacion3=" + recuperacion3 + ", calificacionOrdinariaR=" + calificacionOrdinariaR +
               ", acreditado=" + acreditado + ", desempeno1=" + desempeno1 + ", comentario=" + comentario +
               ", nombreAlumno=" + nombreAlumno + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno +
               ", matricula=" + matricula + ", nombreMateria=" + nombreMateria + "]";
    }
}
