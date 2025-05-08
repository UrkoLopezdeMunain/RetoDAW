package ModeloDAOTest;

import ModeloTest.Competicion;
import ModeloTest.Juego;
import ModeloDAOTest.CompeticionDAO;
import ModeloDAOTest.JuegoDAO;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class CompeticionDAOTest {

    private static Connection con;
    private CompeticionDAO competicionDAO;

    @BeforeAll
    public static void init() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        con = ds.getConnection();

        Statement st = con.createStatement();
        // Crear tablas
        st.execute("CREATE TABLE juegos (cod_juego INT PRIMARY KEY, nombre VARCHAR(50))");
        st.execute("CREATE TABLE competiciones (cod_comp INT PRIMARY KEY, estado VARCHAR(10), cod_juego INT)");

        // Insertar datos
        st.execute("INSERT INTO juegos VALUES (100, 'Fútbol')");
        st.execute("INSERT INTO competiciones VALUES (1, 'A', 100)");
    }

    @BeforeEach
    public void setup() {
        JuegoDAO juegoDAO = new JuegoDAO(con);
        competicionDAO = new CompeticionDAO(con, juegoDAO);
    }

    @Test
    public void testConseguirCompeticionExistente() throws SQLException {
        Competicion comp = competicionDAO.conseguirCompeticion(1);
        assertNotNull(comp);
        assertEquals(1, comp.getCodCompeticion());
        assertEquals('A', comp.getEstado());
        assertNotNull(comp.getJuego());
        assertEquals("Fútbol", comp.getJuego().getNombre());
    }

    @Test
    public void testConseguirCompeticionInexistente() throws SQLException {
        Competicion comp = competicionDAO.conseguirCompeticion(999);
        assertNull(comp);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        con.close();
    }
}