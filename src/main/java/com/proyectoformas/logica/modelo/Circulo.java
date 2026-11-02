package com.proyectoformas.logica.modelo;

import java.util.Objects;

public class Circulo extends Figura {

    private double radio;

    public Circulo(double radio) {
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
    public String getNombre() {
        return "Círculo";
    }

    @Override
    public double calcularPerimetro() {
        return 2 * Math.PI * radio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * radio * radio;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && Double.compare(radio, ((Circulo) o).radio) == 0;
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
