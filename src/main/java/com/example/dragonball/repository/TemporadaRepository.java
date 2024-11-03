package com.example.dragonball.repository;

import com.example.dragonball.model.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long> {
    // Método para verificar si existe una temporada con un número específico y un ID de serie
    boolean existsByNumeroAndSerie_Id(int numero, Long serieId);
}
