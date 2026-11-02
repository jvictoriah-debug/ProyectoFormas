package com.proyectoformas.ui;

import com.proyectoformas.logica.controlador.ColeccionControlador;

public class OpcionAreaTotal extends OpcionMenu {

    public OpcionAreaTotal(ColeccionControlador controlador, ConsolaVista vista) {
        super("Calcular el área total de la colección", controlador, vista);
    }

    @Override
    public void ejecutar() {
        getVista().mostrarTotal("Área total (figuras + superficie de cuerpos)",
                getControlador().calcularAreaTotal());
    }
}
