package Java.Test;

import BaseDatos.BaseDatos;
import ModeloDAO.CompeticionDAO;
import ModeloDAO.JornadaDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JornadaDAOTest {
    private static Connection c;
    private static JornadaDAO jornadaDAO;
    private static CompeticionDAO competicionDAO;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        c = BaseDatos.abrirCon();
        competicionDAO = new CompeticionDAO(c);
        jornadaDAO = new JornadaDAO(c,competicionDAO);
    }
    @AfterAll
    public static void tearDownAfterClass() throws SQLException {
        c.close();
        competicionDAO = null;
        jornadaDAO = null;
    }

    @Test
    void getJornadas() {
        assertAll(
                () -> assertNotNull(jornadaDAO.getJornadas()),
                () -> assertDoesNotThrow(jornadaDAO::getJornadas)
        );
    }

    @Test
    void getJornadaPorId() {
    }
}