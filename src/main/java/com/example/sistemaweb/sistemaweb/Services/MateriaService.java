package com.example.sistemaweb.sistemaweb.Services;

import com.example.sistemaweb.sistemaweb.Entities.Grupo;
import com.example.sistemaweb.sistemaweb.Entities.Materia;
import com.example.sistemaweb.sistemaweb.Entities.MateriaDefecto;
import com.example.sistemaweb.sistemaweb.Entities.Semestre;
import com.example.sistemaweb.sistemaweb.Entities.Periodo;
import com.example.sistemaweb.sistemaweb.Entities.Profesor;
import com.example.sistemaweb.sistemaweb.Repositories.GrupoRepository;
import com.example.sistemaweb.sistemaweb.Repositories.MateriaDefectoRepository;
import com.example.sistemaweb.sistemaweb.Repositories.MateriaRepository;
import com.example.sistemaweb.sistemaweb.Repositories.SemestreRepository;
import com.example.sistemaweb.sistemaweb.Repositories.PeriodoRepository;
import com.example.sistemaweb.sistemaweb.Repositories.ProfesorRepository;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MateriaService {
    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private PeriodoRepository periodoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private MateriaDefectoRepository materiaDefectoRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public List<Materia> listarTodas() {
        return materiaRepository.findAll();
    }

    public List<Materia> buscarPorClave(String clave) {
        return materiaRepository.findByMateriaDefectoClave(clave);
    }

    public Materia guardar(Materia materia) {
        return materiaRepository.save(materia);
    }

    public void guardarMateria(Materia materia) {
        materiaRepository.save(materia);
    }

    
    public List<Materia> obtenerMateriasPorGrupo(Long idGrupo) {
        return materiaRepository.findMateriasByGrupoId(idGrupo);
    }
    
    

    /* 
    @Transactional
    public Materia agregarMateria(MateriaDefecto clave, Periodo idPeriodo, Profesor idProfesor) {
        // Crear el objeto Materia y asignar valores
        Materia materia = new Materia();
        materia.setMateriaDefecto(clave);  // Asigna la clave de la materia
        materia.setPeriodo(idPeriodo);  // Asigna el id del periodo
        materia.setProfesor(idProfesor);  // Asigna el id del profesor
        
        // Guardar la materia en la base de datos y devolver el objeto guardado
        return materiaRepository.save(materia);
    }
    */
    
        

            
}
