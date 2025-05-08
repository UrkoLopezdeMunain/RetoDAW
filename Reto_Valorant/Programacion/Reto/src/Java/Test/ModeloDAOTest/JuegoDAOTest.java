package ModeloDAOTest;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JuegoDAOTest {

    private static Connection con;
    private JuegoDAO juegoDAO;

    @BeforeAll
    public static void setupDatabase() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        con = ds.getConnection();

        Statement st = con.createStatement();
        st.execute("CREATE TABLE juegos (cod_juego INT PRIMARY KEY, nombre VARCHAR(50), fecha_salida DATE)");

        // Insertar un juego para probar
        st.execute("INSERT INTO juegos VALUES (1, 'Fútbol', '2022-01-01')");
    }

    @BeforeEach
    public void setup() {
        juegoDAO = new JuegoDAO(con);
    }

    @Test
    public void testConseguirJuego() throws SQLException {
        // Prueba que conseguimos el juego con el código 1
        Juego juego = juegoDAO.conseguirJuego(1);

        assertNotNull(juego);
        assertEquals(1, juego.getCodJuego());
        assertEquals("Fútbol", juego.getNombre());
        assertEquals(LocalDate.of(2022, 1, 1), juego.getFechaSalida());
    }

    @Test
    public void testConseguirJuegoInexistente() throws SQLException {
        // Prueba que conseguimos null si es que el juego no existe
        Juego juego = juegoDAO.conseguirJuego(999);

        assertNull(juego); // No debe existir el juego con este código. Null
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        con.close();
    }
}