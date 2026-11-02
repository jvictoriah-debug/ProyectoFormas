package com.proyectoformas.ui;

import com.proyectoformas.logica.controlador.ColeccionControlador;
import com.proyectoformas.logica.modelo.Cuerpo;

public class OpcionAgregarCuerpo extends OpcionMenu {

    public OpcionAgregarCuerpo(ColeccionControlador controlador, ConsolaVista vista) {
        super("Agregar un cuerpo sólido", controlador, vista);
    }

    @Override
    public void ejecutar() {
        ConsolaVista vista = getVista();
        ColeccionControlador controlador = getControlador();

        vista.mostrarTitulo("Nuevo cuerpo");
        vista.mostrarMensaje("1. Cubo");
        vista.mostrarMensaje("2. Cilindro");
        vista.mostrarMensaje("3. Tetraedro");
        vista.mostrarMensaje("4. Esfera");
        int tipo = vista.leerEntero("Tipo de cuerpo: ", 1, 4);

        Cuerpo cuerpo = switch (tipo) {
            case 1 -> controlador.agregarCubo(vista.leerDoublePositivo("Lado de la cara cuadrada: "));
            case 2 -> controlador.agregarCilindro(
                    vista.leerDoublePositivo("Radio de la base circular: "),
                    vista.leerDoublePositivo("Altura: "));
            case 3 -> controlador.agregarTetraedro(
                    vista.leerDoublePositivo("Lado de la cara triangular equilátera: "));
            default -> controlador.agregarEsfera(vista.leerDoublePositivo("Radio: "));
        };

        vista.mostrarMensaje("Cuerpo agregado a la colección:");
        cuerpo.imprimirInformacion();
    }
}
