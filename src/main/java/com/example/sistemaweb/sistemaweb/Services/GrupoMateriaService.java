package com.example.sistemaweb.sistemaweb.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sistemaweb.sistemaweb.Entities.GrupoMateria;
import com.example.sistemaweb.sistemaweb.Entities.Materia;
import com.example.sistemaweb.sistemaweb.Entities.Grupo;
import com.example.sistemaweb.sistemaweb.Repositories.GrupoMateriaRepository;
import com.example.sistemaweb.sistemaweb.Repositories.MateriaRepository;
import com.example.sistemaweb.sistemaweb.Repositories.GrupoRepository;

@Service
public class GrupoMateriaService {

    @Autowired
    private GrupoMateriaRepository grupoMateriaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    // Método para asignar la materia a un grupo
    public void asignarMateriaAGrupo(Long idMateria, Long idGrupo) {
        // Obtener las entidades de Materia y Grupo usando sus IDs
        Materia materia = materiaRepository.findById(idMateria).orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        Grupo grupo = grupoRepository.findById(idGrupo).orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        // Crear un nuevo objeto de la entidad GrupoMateria
        GrupoMateria grupoMateria = new GrupoMateria(materia, grupo);

        // Guardar la relación en la base de datos
        grupoMateriaRepository.save(grupoMateria);
    }
}
