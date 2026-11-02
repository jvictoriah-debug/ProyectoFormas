package com.proyectoformas.logica.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Clase principal del modelo: aloja las colecciones de figuras planas y de
 * cuerpos sólidos instanciados en el programa y calcula sus totales.
 */
public class Coleccion {

    private final List<Figura> figuras;
    private final List<Cuerpo> cuerpos;

    public Coleccion() {
        this.figuras = new ArrayList<>();
        this.cuerpos = new ArrayList<>();
    }

    public Coleccion(List<Figura> figuras, List<Cuerpo> cuerpos) {
        this();
        setFiguras(figuras);
        setCuerpos(cuerpos);
    }

    public void agregarFigura(Figura figura) {
        figuras.add(Objects.requireNonNull(figura, "La figura no puede ser nula"));
    }

    public void agregarCuerpo(Cuerpo cuerpo) {
        cuerpos.add(Objects.requireNonNull(cuerpo, "El cuerpo no puede ser nulo"));
    }

    public List<Figura> getFiguras() {
        return Collections.unmodifiableList(figuras);
    }

    public void setFiguras(List<Figura> figuras) {
        this.figuras.clear();
        for (Figura figura : Objects.requireNonNull(figuras)) {
            agregarFigura(figura);
        }
    }

    public List<Cuerpo> getCuerpos() {
        return Collections.unmodifiableList(cuerpos);
    }

    public void setCuerpos(List<Cuerpo> cuerpos) {
        this.cuerpos.clear();
        for (Cuerpo cuerpo : Objects.requireNonNull(cuerpos)) {
            agregarCuerpo(cuerpo);
        }
    }

    /** Todas las formas (figuras y cuerpos) tratadas polimórficamente. */
    public List<Forma> getFormas() {
        List<Forma> formas = new ArrayList<>(figuras);
        formas.addAll(cuerpos);
        return formas;
    }

    public int getCantidadFiguras() {
        return figuras.size();
    }

    public int getCantidadCuerpos() {
        return cuerpos.size();
    }

    public double calcularPerimetroTotal() {
        double total = 0;
        for (Forma forma : getFormas()) {
            total += forma.calcularPerimetro();
        }
        return total;
    }

    public double calcularAreaTotal() {
        double total = 0;
        for (Forma forma : getFormas()) {
            total += forma.calcularArea();
        }
        return total;
    }

    public double calcularVolumenTotal() {
        double total = 0;
        for (Cuerpo cuerpo : cuerpos) {
            total += cuerpo.calcularVolumen();
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coleccion otra)) {
            return false;
        }
        return figuras.equals(otra.figuras) && cuerpos.equals(otra.cuerpos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(figuras, cuerpos);
    }

    @Override
    public String toString() {
        return "Coleccion [figuras=" + figuras.size() + ", cuerpos=" + cuerpos.size() + "]";
    }
}
