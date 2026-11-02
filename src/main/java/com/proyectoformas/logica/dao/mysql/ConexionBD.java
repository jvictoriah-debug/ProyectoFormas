package com.proyectoformas.logica.dao.mysql;

import com.proyectoformas.logica.dao.DAOException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

/**
 * Administra los datos de conexión a la base de datos MySQL.
 *
 * <p>La configuración se lee de {@code db.properties} del classpath y puede
 * sobrescribirse con un archivo {@code db.properties} ubicado en el
 * directorio de trabajo.
 */
public class ConexionBD {

    private static final String ARCHIVO_CONFIGURACION = "db.properties";

    private static final String CREAR_TABLA_FIGURA = """
            CREATE TABLE IF NOT EXISTS figura (
                id      INT AUTO_INCREMENT PRIMARY KEY,
                tipo    VARCHAR(20) NOT NULL,
                medida1 DOUBLE NOT NULL,
                medida2 DOUBLE NULL,
                medida3 DOUBLE NULL
            )""";

    private static final String CREAR_TABLA_CUERPO = """
            CREATE TABLE IF NOT EXISTS cuerpo (
                id      INT AUTO_INCREMENT PRIMARY KEY,
                tipo    VARCHAR(20) NOT NULL,
                medida1 DOUBLE NOT NULL,
                medida2 DOUBLE NULL
            )""";

    private String url;
    private String usuario;
    private String contrasena;

    public ConexionBD(String url, String usuario, String contrasena) {
        this.url = Objects.requireNonNull(url, "La URL no puede ser nula");
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    /** Crea la conexión a partir del archivo de configuración. */
    public static ConexionBD desdeConfiguracion() {
        Properties propiedades = new Properties();
        try (InputStream entrada = ConexionBD.class.getClassLoader().getResourceAsStream(ARCHIVO_CONFIGURACION)) {
            if (entrada != null) {
                propiedades.load(entrada);
            }
            Path externo = Path.of(ARCHIVO_CONFIGURACION);
            if (Files.isRegularFile(externo)) {
                try (Reader lector = Files.newBufferedReader(externo)) {
                    propiedades.load(lector);
                }
            }
        } catch (IOException e) {
            throw new DAOException("No se pudo leer " + ARCHIVO_CONFIGURACION, e);
        }
        String url = propiedades.getProperty("db.url");
        if (url == null || url.isBlank()) {
            throw new DAOException("Falta la propiedad db.url en " + ARCHIVO_CONFIGURACION);
        }
        return new ConexionBD(url, propiedades.getProperty("db.usuario", "root"),
                propiedades.getProperty("db.contrasena", ""));
    }

    /** Abre una nueva conexión; quien la pide es responsable de cerrarla. */
    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(url, usuario, contrasena);
    }

    /** Verifica la conexión y crea las tablas si aún no existen. */
    public void inicializarEsquema() {
        try (Connection conexion = obtenerConexion(); Statement sentencia = conexion.createStatement()) {
            sentencia.executeUpdate(CREAR_TABLA_FIGURA);
            sentencia.executeUpdate(CREAR_TABLA_CUERPO);
        } catch (SQLException e) {
            throw new DAOException("No se pudo conectar con MySQL: " + e.getMessage(), e);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = Objects.requireNonNull(url, "La URL no puede ser nula");
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConexionBD otra)) {
            return false;
        }
        return url.equals(otra.url) && Objects.equals(usuario, otra.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, usuario);
    }

    /** No incluye la contraseña para no exponerla en registros o consola. */
    @Override
    public String toString() {
        return "ConexionBD [url=" + url + ", usuario=" + usuario + "]";
    }
}
