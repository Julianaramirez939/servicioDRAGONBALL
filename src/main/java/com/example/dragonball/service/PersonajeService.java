package com.example.dragonball.service;

import com.example.dragonball.model.Personaje;
import com.example.dragonball.repository.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    // Método para obtener todos los personajes
    public List<Personaje> getAllPersonajes() {
        return personajeRepository.findAll();
    }
}
