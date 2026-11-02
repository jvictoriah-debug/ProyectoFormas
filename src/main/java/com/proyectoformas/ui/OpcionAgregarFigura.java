package com.proyectoformas.ui;

import com.proyectoformas.logica.controlador.ColeccionControlador;
import com.proyectoformas.logica.modelo.Figura;

public class OpcionAgregarFigura extends OpcionMenu {

    public OpcionAgregarFigura(ColeccionControlador controlador, ConsolaVista vista) {
        super("Agregar una figura plana", controlador, vista);
    }

    @Override
    public void ejecutar() {
        ConsolaVista vista = getVista();
        ColeccionControlador controlador = getControlador();

        vista.mostrarTitulo("Nueva figura");
        vista.mostrarMensaje("1. Círculo");
        vista.mostrarMensaje("2. Rectángulo");
        vista.mostrarMensaje("3. Cuadrado");
        vista.mostrarMensaje("4. Rombo");
        vista.mostrarMensaje("5. Triángulo (el tipo se determina por sus lados)");
        int tipo = vista.leerEntero("Tipo de figura: ", 1, 5);

        Figura figura = switch (tipo) {
            case 1 -> controlador.agregarCirculo(vista.leerDoublePositivo("Radio: "));
            case 2 -> controlador.agregarRectangulo(
                    vista.leerDoublePositivo("Base: "),
                    vista.leerDoublePositivo("Altura: "));
            case 3 -> controlador.agregarCuadrado(vista.leerDoublePositivo("Lado: "));
            case 4 -> controlador.agregarRombo(
                    vista.leerDoublePositivo("Diagonal mayor: "),
                    vista.leerDoublePositivo("Diagonal menor: "));
            default -> controlador.agregarTriangulo(
                    vista.leerDoublePositivo("Lado A: "),
                    vista.leerDoublePositivo("Lado B: "),
                    vista.leerDoublePositivo("Lado C: "));
        };

        vista.mostrarMensaje("Figura agregada a la colección:");
        figura.imprimirInformacion();
    }
}
