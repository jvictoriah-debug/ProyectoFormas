package com.proyectoformas.logica.modelo;

/**
 * Interfaz que impone a todas las formas del sistema la capacidad de
 * calcular su perímetro y su área a partir de sus propios atributos,
 * así como de imprimir su información.
 */
public interface Forma {

    double calcularPerimetro();

    double calcularArea();

    void imprimirInformacion();
}
