package Java.Test;

import BaseDatos.BaseDatos;
import Modelo.Equipo;
import ModeloDAO.JugadorDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JugadorDAOTest {

    private static Connection c;
    private static JugadorDAO jugadorDAO;
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

    @Test
    void obtenerPorEquipo() throws SQLException {
        assertNotNull(jugadorDAO.obtenerPorEquipo(new Equipo("gorillaz")));
    }

    @Test
    void crearJugador() {

    }

    @Test
    void borrarJugador() {
    }

    @Test
    void obtenerJugador() {
    }

    @Test
    void actualizarJugador() {
    }
}