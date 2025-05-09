package ModeloController;

import Modelo.Usuario;
import ModeloDAO.UsuarioDAO;

import java.sql.SQLException;

/**
 * @author Equipo tres
 * @version (2.0)
 */
public class UsuarioController {
    protected Usuario usuario;
    protected UsuarioDAO usuarioDAO;

    
    /**
     * Constructor 
     * @param usuarioDAO 
     */
    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

     /**
     * Valida las usuario, Validacion correctas, se almacena el usuario  
     * @param u usuario 
     * @return el usuario  si la validación es corecta
     * @throws SQLException si ocurre un error 
     */
    public Usuario validarUsuario(Usuario u) throws SQLException {
        return usuario = usuarioDAO.validarUsuario(u);
    }

}
