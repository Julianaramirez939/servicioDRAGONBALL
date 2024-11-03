package com.example.dragonball.repository;

import com.example.dragonball.model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {
    // Métodos personalizados si es necesario
}
