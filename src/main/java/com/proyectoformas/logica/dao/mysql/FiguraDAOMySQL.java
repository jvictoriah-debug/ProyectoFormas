package com.proyectoformas.logica.dao.mysql;

import com.proyectoformas.logica.dao.DAOException;
import com.proyectoformas.logica.dao.FiguraDAO;
import com.proyectoformas.logica.modelo.Circulo;
import com.proyectoformas.logica.modelo.Cuadrado;
import com.proyectoformas.logica.modelo.Figura;
import com.proyectoformas.logica.modelo.Rectangulo;
import com.proyectoformas.logica.modelo.Rombo;
import com.proyectoformas.logica.modelo.Triangulo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementación MySQL del DAO de figuras. Cada fila guarda el tipo de figura
 * y hasta tres medidas; al leerla se reconstruye el objeto correspondiente.
 */
public class FiguraDAOMySQL implements FiguraDAO {

    private static final int MAX_MEDIDAS = 3;

    private static final String INSERTAR =
            "INSERT INTO figura (tipo, medida1, medida2, medida3) VALUES (?, ?, ?, ?)";
    private static final String SELECCIONAR_TODAS =
            "SELECT id, tipo, medida1, medida2, medida3 FROM figura ORDER BY id";
    private static final String SELECCIONAR_POR_ID =
            "SELECT id, tipo, medida1, medida2, medida3 FROM figura WHERE id = ?";
    private static final String ACTUALIZAR =
            "UPDATE figura SET tipo = ?, medida1 = ?, medida2 = ?, medida3 = ? WHERE id = ?";
    private static final String ELIMINAR = "DELETE FROM figura WHERE id = ?";

    private final ConexionBD conexionBD;

    public FiguraDAOMySQL(ConexionBD conexionBD) {
        this.conexionBD = Objects.requireNonNull(conexionBD);
    }

    @Override
    public Figura insertar(Figura figura) {
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            asignarParametros(sentencia, figura);
            sentencia.executeUpdate();
            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    figura.setId(claves.getInt(1));
                }
            }
            return figura;
        } catch (SQLException e) {
            throw new DAOException("Error al insertar la figura: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Figura> buscarPorId(int id) {
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SELECCIONAR_POR_ID)) {
            sentencia.setInt(1, id);
            try (ResultSet fila = sentencia.executeQuery()) {
                return fila.next() ? Optional.of(mapear(fila)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar la figura " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Figura> listarTodos() {
        List<Figura> figuras = new ArrayList<>();
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SELECCIONAR_TODAS);
             ResultSet filas = sentencia.executeQuery()) {
            while (filas.next()) {
                figuras.add(mapear(filas));
            }
            return figuras;
        } catch (SQLException e) {
            throw new DAOException("Error al listar las figuras: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizar(Figura figura) {
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(ACTUALIZAR)) {
            asignarParametros(sentencia, figura);
            sentencia.setInt(MAX_MEDIDAS + 2, figura.getId());
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar la figura: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(ELIMINAR)) {
            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar la figura " + id + ": " + e.getMessage(), e);
        }
    }

    /** Parámetros 1..4: tipo y las tres medidas (NULL si la figura tiene menos). */
    private static void asignarParametros(PreparedStatement sentencia, Figura figura) throws SQLException {
        double[] medidas = obtenerMedidas(figura);
        sentencia.setString(1, figura.getClass().getSimpleName().toUpperCase(Locale.ROOT));
        for (int i = 0; i < MAX_MEDIDAS; i++) {
            if (i < medidas.length) {
                sentencia.setDouble(i + 2, medidas[i]);
            } else {
                sentencia.setNull(i + 2, Types.DOUBLE);
            }
        }
    }

    private static double[] obtenerMedidas(Figura figura) {
        // Cuadrado debe ir antes que Rectangulo por ser su subclase.
        return switch (figura) {
            case Cuadrado c -> new double[]{c.getLado()};
            case Rectangulo r -> new double[]{r.getBase(), r.getAltura()};
            case Circulo c -> new double[]{c.getRadio()};
            case Rombo r -> new double[]{r.getDiagonalMayor(), r.getDiagonalMenor()};
            case Triangulo t -> new double[]{t.getLadoA(), t.getLadoB(), t.getLadoC()};
            default -> throw new DAOException("Tipo de figura no soportado: " + figura.getClass().getSimpleName());
        };
    }

    private static Figura mapear(ResultSet fila) throws SQLException {
        String tipo = fila.getString("tipo");
        double medida1 = fila.getDouble("medida1");
        double medida2 = fila.getDouble("medida2");
        double medida3 = fila.getDouble("medida3");

        Figura figura = switch (tipo) {
            case "CIRCULO" -> new Circulo(medida1);
            case "RECTANGULO" -> new Rectangulo(medida1, medida2);
            case "CUADRADO" -> new Cuadrado(medida1);
            case "ROMBO" -> new Rombo(medida1, medida2);
            // El tipo concreto de triángulo lo vuelve a decidir la fábrica según los lados.
            case "EQUILATERO", "ISOSCELES", "ESCALENO" -> Triangulo.crear(medida1, medida2, medida3);
            default -> throw new DAOException("Tipo de figura desconocido en la base de datos: " + tipo);
        };
        figura.setId(fila.getInt("id"));
        return figura;
    }

    @Override
    public String toString() {
        return "FiguraDAOMySQL [" + conexionBD + "]";
    }
}
