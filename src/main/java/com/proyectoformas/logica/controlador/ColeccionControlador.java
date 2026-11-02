package com.proyectoformas.logica.controlador;

import com.proyectoformas.logica.dao.CuerpoDAO;
import com.proyectoformas.logica.dao.FiguraDAO;
import com.proyectoformas.logica.modelo.Cilindro;
import com.proyectoformas.logica.modelo.Circulo;
import com.proyectoformas.logica.modelo.Coleccion;
import com.proyectoformas.logica.modelo.Cuadrado;
import com.proyectoformas.logica.modelo.Cubo;
import com.proyectoformas.logica.modelo.Cuerpo;
import com.proyectoformas.logica.modelo.Esfera;
import com.proyectoformas.logica.modelo.Figura;
import com.proyectoformas.logica.modelo.Rectangulo;
import com.proyectoformas.logica.modelo.Rombo;
import com.proyectoformas.logica.modelo.Tetraedro;
import com.proyectoformas.logica.modelo.Triangulo;

import java.util.List;
import java.util.Objects;

/**
 * Controlador del patrón MVC. Recibe las peticiones de la vista, crea los
 * objetos del modelo, los persiste mediante los DAO y los agrega a la
 * {@link Coleccion}.
 */
public class ColeccionControlador {

    private final Coleccion coleccion;
    private FiguraDAO figuraDAO;
    private CuerpoDAO cuerpoDAO;

    public ColeccionControlador(Coleccion coleccion, FiguraDAO figuraDAO, CuerpoDAO cuerpoDAO) {
        this.coleccion = Objects.requireNonNull(coleccion);
        this.figuraDAO = Objects.requireNonNull(figuraDAO);
        this.cuerpoDAO = Objects.requireNonNull(cuerpoDAO);
    }

    /** Carga en la colección las formas guardadas previamente. */
    public void cargarDatos() {
        coleccion.setFiguras(figuraDAO.listarTodos());
        coleccion.setCuerpos(cuerpoDAO.listarTodos());
    }

    // ----- Figuras -----

    public Figura agregarCirculo(double radio) {
        return registrarFigura(new Circulo(radio));
    }

    public Figura agregarRectangulo(double base, double altura) {
        return registrarFigura(new Rectangulo(base, altura));
    }

    public Figura agregarCuadrado(double lado) {
        return registrarFigura(new Cuadrado(lado));
    }

    public Figura agregarRombo(double diagonalMayor, double diagonalMenor) {
        return registrarFigura(new Rombo(diagonalMayor, diagonalMenor));
    }

    /** El tipo concreto de triángulo se decide según las medidas de los lados. */
    public Figura agregarTriangulo(double ladoA, double ladoB, double ladoC) {
        return registrarFigura(Triangulo.crear(ladoA, ladoB, ladoC));
    }

    // ----- Cuerpos -----

    public Cuerpo agregarCubo(double arista) {
        return registrarCuerpo(new Cubo(new Cuadrado(arista)));
    }

    public Cuerpo agregarCilindro(double radio, double altura) {
        return registrarCuerpo(new Cilindro(new Circulo(radio), altura));
    }

    public Cuerpo agregarTetraedro(double arista) {
        return registrarCuerpo(new Tetraedro(arista));
    }

    public Cuerpo agregarEsfera(double radio) {
        return registrarCuerpo(new Esfera(radio));
    }

    private Figura registrarFigura(Figura figura) {
        figuraDAO.insertar(figura);
        coleccion.agregarFigura(figura);
        return figura;
    }

    private Cuerpo registrarCuerpo(Cuerpo cuerpo) {
        cuerpoDAO.insertar(cuerpo);
        coleccion.agregarCuerpo(cuerpo);
        return cuerpo;
    }

    // ----- Consultas -----

    public List<Figura> listarFiguras() {
        return coleccion.getFiguras();
    }

    public List<Cuerpo> listarCuerpos() {
        return coleccion.getCuerpos();
    }

    public double calcularAreaTotal() {
        return coleccion.calcularAreaTotal();
    }

    public double calcularPerimetroTotal() {
        return coleccion.calcularPerimetroTotal();
    }

    public double calcularVolumenTotal() {
        return coleccion.calcularVolumenTotal();
    }

    // ----- Getters y setters -----

    public Coleccion getColeccion() {
        return coleccion;
    }

    public FiguraDAO getFiguraDAO() {
        return figuraDAO;
    }

    public void setFiguraDAO(FiguraDAO figuraDAO) {
        this.figuraDAO = Objects.requireNonNull(figuraDAO);
    }

    public CuerpoDAO getCuerpoDAO() {
        return cuerpoDAO;
    }

    public void setCuerpoDAO(CuerpoDAO cuerpoDAO) {
        this.cuerpoDAO = Objects.requireNonNull(cuerpoDAO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ColeccionControlador otro)) {
            return false;
        }
        return coleccion.equals(otro.coleccion) && figuraDAO.equals(otro.figuraDAO)
                && cuerpoDAO.equals(otro.cuerpoDAO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coleccion, figuraDAO, cuerpoDAO);
    }

    @Override
    public String toString() {
        return "ColeccionControlador [" + coleccion + ", figuraDAO=" + figuraDAO + ", cuerpoDAO=" + cuerpoDAO + "]";
    }
}
