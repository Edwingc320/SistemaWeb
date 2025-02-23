package com.example.sistemaweb.sistemaweb.Services;

import com.example.sistemaweb.sistemaweb.Entities.Materia;
import com.example.sistemaweb.sistemaweb.Entities.MateriaDTO;
import com.example.sistemaweb.sistemaweb.Entities.MateriaDefecto;
import com.example.sistemaweb.sistemaweb.Entities.Periodo;
import com.example.sistemaweb.sistemaweb.Entities.Profesor;
import com.example.sistemaweb.sistemaweb.Repositories.MateriaGrupoRepository;
import com.example.sistemaweb.sistemaweb.Repositories.MateriaRepository;
import com.example.sistemaweb.sistemaweb.Repositories.PeriodoRepository;
import com.example.sistemaweb.sistemaweb.Repositories.ProfesorRepository;
import com.example.sistemaweb.sistemaweb.Repositories.MateriaDefectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class asignarMateriasAGrupo {

  
    @Autowired
    private PeriodoRepository periodoRepository;  // Asumido que existe un repositorio para Periodo


    @Autowired
    private MateriaGrupoRepository materiaGrupoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private MateriaDefectoRepository materiaDefectoRepository; // Asumido que existe un repositorio para MateriaDefecto

    // Método para asignar materias a un grupo
    

    public void asignarMateriasAGrupo(int idGrupo, List<Long> idMaterias) {
        for (Long idMateria : idMaterias) {
            materiaGrupoRepository.asignarMateriaAGrupo(idMateria.intValue(), idGrupo);
        }
    }
    
    
    


    // Método modificado para aceptar una lista de IDs de materias (List<Integer>)
    public List<MateriaDTO> guardarMaterias(List<Long> idMaterias) {
        return idMaterias.stream()
            .map(idMateria -> {
                // Consultamos la MateriaDefecto utilizando el ID
                // Consultamos la MateriaDefecto utilizando el ID
                MateriaDefecto materiaDefecto = materiaDefectoRepository.findById(Long.valueOf(idMateria))
                .orElseThrow(() -> new RuntimeException("MateriaDefecto no encontrada"));


                // Consultamos el Periodo. Aquí asumimos que la relación con Periodo es correcta.
                Periodo periodo = periodoRepository.findById(idMateria.intValue())  // Convertimos Long a Integer
                .orElseThrow(() -> new RuntimeException("Periodo no encontrado"));


                // Consultamos al Profesor utilizando el ID de profesor asociado a la materia
                Profesor profesor = profesorRepository.findById(idMateria.intValue())  // Convertimos Long a Integer
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));


                // Creamos la nueva Materia con las relaciones correspondientes
                Materia materia = new Materia(materiaDefecto, periodo, profesor);

                // Guardamos la Materia en la base de datos
                Materia materiaGuardada = materiaRepository.save(materia);

                // Convertimos la entidad Materia guardada en un DTO
                MateriaDTO savedMateriaDTO = new MateriaDTO();
                savedMateriaDTO.setId(materiaGuardada.getIdMateria());
                savedMateriaDTO.setClave(materiaGuardada.getMateriaDefecto().getClave());  // Usamos la clave de MateriaDefecto
                savedMateriaDTO.setIdProfesor(Long.valueOf(materiaGuardada.getProfesor().getIdProfesor()));  // Convertimos a Long
                savedMateriaDTO.setIdPeriodo(materiaGuardada.getPeriodo().getIdPeriodo());  // ID del periodo asociado

                return savedMateriaDTO;
            })
            .collect(Collectors.toList());  // Recopilamos los resultados en una lista de DTOs
    }
}
