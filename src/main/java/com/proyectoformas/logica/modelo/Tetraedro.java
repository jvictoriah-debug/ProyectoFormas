package com.proyectoformas.logica.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Tetraedro regular compuesto por cuatro caras que son triángulos equiláteros.
 */
public class Tetraedro extends Cuerpo {

    public static final int NUMERO_CARAS = 4;
    public static final int NUMERO_ARISTAS = 6;

    private final List<Equilatero> caras = new ArrayList<>(NUMERO_CARAS);

    public Tetraedro(Equilatero cara) {
        Objects.requireNonNull(cara, "La cara del tetraedro no puede ser nula");
        for (int i = 0; i < NUMERO_CARAS; i++) {
            caras.add(new Equilatero(cara.getLado()));
        }
    }

    public Tetraedro(double arista) {
        this(new Equilatero(arista));
    }

    /** La arista del tetraedro es el lado de cualquiera de sus caras. */
    public double getArista() {
        return caras.get(0).getLado();
    }

    public void setArista(double arista) {
        Validador.positivo(arista, "arista");
        for (Equilatero cara : caras) {
            cara.setLado(arista);
        }
    }

    /** Altura del tetraedro: distancia de un vértice a la cara opuesta. */
    public double getAltura() {
        return getArista() * Math.sqrt(2.0 / 3.0);
    }

    public List<Equilatero> getCaras() {
        List<Equilatero> copia = new ArrayList<>(NUMERO_CARAS);
        for (Equilatero cara : caras) {
            copia.add(new Equilatero(cara.getLado()));
        }
        return copia;
    }

    public void setCaras(Equilatero cara) {
        Objects.requireNonNull(cara, "La cara del tetraedro no puede ser nula");
        setArista(cara.getLado());
    }

    @Override
    public List<Figura> getFigurasComponentes() {
        return new ArrayList<>(getCaras());
    }

    @Override
    public String getNombre() {
        return "Tetraedro";
    }

    @Override
    public double calcularPerimetro() {
        return NUMERO_ARISTAS * getArista();
    }

    @Override
    public double calcularArea() {
        double area = 0;
        for (Equilatero cara : caras) {
            area += cara.calcularArea();
        }
        return area;
    }

    /** Volumen de una pirámide: (área de la base · altura) / 3. */
    @Override
    public double calcularVolumen() {
        return caras.get(0).calcularArea() * getAltura() / 3;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && caras.equals(((Tetraedro) o).caras);
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
