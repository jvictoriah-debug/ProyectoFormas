package com.proyectoformas.ui;

import com.proyectoformas.logica.controlador.ColeccionControlador;
import com.proyectoformas.logica.dao.DAOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Menú de consola simple. Guarda sus opciones como referencias a la clase
 * abstracta {@link OpcionMenu}; el método que realmente se ejecuta al elegir
 * una opción se resuelve en tiempo de ejecución (ligado dinámico).
 */
public class Menu {

    private String titulo;
    private final ConsolaVista vista;
    private final List<OpcionMenu> opciones = new ArrayList<>();

    public Menu(String titulo, ConsolaVista vista) {
        this.titulo = Objects.requireNonNull(titulo);
        this.vista = Objects.requireNonNull(vista);
    }

    /** Construye el menú principal con todas las opciones del sistema. */
    public static Menu crearMenuPrincipal(ColeccionControlador controlador, ConsolaVista vista) {
        Menu menu = new Menu("Cálculo de figuras y cuerpos geométricos", vista);
        menu.agregarOpcion(new OpcionAgregarFigura(controlador, vista));
        menu.agregarOpcion(new OpcionAgregarCuerpo(controlador, vista));
        menu.agregarOpcion(new OpcionListarFiguras(controlador, vista));
        menu.agregarOpcion(new OpcionListarCuerpos(controlador, vista));
        menu.agregarOpcion(new OpcionAreaTotal(controlador, vista));
        menu.agregarOpcion(new OpcionPerimetroTotal(controlador, vista));
        menu.agregarOpcion(new OpcionVolumenTotal(controlador, vista));
        menu.agregarOpcion(new OpcionSalir(vista));
        return menu;
    }

    public void agregarOpcion(OpcionMenu opcion) {
        opciones.add(Objects.requireNonNull(opcion));
    }

    /** Muestra el menú en un ciclo hasta que se elija una opción de salida. */
    public void mostrar() {
        if (opciones.isEmpty()) {
            throw new IllegalStateException("El menú no tiene opciones");
        }
        boolean terminar = false;
        while (!terminar) {
            vista.mostrarTitulo(titulo);
            for (int i = 0; i < opciones.size(); i++) {
                vista.mostrarMensaje((i + 1) + ". " + opciones.get(i).getDescripcion());
            }
            try {
                OpcionMenu opcion = opciones.get(vista.leerEntero("Seleccione una opción: ", 1, opciones.size()) - 1);
                opcion.ejecutar();              // ligado dinámico
                terminar = opcion.terminaMenu(); // ligado dinámico
            } catch (IllegalArgumentException e) {
                vista.mostrarError(e.getMessage());
            } catch (DAOException e) {
                vista.mostrarError("Problema con la base de datos: " + e.getMessage());
            } catch (NoSuchElementException e) {
                vista.mostrarMensaje(System.lineSeparator() + "Entrada finalizada. Saliendo...");
                terminar = true;
            }
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = Objects.requireNonNull(titulo);
    }

    public List<OpcionMenu> getOpciones() {
        return Collections.unmodifiableList(opciones);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu otro)) {
            return false;
        }
        return titulo.equals(otro.titulo) && opciones.equals(otro.opciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, opciones);
    }

    @Override
    public String toString() {
        return "Menu [titulo=" + titulo + ", opciones=" + opciones.size() + "]";
    }
}
