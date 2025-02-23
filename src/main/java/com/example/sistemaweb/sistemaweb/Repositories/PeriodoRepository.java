package com.example.sistemaweb.sistemaweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sistemaweb.sistemaweb.Entities.Periodo;

public interface PeriodoRepository extends JpaRepository<Periodo, Integer> {
    // Aquí puedes agregar consultas personalizadas relacionadas con el periodo
    //
    // Por ejemplo, si quisieras buscar un periodo por su id, podrías hacerlo de la siguiente manera:
     Periodo findByIdPeriodo(Integer idPeriodo);

     boolean existsById(int idPeriodo);
}
