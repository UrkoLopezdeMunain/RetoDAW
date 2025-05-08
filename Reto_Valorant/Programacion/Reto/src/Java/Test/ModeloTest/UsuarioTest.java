package ModeloTest;

import ModeloTest.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void testConstructorConNombreYPass() {
        Usuario usuario = new Usuario("admin", "1234", "administrador");

        assertEquals("admin", usuario.getNombreUsuario());
        assertEquals("1234", usuario.getPaswd());
        assertEquals("administrador", usuario.getTipoUsuario());
    }

    @Test
    public void testSettersYGetters() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("juan");
        usuario.setPaswd("pass");
        usuario.setTipoUsuario("cliente");

        assertEquals("juan", usuario.getNombreUsuario());
        assertEquals("pass", usuario.getPaswd());
        assertEquals("cliente", usuario.getTipoUsuario());
    }

    @Test // para el constructor con solo nombre de usuario
    public void testConstructorConNombreUsuario() {

        Usuario usuario = new Usuario("juan");

        assertEquals("juan", usuario.getNombreUsuario());
        assertNull(usuario.getPaswd()); //  sea null
        assertNull(usuario.getTipoUsuario()); //  sea null
    }
}
