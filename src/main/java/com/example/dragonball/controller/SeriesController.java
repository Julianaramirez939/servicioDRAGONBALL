package com.example.dragonball.controller;

import com.example.dragonball.model.Serie;
import com.example.dragonball.model.Temporada;
import com.example.dragonball.model.Capitulo;
import com.example.dragonball.model.Personaje;
import com.example.dragonball.service.SerieService;
import com.example.dragonball.service.TemporadaService;
import com.example.dragonball.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    private SerieService serieService;

    @Autowired
    private TemporadaService temporadaService;

    @Autowired
    private PersonajeService personajeService;

    // Endpoint para la página principal
    @GetMapping("/")
    public String index(Model model) {
        Serie serie = serieService.getSerie();
        model.addAttribute("serie", serie);
        return "index"; // La vista de la página principal
    }

    // Endpoint para mostrar todos los personajes
    @GetMapping("/personajes")
    public String listarPersonajes(Model model) {
        List<Personaje> personajes = personajeService.getAllPersonajes();
        model.addAttribute("personajes", personajes);
        return "personajes"; // Vista para mostrar personajes y sus actores
    }

    // Endpoint para listar temporadas
    @GetMapping("/temporadas")
    public String listarTemporadas(Model model) {
        List<Temporada> temporadas = temporadaService.getAllTemporadas();
        model.addAttribute("temporadas", temporadas);
        return "listarTemporadas"; // Vista para listar temporadas
    }

    // Endpoint para listar capítulos de una temporada específica
    @GetMapping("/temporadas/{temporadaId}/capitulos")
    public String listarCapitulos(@PathVariable Long temporadaId, Model model) {
        Temporada temporada = temporadaService.getTemporadaById(temporadaId);
        if (temporada != null) {
            List<Capitulo> capitulos = temporada.getCapitulos();
            model.addAttribute("capitulos", capitulos);
            model.addAttribute("temporada", temporada);
            return "listarCapitulos"; // Vista para listar capítulos
        }
        model.addAttribute("error", "La temporada no fue encontrada.");
        return "error"; // Manejar el caso donde la temporada no se encuentra
    }

    // Endpoint para ver detalles de un capítulo
    @GetMapping("/temporadas/{temporadaId}/capitulos/{capituloId}")
    public String detallesCapitulo(@PathVariable Long temporadaId, @PathVariable Long capituloId, Model model) {
        Capitulo capitulo = temporadaService.getCapituloById(temporadaId, capituloId);
        if (capitulo != null) {
            model.addAttribute("capitulo", capitulo);
            model.addAttribute("temporada", temporadaService.getTemporadaById(temporadaId));
            return "detallesCapitulo"; // Vista para detalles del capítulo
        }
        model.addAttribute("error", "El capítulo no fue encontrado.");
        return "error"; // Redirigir a la página de error
    }

    // Endpoint para agregar una nueva temporada
    @GetMapping("/nueva-temporada")
    public String nuevaTemporadaForm(Model model) {
        model.addAttribute("temporada", new Temporada());
        return "nuevaTemporada"; // Vista para crear una nueva temporada
    }

    @PostMapping("/nueva-temporada")
    public String agregarTemporada(@ModelAttribute Temporada temporada, Model model) {
        Long serieId = 1L;

        if (temporadaService.existsByNumero(temporada.getNumero(), serieId)) {
            model.addAttribute("error", "El número de temporada ya existe para esta serie.");
            return "nuevaTemporada";
        }

        Serie serie = new Serie();
        serie.setId(serieId);
        temporada.setSerie(serie);

        temporadaService.saveTemporada(temporada);
        return "redirect:/series/temporadas";
    }

    // Endpoint para agregar un nuevo capítulo
    @GetMapping("/temporadas/{temporadaId}/capitulos/nuevo")
    public String nuevoCapituloForm(@PathVariable Long temporadaId, Model model) {
        Temporada temporada = temporadaService.getTemporadaById(temporadaId);
        if (temporada != null) {
            model.addAttribute("capitulo", new Capitulo());
            model.addAttribute("temporadaId", temporadaId);
            return "nuevoCapitulo"; // Vista para crear un nuevo capítulo
        }
        model.addAttribute("error", "La temporada no fue encontrada.");
        return "error"; // Manejar el caso donde la temporada no se encuentra
    }

    @PostMapping("/temporadas/{temporadaId}/capitulos/nuevo")
    public String agregarCapitulo(@PathVariable Long temporadaId, @ModelAttribute Capitulo capitulo, Model model) {
        Temporada temporada = temporadaService.getTemporadaById(temporadaId);
        if (temporada == null) {
            model.addAttribute("error", "La temporada no fue encontrada.");
            return "nuevoCapitulo";
        }

        try {
            capitulo.setTemporada(temporada);
            temporadaService.agregarCapitulo(temporadaId, capitulo);
            return "redirect:/series/temporadas/" + temporadaId + "/capitulos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "nuevoCapitulo";
        }
    }

    // Endpoint para calificar un capítulo
    @PostMapping("/temporadas/{temporadaId}/capitulos/{capituloId}/calificar")
    public String calificarCapitulo(@PathVariable Long temporadaId, @PathVariable Long capituloId, @RequestParam int calificacion) {
        Capitulo capitulo = temporadaService.getCapituloById(temporadaId, capituloId);
        if (capitulo != null) {
            capitulo.setCalificacionPromedio((double) calificacion);
            temporadaService.saveTemporada(capitulo.getTemporada());
            return "redirect:/series/temporadas/" + temporadaId + "/capitulos/" + capituloId;
        }
        return "error"; // Manejar el caso donde el capítulo no se encuentra
    }
}
