package com.proyectoformas.ui;

import com.proyectoformas.logica.controlador.ColeccionControlador;

import java.util.Objects;

/**
 * Opción genérica del menú. El {@link Menu} solo conoce esta clase abstracta
 * y al invocar {@link #ejecutar()} se ejecuta la implementación de la
 * subclase concreta (ligado dinámico).
 */
public abstract class OpcionMenu {

    private String descripcion;
    private final ColeccionControlador controlador;
    private final ConsolaVista vista;

    protected OpcionMenu(String descripcion, ColeccionControlador controlador, ConsolaVista vista) {
        this.descripcion = Objects.requireNonNull(descripcion);
        this.controlador = controlador;
        this.vista = Objects.requireNonNull(vista);
    }

    public abstract void ejecutar();

    /** Indica si, tras ejecutar la opción, el menú debe terminar. */
    public boolean terminaMenu() {
        return false;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = Objects.requireNonNull(descripcion);
    }

    protected ColeccionControlador getControlador() {
        return controlador;
    }

    protected ConsolaVista getVista() {
        return vista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return descripcion.equals(((OpcionMenu) o).descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), descripcion);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + descripcion + "]";
    }
}
