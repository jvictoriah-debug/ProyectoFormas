package com.proyectoformas.logica.modelo;

/**
 * Utilidades de validación compartidas por las clases del modelo.
 * Solo es visible dentro del paquete del modelo.
 */
final class Validador {

    /** Tolerancia usada para comparar medidas de tipo double. */
    static final double EPSILON = 1e-9;

    private Validador() {
    }

    static double positivo(double valor, String nombreMedida) {
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor <= 0) {
            throw new IllegalArgumentException(
                    "La medida '" + nombreMedida + "' debe ser un número positivo (recibido: " + valor + ")");
        }
        return valor;
    }

    static boolean iguales(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }
}
