package ModeloDAOTest;

import ModeloDAOTest.EquipoDAO;
import ModeloTest.Equipo;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EquipoDAOTest {
    private static Connection con;
    private EquipoDAO equipoDAO;

    @BeforeAll
    public static void setupDatabase() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        con = ds.getConnection();

        Statement st = con.createStatement();
        st.execute("CREATE TABLE equipos (" +
                "cod_equipo INT AUTO_INCREMENT PRIMARY KEY," +
                "nombre VARCHAR(100) UNIQUE," +
                "fecha_fundacion DATE," +
                "puntuacion INT DEFAULT 0)");
    }

    @BeforeEach
    public void setup() {
        equipoDAO = new EquipoDAO(con);
    }

    @Test
    public void testCrearYValidarEquipo() throws SQLException {
        Equipo e = new Equipo();
        e.setNombre("Real Madrid");
        e.setFechaFundacion(LocalDate.of(1902, 3, 6));

        boolean creado = equipoDAO.crearEquipo(e);
        assertTrue(creado);

        Equipo validado = equipoDAO.validarEquipo(e);
        assertNotNull(validado);
        assertEquals("Real Madrid", validado.getNombre());
        assertEquals(LocalDate.of(1902, 3, 6), validado.getFechaFundacion());
        assertEquals(0, validado.getPuntuacion());
    }

    @Test
    public void testActualizarFecha() throws SQLException {
        Equipo e = new Equipo();
        e.setNombre("Barcelona");
        e.setFechaFundacion(LocalDate.of(1899, 11, 29));
        equipoDAO.crearEquipo(e);

        e.setFechaFundacion(LocalDate.of(1900, 1, 1));
        boolean actualizado = equipoDAO.actualizarFechaEquipo(e);
        assertTrue(actualizado);

        Equipo actualizadoDesdeBD = equipoDAO.validarEquipo(e);
        assertEquals(LocalDate.of(1900, 1, 1), actualizadoDesdeBD.getFechaFundacion());
    }

    @Test
    public void testBorrarEquipo() throws SQLException {
        Equipo e = new Equipo();
        e.setNombre("Valencia");
        e.setFechaFundacion(LocalDate.of(1919, 3, 18));
        equipoDAO.crearEquipo(e);

        boolean borrado = equipoDAO.borrarEquipo(e);
        assertTrue(borrado);

        Equipo resultado = equipoDAO.validarEquipo(e);
        assertNull(resultado);
    }

    @Test
    public void testGetEquipos() throws SQLException {
        Equipo e1 = new Equipo(); e1.setNombre("Team A"); e1.setFechaFundacion(LocalDate.of(2000,1,1));
        Equipo e2 = new Equipo(); e2.setNombre("Team B"); e2.setFechaFundacion(LocalDate.of(2001,1,1));
        equipoDAO.crearEquipo(e1);
        equipoDAO.crearEquipo(e2);

        List<Equipo> equipos = equipoDAO.getEquipos();
        assertEquals(2, equipos.size());
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        con.close();
    }
}