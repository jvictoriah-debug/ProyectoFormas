package com.proyectoformas.logica.modelo;

import java.util.Arrays;

/**
 * Clase base de los triángulos. No se instancia directamente: el método de
 * fábrica {@link #crear(double, double, double)} decide, según las medidas de
 * los lados, si se crea un {@link Equilatero}, un {@link Isosceles} o un
 * {@link Escaleno}.
 */
public abstract class Triangulo extends Figura {

    private double ladoA;
    private double ladoB;
    private double ladoC;

    protected Triangulo(double ladoA, double ladoB, double ladoC) {
        setLados(ladoA, ladoB, ladoC);
    }

    /**
     * Crea el tipo de triángulo adecuado a partir de las medidas de sus lados.
     *
     * @throws IllegalArgumentException si las medidas no forman un triángulo
     */
    public static Triangulo crear(double ladoA, double ladoB, double ladoC) {
        validar(ladoA, ladoB, ladoC);
        boolean ab = Validador.iguales(ladoA, ladoB);
        boolean ac = Validador.iguales(ladoA, ladoC);
        boolean bc = Validador.iguales(ladoB, ladoC);

        if (ab && bc) {
            return new Equilatero(ladoA);
        }
        if (ab) {
            return new Isosceles(ladoA, ladoC);
        }
        if (ac) {
            return new Isosceles(ladoA, ladoB);
        }
        if (bc) {
            return new Isosceles(ladoB, ladoA);
        }
        return new Escaleno(ladoA, ladoB, ladoC);
    }

    /** Comprueba que los lados sean positivos y cumplan la desigualdad triangular. */
    public static void validar(double ladoA, double ladoB, double ladoC) {
        Validador.positivo(ladoA, "lado A");
        Validador.positivo(ladoB, "lado B");
        Validador.positivo(ladoC, "lado C");
        if (ladoA + ladoB <= ladoC || ladoA + ladoC <= ladoB || ladoB + ladoC <= ladoA) {
            throw new IllegalArgumentException(String.format(
                    "Los lados %.2f, %.2f y %.2f no cumplen la desigualdad triangular", ladoA, ladoB, ladoC));
        }
    }

    public double getLadoA() {
        return ladoA;
    }

    public double getLadoB() {
        return ladoB;
    }

    public double getLadoC() {
        return ladoC;
    }

    /**
     * Setter protegido: cada subclase expone sus propios setters públicos que
     * garantizan que el triángulo conserve su clasificación.
     */
    protected final void setLados(double ladoA, double ladoB, double ladoC) {
        validar(ladoA, ladoB, ladoC);
        this.ladoA = ladoA;
        this.ladoB = ladoB;
        this.ladoC = ladoC;
    }

    @Override
    public double calcularPerimetro() {
        return ladoA + ladoB + ladoC;
    }

    /** Área por la fórmula de Herón. */
    @Override
    public double calcularArea() {
        double s = calcularPerimetro() / 2;
        return Math.sqrt(Math.max(0, s * (s - ladoA) * (s - ladoB) * (s - ladoC)));
    }

    private double[] ladosOrdenados() {
        double[] lados = {ladoA, ladoB, ladoC};
        Arrays.sort(lados);
        return lados;
    }

    /** Dos triángulos son iguales si son del mismo tipo y tienen los mismos lados (en cualquier orden). */
    @Override
    public boolean equals(Object o) {
        return super.equals(o) && Arrays.equals(ladosOrdenados(), ((Triangulo) o).ladosOrdenados());
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Arrays.hashCode(ladosOrdenados());
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [lados=%.2f, %.2f, %.2f]", ladoA, ladoB, ladoC);
    }
}
