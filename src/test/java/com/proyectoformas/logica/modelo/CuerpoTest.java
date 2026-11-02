package com.proyectoformas.logica.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CuerpoTest {

    private static final double DELTA = 1e-9;

    @Test
    void cuboSeMideDesdeSusCaras() {
        Cubo cubo = new Cubo(new Cuadrado(2));
        assertEquals(6, cubo.getFigurasComponentes().size());
        assertEquals(24, cubo.calcularPerimetro(), DELTA);
        assertEquals(24, cubo.calcularArea(), DELTA);
        assertEquals(8, cubo.calcularVolumen(), DELTA);

        // Modificar la copia devuelta no altera el cubo (encapsulamiento).
        cubo.getCaras().get(0).setLado(100);
        assertEquals(2, cubo.getArista(), DELTA);

        cubo.setArista(3);
        assertEquals(27, cubo.calcularVolumen(), DELTA);
    }

    @Test
    void cilindro() {
        Cilindro c = new Cilindro(new Circulo(1), 2);
        assertEquals(3, c.getFigurasComponentes().size());
        assertEquals(2 * Math.PI, c.getSuperficieLateral().getBase(), DELTA);
        assertEquals(2 * Math.PI + 4 * Math.PI, c.calcularArea(), DELTA);
        assertEquals(2 * Math.PI, c.calcularVolumen(), DELTA);

        c.setRadio(2);
        assertEquals(4 * Math.PI, c.getSuperficieLateral().getBase(), DELTA);
        assertEquals(8 * Math.PI, c.calcularVolumen(), DELTA);
    }

    @Test
    void tetraedro() {
        double a = 2;
        Tetraedro t = new Tetraedro(a);
        assertEquals(4, t.getFigurasComponentes().size());
        assertEquals(12, t.calcularPerimetro(), DELTA);
        assertEquals(Math.sqrt(3) * a * a, t.calcularArea(), DELTA);
        assertEquals(Math.pow(a, 3) / (6 * Math.sqrt(2)), t.calcularVolumen(), DELTA);
    }

    @Test
    void esfera() {
        Esfera e = new Esfera(3);
        assertTrue(e.getFigurasComponentes().isEmpty());
        assertEquals(36 * Math.PI, e.calcularArea(), DELTA);
        assertEquals(36 * Math.PI, e.calcularVolumen(), DELTA);
    }

    @Test
    void totalesDeLaColeccion() {
        Coleccion coleccion = new Coleccion();
        coleccion.agregarFigura(new Rectangulo(3, 4));
        coleccion.agregarCuerpo(new Cubo(2));
        assertEquals(12 + 24, coleccion.calcularAreaTotal(), DELTA);
        assertEquals(14 + 24, coleccion.calcularPerimetroTotal(), DELTA);
        assertEquals(8, coleccion.calcularVolumenTotal(), DELTA);
    }
}
