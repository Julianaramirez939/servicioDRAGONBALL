package com.example.dragonball.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String nombre;
    private String descripcion;

    // Eliminando precision y scale
    @Column(name = "calificacion_promedio")
    private Double calificacionPromedio;

    @ManyToOne
    @JoinColumn(name = "temporada_id", nullable = false)
    private Temporada temporada;

    @OneToMany(mappedBy = "capitulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenCapitulo> imagenes;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(Double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

    public List<ImagenCapitulo> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenCapitulo> imagenes) {
        this.imagenes = imagenes;
    }

    // Método para calcular la calificación promedio si tienes una lista de calificaciones
    public Double calcularCalificacionPromedio(List<Double> calificaciones) {
        if (calificaciones == null || calificaciones.isEmpty()) {
            return 0.0; // Si no hay calificaciones, devuelve 0.0
        }
        return calificaciones.stream()
                .filter(calificacion -> calificacion != null) // Filtra calificaciones nulas
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0); // Devuelve 0.0 si no hay calificaciones válidas
    }
}
