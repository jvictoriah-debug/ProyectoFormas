package com.proyectoformas.logica.modelo;

import java.util.Objects;

public class Rectangulo extends Figura {

    private double base;
    private double altura;

    public Rectangulo(double base, double altura) {
        this.base = Validador.positivo(base, "base");
        this.altura = Validador.positivo(altura, "altura");
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = Validador.positivo(base, "base");
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = Validador.positivo(altura, "altura");
    }

    public double getDiagonal() {
        return Math.hypot(base, altura);
    }

    @Override
    public String getNombre() {
        return "Rectángulo";
    }

    @Override
    public double calcularPerimetro() {
        return 2 * (base + altura);
    }

    @Override
    public double calcularArea() {
        return base * altura;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Rectangulo otro = (Rectangulo) o;
        return Double.compare(base, otro.base) == 0 && Double.compare(altura, otro.altura) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), base, altura);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [base=%.2f, altura=%.2f]", base, altura);
    }
}
