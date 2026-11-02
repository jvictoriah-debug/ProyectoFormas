package com.proyectoformas.logica.modelo;

/**
 * Triángulo con sus tres lados distintos.
 */
public class Escaleno extends Triangulo {

    public Escaleno(double ladoA, double ladoB, double ladoC) {
        super(ladoA, ladoB, validarLadosDistintos(ladoA, ladoB, ladoC));
    }

    private static double validarLadosDistintos(double ladoA, double ladoB, double ladoC) {
        if (Validador.iguales(ladoA, ladoB) || Validador.iguales(ladoA, ladoC) || Validador.iguales(ladoB, ladoC)) {
            throw new IllegalArgumentException("En un triángulo escaleno los tres lados deben ser distintos");
        }
        return ladoC;
    }

    public void setLadoA(double ladoA) {
        setLados(ladoA, getLadoB(), validarLadosDistintos(ladoA, getLadoB(), getLadoC()));
    }

    public void setLadoB(double ladoB) {
        setLados(getLadoA(), ladoB, validarLadosDistintos(getLadoA(), ladoB, getLadoC()));
    }

    public void setLadoC(double ladoC) {
        setLados(getLadoA(), getLadoB(), validarLadosDistintos(getLadoA(), getLadoB(), ladoC));
    }

    @Override
    public String getNombre() {
        return "Triángulo escaleno";
    }
}
