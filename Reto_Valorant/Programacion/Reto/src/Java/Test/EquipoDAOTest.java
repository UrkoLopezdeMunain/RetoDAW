package Java.Test;

import BaseDatos.BaseDatos;
import Modelo.Equipo;
import ModeloDAO.EquipoDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EquipoDAOTest {

    private static Connection c;
    private static EquipoDAO equipoDAO;
    private static Equipo equipo;
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        c = BaseDatos.abrirCon();
        equipoDAO = new EquipoDAO(c);
        equipo = new Equipo(2,"manoloMovies",LocalDate.parse("15/07/2021"),8);
    }
    @AfterAll
    public static void tearDownAfterClass() throws SQLException {
        c.close();
        equipoDAO = null;
    }

    @Test
    void validarEquipo() throws SQLException {
        assertNotNull(equipoDAO.validarEquipo(equipo));
    }

    @Test
    void getEquipos() {
        assertAll(
                () -> assertNotNull(equipoDAO.getEquipos()),
                () -> assertDoesNotThrow(equipoDAO::getEquipos)
        );
    }

    @Test
    void getEquipoPorId() throws SQLException {
        assertNotNull(equipoDAO.getEquipoPorId(equipo.getCodEquipo()));
    }

    @Test
    void getEquiposProcedimiento() throws SQLException {
        assertAll(
                () -> assertNotNull(equipoDAO.getEquiposProcedimiento()),
                () -> assertDoesNotThrow(equipoDAO::getEquiposProcedimiento)
        );
    }
}