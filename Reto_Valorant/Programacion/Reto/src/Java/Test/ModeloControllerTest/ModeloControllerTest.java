package ModeloControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ModeloControllerTest {

    private ModeloController modeloController;
    private UsuarioController usuarioController;
    private EquipoController equipoController;
    private JugadorController jugadorController;

    private Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        // Configuración de la base de datos
        connection = BaseDatos.abrirCon();

        // Crear los DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
        EquipoDAO equipoDAO = new EquipoDAO(connection);
        JugadorDAO jugadorDAO = new JugadorDAO(connection);

        // Inicializar los controladores con los DAO
        usuarioController = new UsuarioController(usuarioDAO);
        equipoController = new EquipoController(equipoDAO);
        jugadorController = new JugadorController(jugadorDAO);

        // Crear el ModeloController
        modeloController = new ModeloController();
        modeloController.setVistaController(new VistaController());  // VistaController de prueba
    }

    @Test
    public void testValidarUsuario() throws SQLException {
        // Datos de prueba
        Usuario usuario = new Usuario("test", "testpass");

        // Crear un usuario en la base de datos de prueba
        usuarioController.crearUsuario(usuario);

        // Llamada al metodo
        boolean esValido = modeloController.validarUsuario(usuario);

        // Verificar resultados
        assertTrue(esValido, "El usuario debería ser válido.");
    }

    @Test
    public void testValidarPassWord() {

        Usuario usuario = new Usuario("test", "testpass");

        // Llamada al metodo de validación de contraseña
        boolean esValido = modeloController.validarPassWord("testpass");

        // Verifica resultados
        assertTrue(esValido, "La contraseña debería coincidir.");
    }

    @Test
    public void testValidarEquipo() throws Exception {
        // Crear un equipo
        Equipo equipo = new Equipo("Equipo Test");

        // Guardar equipo
        equipoController.crearEquipo(equipo);

        // Llamada al metodo
        boolean esValido = modeloController.validarEquipo(equipo);

        // Verificar resultados
        assertTrue(esValido, "El equipo debería ser válido.");
    }

    @Test
    public void testCrearEquipo() throws Exception {
        // Datos de prueba
        Equipo equipo = new Equipo("Equipo Nuevo");

        // Llamada al metodo
        boolean resultado = modeloController.crearEquipo(equipo);

        // Verificar resultados
        assertTrue(resultado, "El equipo debería ser creado.");
    }

    @Test
    public void testBorrarJugador() throws SQLException {
        // Crear un jugador
        Jugador jugador = new Jugador("Jugador Test");

        // Guardar jugador en la base de datos
        jugadorController.crearJugador(jugador);

        // Llamada al metodo
        boolean resultado = modeloController.borrarJugador(jugador);

        // Verificar resultados
        assertTrue(resultado, "El jugador debería ser borrado.");
    }
    @Test
    public void testValidarEquipoConEquipoNoExistente() throws Exception {
        // Crear un equipo q no exista
        Equipo equipoInexistente = new Equipo("Equipo Inexistente");

        // Verificar que no se puede validar el equipo
        boolean esValido = modeloController.validarEquipo(equipoInexistente);
        assertFalse(esValido, "El equipo no debería ser válido ya que no existe en la base de datos.");
    }
    @Test
    public void testCrearJugador() throws SQLException {
        // Crear un jugador
        Jugador jugador = new Jugador("Jugador Test");

        // Llamar al metodo de creación
        boolean resultado = modeloController.crearJugador(jugador);

        // Verificar que el jugador se creó correctamente
        assertTrue(resultado, "El jugador debería ser creado.");
    }
}
