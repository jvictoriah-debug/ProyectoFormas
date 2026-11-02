package com.proyectoformas.logica.modelo;

/**
 * Triángulo con exactamente dos lados iguales (ladoA = ladoB) y una base distinta (ladoC).
 */
public class Isosceles extends Triangulo {

    public Isosceles(double ladoIgual, double base) {
        super(ladoIgual, ladoIgual, validarBaseDistinta(ladoIgual, base));
    }

    private static double validarBaseDistinta(double ladoIgual, double base) {
        if (Validador.iguales(ladoIgual, base)) {
            throw new IllegalArgumentException(
                    "En un triángulo isósceles la base debe ser distinta de los lados iguales");
        }
        return base;
    }

    public double getLadoIgual() {
        return getLadoA();
    }

    public void setLadoIgual(double ladoIgual) {
        setLados(ladoIgual, ladoIgual, validarBaseDistinta(ladoIgual, getBase()));
    }

    public double getBase() {
        return getLadoC();
    }

    public void setBase(double base) {
        setLados(getLadoIgual(), getLadoIgual(), validarBaseDistinta(getLadoIgual(), base));
    }

    public double getAltura() {
        double mitadBase = getBase() / 2;
        return Math.sqrt(getLadoIgual() * getLadoIgual() - mitadBase * mitadBase);
    }

    @Override
    public String getNombre() {
        return "Triángulo isósceles";
    }

    @Override
    public String toString() {
        return getNombre() + (getId() > 0 ? " #" + getId() : "")
                + String.format(" [ladoIgual=%.2f, base=%.2f]", getLadoIgual(), getBase());
    }
}
