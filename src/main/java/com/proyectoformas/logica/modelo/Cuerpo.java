package com.proyectoformas.logica.modelo;

import java.util.List;

/**
 * Clase base abstracta de los cuerpos sólidos. Además de perímetro y área,
 * todo cuerpo sabe calcular su volumen.
 *
 * <p>Convenciones usadas:
 * <ul>
 *   <li>Perímetro: suma de las longitudes de todas las aristas del cuerpo
 *       (en la esfera, la longitud de su circunferencia máxima).</li>
 *   <li>Área: área de la superficie total.</li>
 * </ul>
 */
public abstract class Cuerpo implements Forma, Identificable {

    private int id;

    protected Cuerpo() {
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public abstract String getNombre();

    public abstract double calcularVolumen();

    /**
     * Figuras planas que componen el cuerpo (copias, para no romper el
     * encapsulamiento). La esfera devuelve una lista vacía.
     */
    public abstract List<Figura> getFigurasComponentes();

    @Override
    public void imprimirInformacion() {
        System.out.println(this);
        System.out.printf("    Perímetro: %.4f%n", calcularPerimetro());
        System.out.printf("    Área:      %.4f%n", calcularArea());
        System.out.printf("    Volumen:   %.4f%n", calcularVolumen());
        List<Figura> componentes = getFigurasComponentes();
        if (!componentes.isEmpty()) {
            System.out.println("    Compuesto por " + componentes.size() + " figuras: " + componentes);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getNombre() + (id > 0 ? " #" + id : "");
    }
}
