package com.proyectoformas.logica.modelo;

/**
 * Triángulo con sus tres lados iguales.
 */
public class Equilatero extends Triangulo {

    public Equilatero(double lado) {
        super(lado, lado, lado);
    }

    public double getLado() {
        return getLadoA();
    }

    public void setLado(double lado) {
        setLados(lado, lado, lado);
    }

    public double getAltura() {
        return Math.sqrt(3) / 2 * getLado();
    }

    @Override
    public String getNombre() {
        return "Triángulo equilátero";
    }

    @Override
    public String toString() {
        return getNombre() + (getId() > 0 ? " #" + getId() : "") + String.format(" [lado=%.2f]", getLado());
    }
}
