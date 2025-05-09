package ModeloController;

import Modelo.Equipo;
import Modelo.Jugador;
import ModeloDAO.JugadorDAO;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Equipo tres
 * @version (2.0)
 */
public class JugadorController {

    private final JugadorDAO jugadorDAO;

    /**
     * Constructor
     * @param jugadorDAO
     */
    public JugadorController(JugadorDAO jugadorDAO) {
        this.jugadorDAO = jugadorDAO;
    }

    /** Metodos para optimizar accesos a BD*/
    /**
     * Obtiene los jugadores que pertenecen a un equipo
     * @param equipo equipo del cual se quieren obtener los jugadores
     * @return jugadores
     * @throws SQLException si ocurre un error
     */
    public ArrayList<Jugador> obtenerJugadores(Equipo equipo) throws SQLException {
        return jugadorDAO.obtenerPorEquipo(equipo);
    }

    /**
     * Actualiza la información de un jugador
     * @param jugador jugador a actualizar
     * @return true si la operación fue echa corectamente, false en caso cnontrario
     * @throws SQLException si ocurre un error
     */
    public boolean actualizarJugador(Jugador jugador) throws SQLException {
        return jugadorDAO.actualizarJugador(jugador);
    }

    /**
     * Obtiene un jugador teniendo en cuenta su nickname.
     * @param nickname nickname del jugador a buscar
     * @return Jugador
     * @throws SQLException si ocurre un error
     */
    public Jugador obtenerJugadorPorNickname(String nickname) throws SQLException {
        Jugador j = new Jugador();
        j.setNickname(nickname);
        return jugadorDAO.obtenerJugador(j);
    }



    /**CRUD de JUGADOR(JugadorController)*/
    /**
     * Crea un nuevo jugador
     * @param jugador jugador a crear
     * @return true si la operación echa correctamente, false en caso de que no
     * @throws SQLException si ocurre un error
     */
    public boolean crearJugador(Jugador jugador) throws SQLException {
        return jugadorDAO.crearJugador(jugador);
    }

    /**
     * Obtiene un jugador
     * @param jugador
     * @return Jugador
     * @throws SQLException si ocurre un error
     */
    public Jugador obtnerJugador(Jugador jugador) throws SQLException {
        return jugadorDAO.obtenerJugador(jugador);
    }

    /**
     * Elimina un jugador
     * @param jugador jugador a eliminar
     * @return true si la operación es correcta, false en caso de que no lo sea
     * @throws SQLException si ocurre un error en la eliminacion
     */
    public boolean borrarJugador(Jugador jugador) throws SQLException {
        return jugadorDAO.borrarJugador(jugador);
    }

    /**
     * Lleva el nombre del equipo a la bd para que le devuelva los datos de los
     * jugadores
     * @param equipo Nombre del equipo a buscar
     * @return Lista de informacion de jugadores
     * @throws Exception si ocurre un error en la busqueda
     */
    public List<String> jugadores(String equipo) throws Exception {
        return jugadorDAO.jugadores(equipo);
    }
}
