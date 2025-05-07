package ModeloController;

import Modelo.Usuario;
import ModeloDAO.UsuarioDAO;

import java.sql.SQLException;

public class UsuarioController {
    protected Usuario usuario;
    protected UsuarioDAO usuarioDAO;

    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario validarUsuario(Usuario u) throws SQLException {
        return usuario = usuarioDAO.validarUsuario(u);
    }

}
