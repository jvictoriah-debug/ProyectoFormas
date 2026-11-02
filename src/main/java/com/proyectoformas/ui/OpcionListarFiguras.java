package com.proyectoformas.ui;

import com.proyectoformas.logica.controlador.ColeccionControlador;

public class OpcionListarFiguras extends OpcionMenu {

    public OpcionListarFiguras(ColeccionControlador controlador, ConsolaVista vista) {
        super("Listar todas las figuras", controlador, vista);
    }

    @Override
    public void ejecutar() {
        getVista().mostrarFormas("Figuras planas", getControlador().listarFiguras());
    }
}
