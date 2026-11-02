package com.proyectoformas.ui;

public class OpcionSalir extends OpcionMenu {

    public OpcionSalir(ConsolaVista vista) {
        super("Salir", null, vista);
    }

    @Override
    public void ejecutar() {
        getVista().mostrarMensaje("¡Hasta luego!");
    }

    @Override
    public boolean terminaMenu() {
        return true;
    }
}
