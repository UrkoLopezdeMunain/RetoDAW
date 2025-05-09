package Java.Test;

import BaseDatos.BaseDatos;
import Modelo.Enfrentamiento;

import Modelo.Equipo;
import ModeloDAO.EnfrentamientoDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;

class EnfrentamientoDAOTest {
    private static Connection c;
    private static EnfrentamientoDAO enfrentamientoDAO;
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        c = BaseDatos.abrirCon();
        enfrentamientoDAO = new EnfrentamientoDAO(c);
    }
    @AfterAll
    public static void tearDownAfterClass() throws SQLException {
        c.close();
        enfrentamientoDAO = null;
    }

    @Test
    public void anadirEnfrentamientos() throws SQLException {
        assertTrue(enfrentamientoDAO.anadirEnfrentamientos(new Enfrentamiento()));
    }

    @Test
    public void getEnfrentamientos() {
        assertAll(
            () -> assertNotNull(enfrentamientoDAO.getEnfrentamientos()),
            () -> assertDoesNotThrow(enfrentamientoDAO::getEnfrentamientos)
        );

    }

    @Test
    public void getEnfrentamientoPorId() throws Exception {
        assertNotNull(enfrentamientoDAO.getEnfrentamientoPorId( 1 ));
    }

    @Test
    public void getEnfrentamientoPorEquipos() throws SQLException {
        assertNotNull(enfrentamientoDAO.getEnfrentamientoPorEquipos(new Equipo(), new Equipo()));

    }

    @Test
    public void getEnfrentamientoPorJornada() throws Exception {
        assertNotNull(enfrentamientoDAO.getEnfrentamientoPorJornada(1));
    }
}