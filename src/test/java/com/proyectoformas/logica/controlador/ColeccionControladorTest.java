package com.proyectoformas.logica.controlador;

import com.proyectoformas.logica.dao.memoria.CuerpoDAOMemoria;
import com.proyectoformas.logica.dao.memoria.FiguraDAOMemoria;
import com.proyectoformas.logica.modelo.Coleccion;
import com.proyectoformas.logica.modelo.Equilatero;
import com.proyectoformas.logica.modelo.Figura;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ColeccionControladorTest {

    @Test
    void agregarPersisteYCargarRecupera() {
        FiguraDAOMemoria figuraDAO = new FiguraDAOMemoria();
        CuerpoDAOMemoria cuerpoDAO = new CuerpoDAOMemoria();
        ColeccionControlador controlador = new ColeccionControlador(new Coleccion(), figuraDAO, cuerpoDAO);

        Figura t = controlador.agregarTriangulo(4, 4, 4);
        controlador.agregarEsfera(1);
        assertInstanceOf(Equilatero.class, t);
        assertEquals(1, t.getId());

        // Un controlador nuevo con los mismos DAO recupera los datos.
        ColeccionControlador otro = new ColeccionControlador(new Coleccion(), figuraDAO, cuerpoDAO);
        otro.cargarDatos();
        assertEquals(1, otro.listarFiguras().size());
        assertEquals(1, otro.listarCuerpos().size());
        assertEquals(controlador.calcularVolumenTotal(), otro.calcularVolumenTotal(), 1e-9);
    }
}
