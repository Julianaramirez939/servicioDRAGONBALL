package com.example.dragonball.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String clasificacion;
    private String descripcion;

    @Column(name = "poster_url")
    private String posterUrl;

    // Eliminando precision y scale
    @Column(name = "calificacion_promedio")
    private Double calificacionPromedio;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Temporada> temporadas;

    @Transient // No se guarda en la base de datos
    private Capitulo capituloMejorCalificado;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(Double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }

    public Capitulo getCapituloMejorCalificado() {
        return capituloMejorCalificado;
    }

    public void setCapituloMejorCalificado(Capitulo capituloMejorCalificado) {
        this.capituloMejorCalificado = capituloMejorCalificado;
    }
}
