package com.example.dragonball.repository;


import com.example.dragonball.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
    // Aquí puedes agregar métodos personalizados si los necesitas
}
