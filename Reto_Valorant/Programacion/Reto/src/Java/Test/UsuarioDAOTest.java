package Java.Test;

import BaseDatos.BaseDatos;
import Modelo.Usuario;
import ModeloDAO.UsuarioDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDAOTest {

    private static Connection c;
    private static UsuarioDAO usuarioDAO;
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        c = BaseDatos.abrirCon();
        usuarioDAO = new UsuarioDAO(c);
    }
    @AfterAll
    public static void tearDownAfterClass() throws SQLException {
        c.close();
        usuarioDAO = null;
    }

    @Test
    void validarUsuario() throws SQLException {
        assertNotNull(usuarioDAO.validarUsuario(new Usuario("admin","Jm12345","a")));

    }
}