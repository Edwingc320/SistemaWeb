package com.example.sistemaweb.sistemaweb.Services;

import com.example.sistemaweb.sistemaweb.Entities.MateriaDefecto;
import com.example.sistemaweb.sistemaweb.Entities.Periodo;
import com.example.sistemaweb.sistemaweb.Entities.Profesor;
import com.example.sistemaweb.sistemaweb.Entities.Semestre;
import com.example.sistemaweb.sistemaweb.Repositories.MateriaDefectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaDefectoService {

    private final MateriaDefectoRepository materiaDefectoRepository;

    @Autowired
    private PeriodoService periodoService;

    @Autowired
    private ProfesorService profesorService;


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public MateriaDefectoService(MateriaDefectoRepository materiaDefectoRepository) {
        this.materiaDefectoRepository = materiaDefectoRepository;
    }

    // Obtener todas las materias
    public List<MateriaDefecto> obtenerTodasLasMaterias() {
        return materiaDefectoRepository.findAll();
    }

    

    // Obtener las materias por semestre
    public List<MateriaDefecto> obtenerMateriasPorSemestre(Long idSemestre) {
        return materiaDefectoRepository.findBySemestre_IdSemestre(idSemestre);
    }

    // Obtener un semestre por su nombre
    // Método para obtener un objeto Semestre por su nombre
    public Semestre obtenerSemestrePorNombre(String semestreNombre) {
        String sql = "SELECT * FROM semestre WHERE nombre = ?";  // Ajusta el SQL según el esquema de tu base de datos

        return jdbcTemplate.queryForObject(sql, new Object[]{semestreNombre}, (rs, rowNum) -> {
            Semestre semestre = new Semestre();
            semestre.setIdSemestre(rs.getLong("id_semestre"));
            semestre.setNombre(rs.getString("nombre"));
            return semestre;
        });
    }



    

        public MateriaDefecto obtenerMateriaDefectoPorId(long idMateriaDefecto) {
        String sql = "SELECT * FROM materia_defecto WHERE id_materia_defecto = ?";
        
        return jdbcTemplate.queryForObject(sql, new Object[]{idMateriaDefecto}, (rs, rowNum) -> {
            MateriaDefecto materiaDefecto = new MateriaDefecto();
            materiaDefecto.setIdMateriaDefecto(rs.getLong("id_materia_defecto"));
            materiaDefecto.setNombre(rs.getString("nombre"));
            materiaDefecto.setClave(rs.getString("clave"));

            // Obtener el semestre como String
            String semestreNombre = rs.getString("semestre");

            // Obtener el objeto Semestre desde el nombre (o código)
            Semestre semestre = obtenerSemestrePorNombre(semestreNombre);

            // Asignar el objeto Semestre al campo correspondiente
            materiaDefecto.setSemestre(semestre);

            return materiaDefecto;
        });
    }


    public Optional<MateriaDefecto> findById(Long id) {
        return materiaDefectoRepository.findById(id);
    }

    

}
