package com.example.sistemaweb.sistemaweb.Services;

import com.example.sistemaweb.sistemaweb.Entities.Grupo;
import com.example.sistemaweb.sistemaweb.Entities.MateriaRequest;
import com.example.sistemaweb.sistemaweb.Entities.Periodo;
import com.example.sistemaweb.sistemaweb.Entities.Semestre;
import com.example.sistemaweb.sistemaweb.Repositories.PeriodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DatosTemporalesService {

    // Lista para almacenar los IDs de las materias temporalmente
    private List<Long> materiasTemporales = new ArrayList<>();
    private Long idGrupoTemporal;  // Para almacenar el ID del grupo temporalmente

    

    // Método para obtener los IDs de las materias almacenadas temporalmente
    public List<Long> getMateriasTemporales() {
        return materiasTemporales;
    }

    // Método para almacenar temporalmente el ID del grupo
    public void setIdGrupo(Long idGrupo) {
        this.idGrupoTemporal = idGrupo;
    }

    // Método para obtener el ID del grupo temporalmente almacenado
    public Long getIdGrupo() {
        return this.idGrupoTemporal;
    }

    // Método para limpiar los datos temporales después de asignar
    public void limpiarDatosTemporales() {
        materiasTemporales.clear();
        idGrupoTemporal = null;
    }
}
