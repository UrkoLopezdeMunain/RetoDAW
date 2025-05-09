package Java.Test;

import BaseDatos.BaseDatos;
import Modelo.Competicion;
import ModeloDAO.CompeticionDAO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CompeticionDAOTest {
    private static CompeticionDAO competicionDAO;
    private static Connection c;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        c = BaseDatos.abrirCon();
        competicionDAO = new CompeticionDAO(c);
    }
    @AfterAll
    static void tearDownAfterClass() throws Exception {
        c.close();
        competicionDAO = null;
    }

    @Test
    @DisplayName("Empezar competicion")
    void empezarCompeticion() throws SQLException {
        assertTrue(competicionDAO.empezarCompeticion());
    }
    @Test
    @DisplayName("Excepciones empezar competicion")
    void empezarCompeticionEXPC() {
        assertDoesNotThrow(() -> competicionDAO.empezarCompeticion());
    }

    @Test
    void conseguirCompeticion() throws SQLException {
        assertNotNull(competicionDAO.conseguirCompeticion());
    }

    @Test
    @DisplayName("Excepciones conseguir competicion")
    void conseguirCompeticionEXPC() {
        assertDoesNotThrow(() -> competicionDAO.conseguirCompeticion());
    }

}