package ModeloDAOTest;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDAOTest {

    private static Connection con;
    private UsuarioDAO usuarioDAO;

    @BeforeAll
    public static void setupDatabase() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        con = ds.getConnection();

        Statement st = con.createStatement();
        st.execute("CREATE TABLE usuarios (nombre VARCHAR(50) PRIMARY KEY, contraseña VARCHAR(50), tipo_usuario VARCHAR(50))");

        // Insertar usuario de prueba
        st.execute("INSERT INTO usuarios (nombre, contraseña, tipo_usuario) VALUES ('testuser', 'password123', 'admin')");
    }

    @BeforeEach
    public void setup() {
        usuarioDAO = new UsuarioDAO(con);
    }

    @Test
    public void testValidarUsuarioExistente() throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("testuser");

        // Para Validar
        Usuario usuarioValido = usuarioDAO.validarUsuario(usuario);

        assertNotNull(usuarioValido);
        assertEquals("testuser", usuarioValido.getNombreUsuario());
        assertEquals("password123", usuarioValido.getPaswd());
        assertEquals("admin", usuarioValido.getTipoUsuario());
    }

    @Test
    public void testValidarUsuarioNoExistente() throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("nonexistentuser");

        // Validar usuario que no existe
        Usuario usuarioInvalido = usuarioDAO.validarUsuario(usuario);

        assertNull(usuarioInvalido); // Debe retornar null porque el usuario no existe
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        con.close();
    }
}
