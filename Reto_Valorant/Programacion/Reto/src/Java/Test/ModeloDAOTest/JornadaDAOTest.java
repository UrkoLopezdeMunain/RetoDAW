package ModeloDAOTest;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JornadaDAOTest {

    private static Connection con;
    private JornadaDAO jornadaDAO;

    @BeforeAll
    public static void setupDatabase() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        con = ds.getConnection();

        Statement st = con.createStatement();
        st.execute("CREATE TABLE competiciones (cod_comp INT PRIMARY KEY, estado VARCHAR(1), cod_juego INT)");
        st.execute("CREATE TABLE juegos (cod_juego INT PRIMARY KEY, nombre VARCHAR(50))");
        st.execute("CREATE TABLE jornadas (num_jornada INT, fecha_inicio DATE, cod_comp INT)");

        // Insertar datos de juego y competición
        st.execute("INSERT INTO juegos VALUES (1, 'Fútbol')");
        st.execute("INSERT INTO competiciones VALUES (10, 'A', 1)");
    }

    @BeforeEach
    public void setup() throws SQLException {
        JuegoDAO juegoDAO = new JuegoDAO(con);
        CompeticionDAO competicionDAO = new CompeticionDAO(con, juegoDAO);
        jornadaDAO = new JornadaDAO(con, competicionDAO);
    }

    @Test
    public void testAnadirYRecuperarJornada() throws SQLException {
        Competicion comp = new Competicion();
        comp.setCodCompeticion(10);
        comp.setEstado('A');
        comp.setJuego(new Juego()); // juego innecesario para inserción, solo para evitar null

        Jornada jornada = new Jornada();
        jornada.setNumJornada(1);
        jornada.setFechaInicio(LocalDate.of(2025, 5, 6));
        jornada.setCompeticion(comp);

        jornadaDAO.anadirJornada(jornada);

        List<Jornada> jornadas = jornadaDAO.getJornadas();
        assertEquals(1, jornadas.size());
        Jornada j = jornadas.get(0);
        assertEquals(1, j.getNumJornada());
        assertEquals(LocalDate.of(2025, 5, 6), j.getFechaInicio());
        assertEquals(10, j.getCompeticion().getCodCompeticion());
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        con.close();
    }
}
