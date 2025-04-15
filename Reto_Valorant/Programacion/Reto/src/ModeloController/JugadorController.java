package ModeloController;

import Modelo.Jugador;
import ModeloDAO.JugadorDAO;

import java.sql.SQLException;
import java.util.*;


public class JugadorController {

    private final JugadorDAO jugadorDAO;

    public JugadorController(JugadorDAO jugadorDAO) {
        this.jugadorDAO = jugadorDAO;
    }

    public ArrayList<Jugador> obtenerJugadores(int codEquipo) throws SQLException {
        return jugadorDAO.obtenerPorEquipo(codEquipo);
    }

    public boolean crearJugador(String nombre, String apellido, String nacionalidad, String fechaNac, String sueldo, String rol, String nickName, int codEquipo) throws SQLException {
        return jugadorDAO.crearJugador(nombre,apellido,nacionalidad,fechaNac,sueldo,rol,nickName,codEquipo);
    }
    public Jugador obtnerJugador(String nickName) throws SQLException {
        return jugadorDAO.obtenerJugador(nickName);
    }
    public boolean borrarJugador(String nickName) throws SQLException {
        return jugadorDAO.borrarJugador(nickName);
    }
}
