package com.proyectoformas;

import com.proyectoformas.logica.controlador.ColeccionControlador;
import com.proyectoformas.logica.dao.CuerpoDAO;
import com.proyectoformas.logica.dao.DAOException;
import com.proyectoformas.logica.dao.FiguraDAO;
import com.proyectoformas.logica.dao.memoria.CuerpoDAOMemoria;
import com.proyectoformas.logica.dao.memoria.FiguraDAOMemoria;
import com.proyectoformas.logica.dao.mysql.ConexionBD;
import com.proyectoformas.logica.dao.mysql.CuerpoDAOMySQL;
import com.proyectoformas.logica.dao.mysql.FiguraDAOMySQL;
import com.proyectoformas.logica.modelo.Coleccion;
import com.proyectoformas.ui.ConsolaVista;
import com.proyectoformas.ui.Menu;

/**
 * Punto de entrada: arma el modelo, los DAO, el controlador y la vista.
 */
public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        ConsolaVista vista = new ConsolaVista();
        FiguraDAO figuraDAO;
        CuerpoDAO cuerpoDAO;

        try {
            ConexionBD conexion = ConexionBD.desdeConfiguracion();
            conexion.inicializarEsquema();
            figuraDAO = new FiguraDAOMySQL(conexion);
            cuerpoDAO = new CuerpoDAOMySQL(conexion);
            vista.mostrarMensaje("Conectado a MySQL: " + conexion.getUrl());
        } catch (DAOException e) {
            vista.mostrarError(e.getMessage());
            vista.mostrarMensaje("Se usará almacenamiento en memoria; los datos no se guardarán al salir.");
            figuraDAO = new FiguraDAOMemoria();
            cuerpoDAO = new CuerpoDAOMemoria();
        }

        ColeccionControlador controlador = new ColeccionControlador(new Coleccion(), figuraDAO, cuerpoDAO);
        try {
            controlador.cargarDatos();
        } catch (DAOException e) {
            vista.mostrarError("No se pudieron cargar los datos guardados: " + e.getMessage());
        }

        Menu.crearMenuPrincipal(controlador, vista).mostrar();
    }
}
