package com.example.dragonball.service;

import com.example.dragonball.model.Capitulo;
import com.example.dragonball.model.Temporada;
import com.example.dragonball.repository.TemporadaRepository;
import com.example.dragonball.repository.CapituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemporadaService {

    @Autowired
    private TemporadaRepository temporadaRepository;

    @Autowired
    private CapituloRepository capituloRepository;

    // Método para obtener todas las temporadas
    public List<Temporada> getAllTemporadas() {
        return temporadaRepository.findAll();
    }

    // Método para obtener una temporada por ID
    public Temporada getTemporadaById(Long id) {
        return temporadaRepository.findById(id).orElse(null); // Retorna null si no se encuentra la temporada
    }

    // Método para verificar si existe una temporada con el mismo número y serie
    public boolean existsByNumero(int numero, Long serieId) {
        return temporadaRepository.existsByNumeroAndSerie_Id(numero, serieId);
    }

    // Método para guardar una nueva temporada con validación de duplicados
    public Temporada saveTemporada(Temporada temporada) {
        if (existsByNumero(temporada.getNumero(), temporada.getSerie().getId())) {
            throw new IllegalArgumentException("Ya existe una temporada con el número " + temporada.getNumero() +
                    " para la serie " + temporada.getSerie().getId());
        }
        return temporadaRepository.save(temporada);
    }

    // Método para obtener un capítulo por ID dentro de una temporada
    public Capitulo getCapituloById(Long temporadaId, Long capituloId) {
        Optional<Temporada> temporadaOpt = temporadaRepository.findById(temporadaId);

        if (temporadaOpt.isPresent()) {
            return temporadaOpt.get().getCapitulos().stream()
                    .filter(capitulo -> capitulo.getId().equals(capituloId))
                    .findFirst()
                    .orElse(null); // Retorna null si no se encuentra el capítulo
        }
        return null; // Retorna null si no se encuentra la temporada
    }

    // Método para obtener todos los capítulos de todas las temporadas
    public List<Capitulo> getAllCapitulos() {
        return temporadaRepository.findAll().stream()
                .flatMap(temporada -> temporada.getCapitulos().stream())
                .toList(); // Obtiene todos los capítulos de todas las temporadas
    }

    // Método para agregar un capítulo a una temporada existente
    public void agregarCapitulo(Long temporadaId, Capitulo capitulo) {
        Temporada temporada = temporadaRepository.findById(temporadaId)
                .orElseThrow(() -> new IllegalArgumentException("La temporada no existe.")); // Lanza excepción si no encuentra la temporada

        // Verifica si el capítulo ya existe en la temporada
        boolean capituloExiste = temporada.getCapitulos().stream()
                .anyMatch(c -> c.getCodigo() == capitulo.getCodigo());

        if (capituloExiste) {
            throw new IllegalArgumentException("El capítulo número " + capitulo.getCodigo() +
                    " ya existe en la temporada " + temporada.getNumero());
        }

        capitulo.setTemporada(temporada); // Establecer la temporada en el capítulo
        temporada.getCapitulos().add(capitulo); // Agregar capítulo a la lista de capítulos

        // Guarda tanto el capítulo como la temporada
        capituloRepository.save(capitulo);
        temporadaRepository.save(temporada); // Guardar la temporada actualizada
    }
}
