package com.proyectoformas.logica.dao;

/**
 * Error producido en la capa de acceso a datos. Envuelve las excepciones
 * propias de cada tecnología (por ejemplo, SQLException) para no acoplar
 * las capas superiores a ellas.
 */
public class DAOException extends RuntimeException {

    public DAOException(String mensaje) {
        super(mensaje);
    }

    public DAOException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
