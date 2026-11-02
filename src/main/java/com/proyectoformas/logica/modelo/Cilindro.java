package com.proyectoformas.logica.modelo;

import java.util.List;
import java.util.Objects;

/**
 * Cilindro recto compuesto por dos bases circulares y una superficie lateral
 * que, desenrollada, es un rectángulo de base 2·π·r y altura h.
 */
public class Cilindro extends Cuerpo {

    private final Circulo baseInferior;
    private final Circulo baseSuperior;
    private final Rectangulo superficieLateral;

    public Cilindro(Circulo base, double altura) {
        Objects.requireNonNull(base, "La base del cilindro no puede ser nula");
        this.baseInferior = new Circulo(base.getRadio());
        this.baseSuperior = new Circulo(base.getRadio());
        this.superficieLateral = new Rectangulo(baseInferior.calcularPerimetro(), altura);
    }

    public Cilindro(double radio, double altura) {
        this(new Circulo(radio), altura);
    }

    /** El radio se obtiene de la base circular. */
    public double getRadio() {
        return baseInferior.getRadio();
    }

    public void setRadio(double radio) {
        baseInferior.setRadio(radio);
        baseSuperior.setRadio(radio);
        superficieLateral.setBase(baseInferior.calcularPerimetro());
    }

    /** La altura se obtiene de la superficie lateral. */
    public double getAltura() {
        return superficieLateral.getAltura();
    }

    public void setAltura(double altura) {
        superficieLateral.setAltura(altura);
    }

    public Circulo getBaseInferior() {
        return new Circulo(baseInferior.getRadio());
    }

    public Circulo getBaseSuperior() {
        return new Circulo(baseSuperior.getRadio());
    }

    public Rectangulo getSuperficieLateral() {
        return new Rectangulo(superficieLateral.getBase(), superficieLateral.getAltura());
    }

    @Override
    public List<Figura> getFigurasComponentes() {
        return List.of(getBaseInferior(), getBaseSuperior(), getSuperficieLateral());
    }

    @Override
    public String getNombre() {
        return "Cilindro";
    }

    /** Las aristas del cilindro son las dos circunferencias de sus bases. */
    @Override
    public double calcularPerimetro() {
        return baseInferior.calcularPerimetro() + baseSuperior.calcularPerimetro();
    }

    @Override
    public double calcularArea() {
        return baseInferior.calcularArea() + baseSuperior.calcularArea() + superficieLateral.calcularArea();
    }

    @Override
    public double calcularVolumen() {
        return baseInferior.calcularArea() * getAltura();
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Cilindro otro = (Cilindro) o;
        return baseInferior.equals(otro.baseInferior) && superficieLateral.equals(otro.superficieLateral);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), baseInferior, superficieLateral);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [radio=%.2f, altura=%.2f]", getRadio(), getAltura());
    }
}
