package com.proyectoformas.logica.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Cubo compuesto por seis caras cuadradas. Sus medidas se obtienen de forma
 * indirecta a partir de dichas caras.
 */
public class Cubo extends Cuerpo {

    public static final int NUMERO_CARAS = 6;
    public static final int NUMERO_ARISTAS = 12;

    private final List<Cuadrado> caras = new ArrayList<>(NUMERO_CARAS);

    public Cubo(Cuadrado cara) {
        Objects.requireNonNull(cara, "La cara del cubo no puede ser nula");
        for (int i = 0; i < NUMERO_CARAS; i++) {
            caras.add(new Cuadrado(cara.getLado()));
        }
    }

    public Cubo(double arista) {
        this(new Cuadrado(arista));
    }

    /** La arista del cubo es el lado de cualquiera de sus caras. */
    public double getArista() {
        return caras.get(0).getLado();
    }

    public void setArista(double arista) {
        Validador.positivo(arista, "arista");
        for (Cuadrado cara : caras) {
            cara.setLado(arista);
        }
    }

    /** Devuelve copias de las caras para que no puedan modificarse por separado. */
    public List<Cuadrado> getCaras() {
        List<Cuadrado> copia = new ArrayList<>(NUMERO_CARAS);
        for (Cuadrado cara : caras) {
            copia.add(new Cuadrado(cara.getLado()));
        }
        return copia;
    }

    /** Reemplaza todas las caras por cuadrados iguales al recibido. */
    public void setCaras(Cuadrado cara) {
        Objects.requireNonNull(cara, "La cara del cubo no puede ser nula");
        setArista(cara.getLado());
    }

    @Override
    public List<Figura> getFigurasComponentes() {
        return new ArrayList<>(getCaras());
    }

    @Override
    public String getNombre() {
        return "Cubo";
    }

    @Override
    public double calcularPerimetro() {
        return NUMERO_ARISTAS * getArista();
    }

    @Override
    public double calcularArea() {
        double area = 0;
        for (Cuadrado cara : caras) {
            area += cara.calcularArea();
        }
        return area;
    }

    @Override
    public double calcularVolumen() {
        return caras.get(0).calcularArea() * getArista();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && caras.equals(((Cubo) o).caras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), caras);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [arista=%.2f]", getArista());
    }
}
