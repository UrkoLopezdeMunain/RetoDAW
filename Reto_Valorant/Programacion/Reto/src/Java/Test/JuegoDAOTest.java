package Java.Test;

import BaseDatos.BaseDatos;
import ModeloDAO.JuegoDAO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JuegoDAOTest {
    private static Connection con;
    private static JuegoDAO juegoDAO;

    @BeforeAll
    static void setUp() throws SQLException {
        con = BaseDatos.abrirCon();
        juegoDAO = new JuegoDAO(con);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        con.close();
        juegoDAO = null;
    }

    @Test
    void conseguirJuego() {
        assertAll(
                () -> assertNotNull(juegoDAO.conseguirJuego()),
                () -> assertDoesNotThrow(juegoDAO::conseguirJuego)
        );
    }
}