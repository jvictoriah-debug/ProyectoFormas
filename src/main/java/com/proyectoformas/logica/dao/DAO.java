package com.proyectoformas.logica.dao;

import java.util.List;
import java.util.Optional;

/**
 * Contrato genérico del patrón DAO (Data Access Object): operaciones CRUD
 * independientes del mecanismo de almacenamiento.
 *
 * @param <T> tipo de entidad persistida
 */
public interface DAO<T> {

    /** Guarda la entidad y le asigna el id generado. */
    T insertar(T entidad);

    Optional<T> buscarPorId(int id);

    List<T> listarTodos();

    boolean actualizar(T entidad);

    boolean eliminar(int id);
}
