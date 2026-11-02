package com.proyectoformas.ui;

import com.proyectoformas.logica.modelo.Forma;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Vista del patrón MVC: concentra toda la lectura y escritura por consola.
 */
public class ConsolaVista {

    private final Scanner scanner;

    public ConsolaVista(Scanner scanner) {
        this.scanner = Objects.requireNonNull(scanner);
    }

    public ConsolaVista() {
        this(new Scanner(System.in));
    }

    public void mostrarTitulo(String titulo) {
        String linea = "=".repeat(titulo.length() + 4);
        System.out.println();
        System.out.println(linea);
        System.out.println("  " + titulo);
        System.out.println(linea);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }

    public void mostrarTotal(String concepto, double valor) {
        System.out.printf("%s: %.4f%n", concepto, valor);
    }

    /**
     * Muestra una lista de formas. Cada elemento ejecuta su propia versión de
     * imprimirInformacion() (ligado dinámico).
     */
    public void mostrarFormas(String titulo, List<? extends Forma> formas) {
        mostrarTitulo(titulo + " (" + formas.size() + ")");
        if (formas.isEmpty()) {
            mostrarMensaje("No hay elementos registrados.");
            return;
        }
        for (Forma forma : formas) {
            forma.imprimirInformacion();
        }
    }

    /**
     * Lee un entero dentro del rango indicado, repitiendo hasta que sea válido.
     *
     * @throws NoSuchElementException si la entrada estándar se cierra
     */
    public int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            String texto = leerLinea(mensaje);
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // se informa abajo
            }
            mostrarError("Ingrese un número entero entre " + minimo + " y " + maximo + ".");
        }
    }

    /** Lee un número real positivo; acepta coma o punto como separador decimal. */
    public double leerDoublePositivo(String mensaje) {
        while (true) {
            String texto = leerLinea(mensaje).replace(',', '.');
            try {
                double valor = Double.parseDouble(texto);
                if (valor > 0 && !Double.isInfinite(valor)) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // se informa abajo
            }
            mostrarError("Ingrese un número mayor que cero.");
        }
    }

    private String leerLinea(String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) {
            throw new NoSuchElementException("Fin de la entrada");
        }
        return scanner.nextLine().trim();
    }

    @Override
    public String toString() {
        return "ConsolaVista";
    }
}
