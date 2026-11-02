package com.proyectoformas.ui;

import com.proyectoformas.logica.controlador.ColeccionControlador;

public class OpcionVolumenTotal extends OpcionMenu {

    public OpcionVolumenTotal(ColeccionControlador controlador, ConsolaVista vista) {
        super("Calcular el volumen total de la colección", controlador, vista);
    }

    @Override
    public void ejecutar() {
        getVista().mostrarTotal("Volumen total de los cuerpos", getControlador().calcularVolumenTotal());
    }
}
