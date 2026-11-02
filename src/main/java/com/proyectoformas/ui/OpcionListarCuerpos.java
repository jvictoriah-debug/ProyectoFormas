package com.proyectoformas.ui;

import com.proyectoformas.logica.controlador.ColeccionControlador;

public class OpcionListarCuerpos extends OpcionMenu {

    public OpcionListarCuerpos(ColeccionControlador controlador, ConsolaVista vista) {
        super("Listar todos los cuerpos", controlador, vista);
    }

    @Override
    public void ejecutar() {
        getVista().mostrarFormas("Cuerpos sólidos", getControlador().listarCuerpos());
    }
}
