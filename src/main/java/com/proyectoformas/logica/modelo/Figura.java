package com.proyectoformas.logica.modelo;

/**
 * Clase base abstracta de todas las figuras geométricas planas.
 */
public abstract class Figura implements Forma, Identificable {

    private int id;

    protected Figura() {
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    /** Nombre legible de la figura, por ejemplo "Círculo". */
    public abstract String getNombre();

    @Override
    public void imprimirInformacion() {
        System.out.println(this);
        System.out.printf("    Perímetro: %.4f%n", calcularPerimetro());
        System.out.printf("    Área:      %.4f%n", calcularArea());
    }

    /**
     * Dos figuras son iguales si son de la misma clase concreta y tienen las
     * mismas medidas (las subclases comparan sus medidas). El id no interviene.
     */
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
