package com.proyectoformas.logica.dao.memoria;

import com.proyectoformas.logica.dao.DAO;
import com.proyectoformas.logica.modelo.Identificable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementación en memoria del DAO. Se usa cuando MySQL no está disponible
 * y en las pruebas unitarias; demuestra que el resto del sistema no depende
 * del mecanismo de almacenamiento.
 */
public abstract class DAOMemoria<T extends Identificable> implements DAO<T> {

    private final Map<Integer, T> almacen = new LinkedHashMap<>();
    private int siguienteId = 1;

    @Override
    public T insertar(T entidad) {
        Objects.requireNonNull(entidad);
        entidad.setId(siguienteId++);
        almacen.put(entidad.getId(), entidad);
        return entidad;
    }

    @Override
    public Optional<T> buscarPorId(int id) {
        return Optional.ofNullable(almacen.get(id));
    }

    @Override
    public List<T> listarTodos() {
        return new ArrayList<>(almacen.values());
    }

    @Override
    public boolean actualizar(T entidad) {
        if (!almacen.containsKey(entidad.getId())) {
            return false;
        }
        almacen.put(entidad.getId(), entidad);
        return true;
    }

    @Override
    public boolean eliminar(int id) {
        return almacen.remove(id) != null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [registros=" + almacen.size() + "]";
    }
}
