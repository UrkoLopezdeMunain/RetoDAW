package Java.Test;

import BaseDatos.BaseDatos;
import Modelo.Equipo;
import Modelo.Jugador;
import ModeloDAO.JugadorDAO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JugadorDAOTest {

    private static Connection c;
    private static JugadorDAO jugadorDAO;
    private static Jugador jugador;
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        c = BaseDatos.abrirCon();
        jugadorDAO = new JugadorDAO(c);
    }

    @AfterAll
    public static void tearDownAfterClass() throws SQLException {
        c.close();
        jugadorDAO = null;
    }
    @BeforeEach
    public void setUp() throws Exception {
        jugador = new Jugador("dani");
    }

    @AfterEach
    public void tearDown() throws Exception {
        jugador = null;
    }


    @Test
    void obtenerPorEquipo() throws SQLException {
        assertNotNull(jugadorDAO.obtenerPorEquipo(new Equipo("gorillaz")));
    }

    @Test
    void crearJugador() throws SQLException {
        assertTrue(jugadorDAO.crearJugador(jugador));
    }

    @Test
    void borrarJugador() throws SQLException {
        assertTrue(jugadorDAO.borrarJugador(jugador));
    }

    @Test
    void obtenerJugador() throws SQLException {
        assertNotNull(jugadorDAO.obtenerJugador(jugador));
    }

    @Test
    void actualizarJugador() throws SQLException {
        assertTrue(jugadorDAO.actualizarJugador(jugador));
    }
}