package com.proyectoformas.logica.modelo;

import java.util.List;
import java.util.Objects;

/**
 * Esfera: único cuerpo que no está compuesto por figuras planas.
 */
public class Esfera extends Cuerpo {

    private double radio;

    public Esfera(double radio) {
        this.radio = Validador.positivo(radio, "radio");
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = Validador.positivo(radio, "radio");
    }

    public double getDiametro() {
        return 2 * radio;
    }

    @Override
    public List<Figura> getFigurasComponentes() {
        return List.of();
    }

    @Override
    public String getNombre() {
        return "Esfera";
    }

    /** Longitud de la circunferencia máxima de la esfera. */
    @Override
    public double calcularPerimetro() {
        return 2 * Math.PI * radio;
    }

    @Override
    public double calcularArea() {
        return 4 * Math.PI * radio * radio;
    }

    @Override
    public double calcularVolumen() {
        return 4.0 / 3.0 * Math.PI * Math.pow(radio, 3);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && Double.compare(radio, ((Esfera) o).radio) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), radio);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [radio=%.2f]", radio);
    }
}
