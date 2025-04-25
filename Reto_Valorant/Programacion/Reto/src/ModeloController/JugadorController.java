package ModeloController;

import Modelo.Equipo;
import Modelo.Jugador;
import ModeloDAO.JugadorDAO;

import java.sql.SQLException;
import java.util.*;


public class JugadorController {

    private final JugadorDAO jugadorDAO;

    public JugadorController(JugadorDAO jugadorDAO) {
        this.jugadorDAO = jugadorDAO;
    }

    /** Metodos para optimizar accesos a BD*/
    public ArrayList<Jugador> obtenerJugadores(Equipo equipo) throws SQLException {
        return jugadorDAO.obtenerPorEquipo(equipo);
    }


    /**CRUD de JUGADOR(JugadorController)*/
    public boolean crearJugador(Jugador jugador) throws SQLException {
        return jugadorDAO.crearJugador(jugador);
    }

    public Jugador obtnerJugador(Jugador jugador) throws SQLException {
        return jugadorDAO.obtenerJugador(jugador);
    }

    public boolean borrarJugador(Jugador jugador) throws SQLException {
        return jugadorDAO.borrarJugador(jugador);
    }
}
