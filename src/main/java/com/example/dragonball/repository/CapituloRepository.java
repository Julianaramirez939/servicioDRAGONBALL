package com.example.dragonball.repository;


import com.example.dragonball.model.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapituloRepository extends JpaRepository<Capitulo, Long> {
    // Puedes agregar métodos personalizados si necesitas funcionalidades adicionales
}
