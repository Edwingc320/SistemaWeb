package com.example.sistemaweb.sistemaweb.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sistemaweb.sistemaweb.Entities.Grupo;
import com.example.sistemaweb.sistemaweb.Entities.Materia;
import com.example.sistemaweb.sistemaweb.Entities.MateriaDTO;
import com.example.sistemaweb.sistemaweb.Entities.GrupoMateria;
import com.example.sistemaweb.sistemaweb.Entities.MateriaGrupoId;
import com.example.sistemaweb.sistemaweb.Repositories.GrupoRepository;
import com.example.sistemaweb.sistemaweb.Repositories.MateriaGrupoRepository;
import com.example.sistemaweb.sistemaweb.Repositories.MateriaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaGrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaGrupoRepository materiaGrupoRepository;

    /**
     * Asigna una lista de materias a un grupo.
     * 
     * @param idGrupo    ID del grupo al que se asignarán las materias.
     * @param idMaterias Lista de IDs de las materias que se asignarán al grupo.
     * @throws IllegalArgumentException Si el grupo o alguna materia no existe.
     */
    
}

