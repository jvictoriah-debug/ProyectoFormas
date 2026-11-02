package com.proyectoformas.logica.modelo;

import java.util.Objects;

/**
 * Rombo definido por sus dos diagonales.
 */
public class Rombo extends Figura {

    private double diagonalMayor;
    private double diagonalMenor;

    public Rombo(double diagonalMayor, double diagonalMenor) {
        this.diagonalMayor = Validador.positivo(diagonalMayor, "diagonal mayor");
        this.diagonalMenor = Validador.positivo(diagonalMenor, "diagonal menor");
    }

    public double getDiagonalMayor() {
        return diagonalMayor;
    }

    public void setDiagonalMayor(double diagonalMayor) {
        this.diagonalMayor = Validador.positivo(diagonalMayor, "diagonal mayor");
    }

    public double getDiagonalMenor() {
        return diagonalMenor;
    }

    public void setDiagonalMenor(double diagonalMenor) {
        this.diagonalMenor = Validador.positivo(diagonalMenor, "diagonal menor");
    }

    /** El lado se obtiene por Pitágoras con las semidiagonales. */
    public double getLado() {
        return Math.hypot(diagonalMayor / 2, diagonalMenor / 2);
    }

    @Override
    public String getNombre() {
        return "Rombo";
    }

    @Override
    public double calcularPerimetro() {
        return 4 * getLado();
    }

    @Override
    public double calcularArea() {
        return diagonalMayor * diagonalMenor / 2;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Rombo otro = (Rombo) o;
        return Double.compare(diagonalMayor, otro.diagonalMayor) == 0
                && Double.compare(diagonalMenor, otro.diagonalMenor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), diagonalMayor, diagonalMenor);
    }

    @Override
    public String toString() {
        return super.toString()
                + String.format(" [diagonalMayor=%.2f, diagonalMenor=%.2f]", diagonalMayor, diagonalMenor);
    }
}
