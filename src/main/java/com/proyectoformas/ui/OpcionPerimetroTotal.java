package com.proyectoformas.ui;

import com.proyectoformas.logica.controlador.ColeccionControlador;

public class OpcionPerimetroTotal extends OpcionMenu {

    public OpcionPerimetroTotal(ColeccionControlador controlador, ConsolaVista vista) {
        super("Calcular el perímetro total de la colección", controlador, vista);
    }

    @Override
    public void ejecutar() {
        getVista().mostrarTotal("Perímetro total (figuras + aristas de cuerpos)",
                getControlador().calcularPerimetroTotal());
    }
}
