package com.example.dragonball.service;

import com.example.dragonball.model.Capitulo;
import com.example.dragonball.model.Serie;
import com.example.dragonball.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    // Método para obtener la serie
    public Serie getSerie() {
        // Suponiendo que hay una única serie en la base de datos
        Serie serie = serieRepository.findAll().stream().findFirst().orElse(null);

        if (serie != null) {
            // Obtener el capítulo mejor calificado, manejando nulos
            Optional<Capitulo> capituloMejorCalificado = serie.getTemporadas().stream()
                    .flatMap(temporada -> temporada.getCapitulos().stream())
                    .filter(capitulo -> capitulo.getCalificacionPromedio() != null) // Filtra capítulos con calificación nula
                    .max(Comparator.comparing(Capitulo::getCalificacionPromedio));

            // Si se encontró un capítulo mejor calificado, lo establece en la serie
            capituloMejorCalificado.ifPresent(serie::setCapituloMejorCalificado);
        }

        return serie; // Puede devolver null si no hay series
    }

    // Método para guardar una nueva serie
    public Serie saveSerie(Serie serie) {
        return serieRepository.save(serie);
    }
}
