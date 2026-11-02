package com.proyectoformas.logica.modelo;

/**
 * Un cuadrado es un rectángulo cuya base y altura son siempre iguales.
 * Se sobrescriben los setters heredados para mantener esa invariante.
 */
public class Cuadrado extends Rectangulo {

    public Cuadrado(double lado) {
        super(lado, lado);
    }

    public double getLado() {
        return getBase();
    }

    public void setLado(double lado) {
        super.setBase(lado);
        super.setAltura(lado);
    }

    @Override
    public void setBase(double base) {
        setLado(base);
    }

    @Override
    public void setAltura(double altura) {
        setLado(altura);
    }

    @Override
    public String getNombre() {
        return "Cuadrado";
    }

    // equals() y hashCode() heredados de Rectangulo son suficientes: comparan
    // la clase concreta (getClass) y ambas medidas.

    @Override
    public String toString() {
        return getNombre() + (getId() > 0 ? " #" + getId() : "") + String.format(" [lado=%.2f]", getLado());
    }
}
