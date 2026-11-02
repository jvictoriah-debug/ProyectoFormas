package com.proyectoformas.logica.dao.mysql;

import com.proyectoformas.logica.dao.CuerpoDAO;
import com.proyectoformas.logica.dao.DAOException;
import com.proyectoformas.logica.modelo.Cilindro;
import com.proyectoformas.logica.modelo.Cubo;
import com.proyectoformas.logica.modelo.Cuerpo;
import com.proyectoformas.logica.modelo.Esfera;
import com.proyectoformas.logica.modelo.Tetraedro;

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
 * Implementación MySQL del DAO de cuerpos. Se guardan solo las medidas que
 * definen las figuras componentes; al leer la fila, el constructor del
 * cuerpo vuelve a crear dichas figuras.
 */
public class CuerpoDAOMySQL implements CuerpoDAO {

    private static final int MAX_MEDIDAS = 2;

    private static final String INSERTAR =
            "INSERT INTO cuerpo (tipo, medida1, medida2) VALUES (?, ?, ?)";
    private static final String SELECCIONAR_TODOS =
            "SELECT id, tipo, medida1, medida2 FROM cuerpo ORDER BY id";
    private static final String SELECCIONAR_POR_ID =
            "SELECT id, tipo, medida1, medida2 FROM cuerpo WHERE id = ?";
    private static final String ACTUALIZAR =
            "UPDATE cuerpo SET tipo = ?, medida1 = ?, medida2 = ? WHERE id = ?";
    private static final String ELIMINAR = "DELETE FROM cuerpo WHERE id = ?";

    private final ConexionBD conexionBD;

    public CuerpoDAOMySQL(ConexionBD conexionBD) {
        this.conexionBD = Objects.requireNonNull(conexionBD);
    }

    @Override
    public Cuerpo insertar(Cuerpo cuerpo) {
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            asignarParametros(sentencia, cuerpo);
            sentencia.executeUpdate();
            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    cuerpo.setId(claves.getInt(1));
                }
            }
            return cuerpo;
        } catch (SQLException e) {
            throw new DAOException("Error al insertar el cuerpo: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Cuerpo> buscarPorId(int id) {
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SELECCIONAR_POR_ID)) {
            sentencia.setInt(1, id);
            try (ResultSet fila = sentencia.executeQuery()) {
                return fila.next() ? Optional.of(mapear(fila)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar el cuerpo " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cuerpo> listarTodos() {
        List<Cuerpo> cuerpos = new ArrayList<>();
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SELECCIONAR_TODOS);
             ResultSet filas = sentencia.executeQuery()) {
            while (filas.next()) {
                cuerpos.add(mapear(filas));
            }
            return cuerpos;
        } catch (SQLException e) {
            throw new DAOException("Error al listar los cuerpos: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizar(Cuerpo cuerpo) {
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(ACTUALIZAR)) {
            asignarParametros(sentencia, cuerpo);
            sentencia.setInt(MAX_MEDIDAS + 2, cuerpo.getId());
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar el cuerpo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(ELIMINAR)) {
            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar el cuerpo " + id + ": " + e.getMessage(), e);
        }
    }

    private static void asignarParametros(PreparedStatement sentencia, Cuerpo cuerpo) throws SQLException {
        double[] medidas = obtenerMedidas(cuerpo);
        sentencia.setString(1, cuerpo.getClass().getSimpleName().toUpperCase(Locale.ROOT));
        for (int i = 0; i < MAX_MEDIDAS; i++) {
            if (i < medidas.length) {
                sentencia.setDouble(i + 2, medidas[i]);
            } else {
                sentencia.setNull(i + 2, Types.DOUBLE);
            }
        }
    }

    private static double[] obtenerMedidas(Cuerpo cuerpo) {
        return switch (cuerpo) {
            case Cubo c -> new double[]{c.getArista()};
            case Cilindro c -> new double[]{c.getRadio(), c.getAltura()};
            case Tetraedro t -> new double[]{t.getArista()};
            case Esfera e -> new double[]{e.getRadio()};
            default -> throw new DAOException("Tipo de cuerpo no soportado: " + cuerpo.getClass().getSimpleName());
        };
    }

    private static Cuerpo mapear(ResultSet fila) throws SQLException {
        String tipo = fila.getString("tipo");
        double medida1 = fila.getDouble("medida1");
        double medida2 = fila.getDouble("medida2");

        Cuerpo cuerpo = switch (tipo) {
            case "CUBO" -> new Cubo(medida1);
            case "CILINDRO" -> new Cilindro(medida1, medida2);
            case "TETRAEDRO" -> new Tetraedro(medida1);
            case "ESFERA" -> new Esfera(medida1);
            default -> throw new DAOException("Tipo de cuerpo desconocido en la base de datos: " + tipo);
        };
        cuerpo.setId(fila.getInt("id"));
        return cuerpo;
    }

    @Override
    public String toString() {
        return "CuerpoDAOMySQL [" + conexionBD + "]";
    }
}
