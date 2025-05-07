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

    public ArrayList<Jugador> obtenerJugadores(Equipo equipo) throws SQLException {
        return jugadorDAO.obtenerPorEquipo(equipo);
    }
    public Jugador validarJugador(Jugador jugador) throws Exception {
        return jugadorDAO.obtenerJugador(jugador);
    }
    public boolean crearJugador(Jugador jugador) throws Exception {
        return jugadorDAO.crearJugador(jugador);
    }
    public boolean borrarJugador(Jugador jugador) throws Exception {
        return jugadorDAO.borrarJugador(jugador);
    }

}
