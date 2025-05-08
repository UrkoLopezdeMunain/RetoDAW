package ModeloDAOTest;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JugadorDAOTest {

    private static Connection con;
    private JugadorDAO jugadorDAO;
    private EquipoDAO equipoDAO;

    @BeforeAll
    public static void setupDatabase() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        con = ds.getConnection();

        Statement st = con.createStatement();
        st.execute("CREATE TABLE equipos (cod_equipo INT PRIMARY KEY, nombre VARCHAR(50))");
        st.execute("CREATE TABLE jugadores (cod_jugador INT PRIMARY KEY, nombre VARCHAR(50), apellido VARCHAR(50), nacionalidad VARCHAR(50), fecha_nac DATE, sueldo DOUBLE, nickname VARCHAR(50), rol VARCHAR(50), cod_equipo INT, FOREIGN KEY (cod_equipo) REFERENCES equipos(cod_equipo))");

        // Insertar equipo de pruebas
        st.execute("INSERT INTO equipos VALUES (1, 'Equipo A')");
    }

    @BeforeEach
    public void setup() {
        equipoDAO = new EquipoDAO(con);
        jugadorDAO = new JugadorDAO(con);
    }

    @Test
    public void testCrearYObtenerJugador() throws SQLException {
        // Crear jugador
        Equipo equipo = equipoDAO.getEquipos().get(0);  // Usamos el equipo insertado anteriormente
        Jugador jugador = new Jugador();
        jugador.setNombre("Juan");
        jugador.setApellido("Pérez");
        jugador.setNacionalidad("España");
        jugador.setFechaNacimiento(LocalDate.of(1990, 5, 10));
        jugador.setSueldo(50000.0);
        jugador.setNickname("juanperez");
        jugador.setRol("Delantero");
        jugador.setEquipo(equipo);

        // Insertar
        assertTrue(jugadorDAO.crearJugador(jugador));

        // Obtener por nickname
        Jugador jugadorObtenido = jugadorDAO.obtenerJugador(jugador);
        assertNotNull(jugadorObtenido);
        assertEquals("Juan", jugadorObtenido.getNombre());
        assertEquals("Pérez", jugadorObtenido.getApellido());
        assertEquals("juanperez", jugadorObtenido.getNickname());
    }

    @Test
    public void testObtenerJugadoresPorEquipo() throws SQLException {
        // Crear jugador
        Equipo equipo = equipoDAO.getEquipos().get(0);  // Usamos el equipo insertado anteriormente
        Jugador jugador1 = new Jugador();
        jugador1.setNombre("Juan");
        jugador1.setApellido("Pérez");
        jugador1.setNacionalidad("España");
        jugador1.setFechaNacimiento(LocalDate.of(1990, 5, 10));
        jugador1.setSueldo(50000.0);
        jugador1.setNickname("juanperez");
        jugador1.setRol("Delantero");
        jugador1.setEquipo(equipo);
        jugadorDAO.crearJugador(jugador1);

        Jugador jugador2 = new Jugador();
        jugador2.setNombre("Carlos");
        jugador2.setApellido("Gómez");
        jugador2.setNacionalidad("España");
        jugador2.setFechaNacimiento(LocalDate.of(1992, 7, 20));
        jugador2.setSueldo(40000.0);
        jugador2.setNickname("carlosgomez");
        jugador2.setRol("Defensa");
        jugador2.setEquipo(equipo);
        jugadorDAO.crearJugador(jugador2);

        // Obtener jugador por equipo
        ArrayList<Jugador> jugadoresPorEquipo = jugadorDAO.obtenerPorEquipo(equipo);
        assertNotNull(jugadoresPorEquipo);
        assertEquals(2, jugadoresPorEquipo.size());
    }

    @Test
    public void testBorrarJugador() throws SQLException {
        // Crear jugador
        Equipo equipo = equipoDAO.getEquipos().get(0);  // Usamos el equipo insertado anteriormente
        Jugador jugador = new Jugador();
        jugador.setNombre("Juan");
        jugador.setApellido("Pérez");
        jugador.setNacionalidad("España");
        jugador.setFechaNacimiento(LocalDate.of(1990, 5, 10));
        jugador.setSueldo(50000.0);
        jugador.setNickname("juanperez");
        jugador.setRol("Delantero");
        jugador.setEquipo(equipo);

        // Insertar jugador
        jugadorDAO.crearJugador(jugador);

        // Borrar jugador
        assertTrue(jugadorDAO.borrarJugador(jugador));

        // Verificar que el jugador ya no está
        Jugador jugadorBorrado = jugadorDAO.obtenerJugador(jugador);
        assertNull(jugadorBorrado);
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        con.close();
    }
}
