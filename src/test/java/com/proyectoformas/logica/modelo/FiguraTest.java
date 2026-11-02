package com.proyectoformas.logica.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FiguraTest {

    private static final double DELTA = 1e-9;

    @Test
    void circulo() {
        Circulo c = new Circulo(2);
        assertEquals(4 * Math.PI, c.calcularPerimetro(), DELTA);
        assertEquals(4 * Math.PI, c.calcularArea(), DELTA);
    }

    @Test
    void rectanguloYCuadrado() {
        Rectangulo r = new Rectangulo(3, 4);
        assertEquals(14, r.calcularPerimetro(), DELTA);
        assertEquals(12, r.calcularArea(), DELTA);

        Cuadrado c = new Cuadrado(5);
        c.setBase(2); // debe seguir siendo cuadrado
        assertEquals(2, c.getAltura(), DELTA);
        assertEquals(8, c.calcularPerimetro(), DELTA);
        assertNotEquals(new Rectangulo(2, 2), c);
    }

    @Test
    void rombo() {
        Rombo r = new Rombo(8, 6);
        assertEquals(5, r.getLado(), DELTA);
        assertEquals(20, r.calcularPerimetro(), DELTA);
        assertEquals(24, r.calcularArea(), DELTA);
    }

    @Test
    void fabricaDeTriangulosSegunLados() {
        assertInstanceOf(Equilatero.class, Triangulo.crear(3, 3, 3));
        assertInstanceOf(Isosceles.class, Triangulo.crear(5, 5, 6));
        assertInstanceOf(Isosceles.class, Triangulo.crear(6, 5, 5));
        assertInstanceOf(Isosceles.class, Triangulo.crear(5, 6, 5));
        assertInstanceOf(Escaleno.class, Triangulo.crear(3, 4, 5));
    }

    @Test
    void trianguloCalculos() {
        Triangulo t = Triangulo.crear(3, 4, 5);
        assertEquals(12, t.calcularPerimetro(), DELTA);
        assertEquals(6, t.calcularArea(), DELTA);
        assertEquals(t, Triangulo.crear(5, 3, 4));

        Isosceles i = (Isosceles) Triangulo.crear(6, 5, 5);
        assertEquals(5, i.getLadoIgual(), DELTA);
        assertEquals(6, i.getBase(), DELTA);
        assertEquals(12, i.calcularArea(), DELTA);
    }

    @Test
    void trianguloInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Triangulo.crear(1, 2, 3));
        assertThrows(IllegalArgumentException.class, () -> new Isosceles(4, 4));
        assertThrows(IllegalArgumentException.class, () -> new Escaleno(3, 3, 4));
        Escaleno e = new Escaleno(3, 4, 5);
        assertThrows(IllegalArgumentException.class, () -> e.setLadoA(4));
    }

    @Test
    void medidasNoPositivas() {
        assertThrows(IllegalArgumentException.class, () -> new Circulo(0));
        assertThrows(IllegalArgumentException.class, () -> new Rectangulo(-1, 2));
    }
}
