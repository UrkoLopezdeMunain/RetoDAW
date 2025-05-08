package ModeloControllerTest;

import java.sql.SQLException;

public class UsuarioControllerTest {

    private UsuarioController usuarioController;
    private Connection connection;
    private UsuarioDAO usuarioDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        // Conexión a base de datos
        connection = BaseDatos.abrirCon();

        // Inicializar UsuarioDAO con la conexión
        usuarioDAO = new UsuarioDAO(connection);

        // Inicializar el UsuarioController con el DAO
        usuarioController = new UsuarioController(usuarioDAO);

        //eliminar si ya existe
        usuarioDAO.eliminarUsuario("testuser");

        // Crear datos de prueba
        Usuario usuario = new Usuario("testuser", "testpass");
        usuarioDAO.crearUsuario(usuario);
    }

    @Test
    public void testValidarUsuario() throws SQLException {
        // Crear un usuario incorrecto
        Usuario usuario = new Usuario("testuser", "testpass");

        // Llamada al metodo de validación
        Usuario usuarioValido = usuarioController.validarUsuario(usuario);

        // Verificar que el usuario es válido y no es null
        assertNotNull(usuarioValido, "El usuario debería ser válido.");
        assertEquals("testuser", usuarioValido.getNombre(), "El nombre de usuario debe coincidir.");
    }

    @Test
    public void testValidarUsuarioInvalido() throws SQLException {
        // Crear un usuario incorrecto
        Usuario usuario = new Usuario("testuser", "wrongpass");

        // Llamada al metodo de validación
        Usuario usuarioInvalido = usuarioController.validarUsuario(usuario);

        // Verificar que el usuario no es válido y el resultado es null
        assertNull(usuarioInvalido, "El usuario debería ser inválido.");
    }
}