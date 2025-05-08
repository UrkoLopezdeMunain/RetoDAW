import Modelo.Equipo;
import Modelo.Jugador;
import ModeloDAO.JugadorDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

import java.util.ArrayList;

class JugadorControllerTest {

    private JugadorDAO jugadorDAO;
    private JugadorController jugadorController;

    @BeforeEach
    void setUp() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

        // Creaamos tablas en la base de datos de pruebas
        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE Equipo (id INT PRIMARY KEY, nombre VARCHAR(255));");
        stmt.execute("CREATE TABLE Jugador (id INT PRIMARY KEY, nombre VARCHAR(255), equipo_id INT, FOREIGN KEY(equipo_id) REFERENCES Equipo(id));");


        jugadorDAO = new JugadorDAO(connection);
        jugadorController = new JugadorController(jugadorDAO);

        // Insertar un equipo
        stmt.execute("INSERT INTO Equipo (id, nombre) VALUES (1, 'Equipo A');");
    }

    @Test
    void testCrearJugador() throws SQLException {
        // Datos de prueba
        Jugador jugador = new Jugador();
        jugador.setNombre("Juan");
        jugador.setEquipo(new Equipo(1, "Equipo A"));

        // Crear jugador
        boolean result = jugadorController.crearJugador(jugador);

        // Verificar que el jugador fue creado correctamente
        assertTrue(result);

        // Verificar que el jugador existe en la base de datos
        Statement stmt = jugadorDAO.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Jugador WHERE nombre = 'Juan'");
        assertTrue(rs.next());
        assertEquals("Juan", rs.getString("nombre"));
    }

    @Test
    void testObtenerJugadores() throws SQLException {
        // Crear jugadores en la base de datos
        Statement stmt = jugadorDAO.getConnection().createStatement();
        stmt.execute("INSERT INTO Jugador (id, nombre, equipo_id) VALUES (1, 'Juan', 1);");
        stmt.execute("INSERT INTO Jugador (id, nombre, equipo_id) VALUES (2, 'Pedro', 1);");

        // para obtener jugadores
        Equipo equipo = new Equipo(1, "Equipo A");
        ArrayList<Jugador> jugadores = jugadorController.obtenerJugadores(equipo);

        // Verificar que los jugadores sean obtenidos correctamente
        assertEquals(2, jugadores.size());
        assertEquals("Juan", jugadores.get(0).getNombre());
        assertEquals("Pedro", jugadores.get(1).getNombre());
    }

    @Test
    void testObtenerJugador() throws SQLException {
        // Crear un jugador en la base de datos
        Statement stmt = jugadorDAO.getConnection().createStatement();
        stmt.execute("INSERT INTO Jugador (id, nombre, equipo_id) VALUES (1, 'Juan', 1);");

        // Buscar el jugador
        Jugador jugador = new Jugador();
        jugador.setId(1);
        Jugador result = jugadorController.obtnerJugador(jugador);

        // Verificar que el jugador es encontrado correctamente
        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    void testBorrarJugador() throws SQLException {
        // Crear un jugador en la base de datos
        Statement stmt = jugadorDAO.getConnection().createStatement();
        stmt.execute("INSERT INTO Jugador (id, nombre, equipo_id) VALUES (1, 'Juan', 1);");

        // Crear un objeto jugador para borrar
        Jugador jugador = new Jugador();
        jugador.setId(1);

        // Borrar el jugador
        boolean result = jugadorController.borrarJugador(jugador);

        // Verificar que el jugador es borrado correctamente
        assertTrue(result);

        // Verificar que el jugador sea eliminado de la base de datos
        ResultSet rs = stmt.executeQuery("SELECT * FROM Jugador WHERE id = 1");
        assertFalse(rs.next());
    }
}
