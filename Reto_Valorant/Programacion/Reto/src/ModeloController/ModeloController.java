package ModeloController;

import Modelo.Competicion;
import Modelo.Enfrentamiento;
import Modelo.Usuario;
import ModeloDAO.*;

import java.sql.SQLException;

public class ModeloController {
    protected VistaController vistaController;

    protected CompeticionController competicionController;
    protected EnfrentamientoController enfrentamientoController;
    protected EquipoController equipoController;
    protected JornadaController jornadaController;
    protected JuegoController juegoController;
    protected JugadorController jugadorController;
    protected RolController rolController;
    protected UsuarioController usuarioController;

    protected Usuario usuario;

    public ModeloController() {
        try {
            //BD
            //BaseDatos = new BaseDatos();

            //dao
            CompeticionDAO competicionDAO = new CompeticionDAO();
            EnfrentamientoDAO enfrentamientoDAO = new EnfrentamientoDAO();
            EquipoDAO equipoDAO = new EquipoDAO();
            JornadaDAO jornadaDAO = new JornadaDAO();
            JuegoDAO juegoDAO = new JuegoDAO();
            JugadorDAO jugadorDAO = new JugadorDAO();
            RolDAO rolDAO = new RolDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            //Controllers
            competicionController = new CompeticionController(competicionDAO);
            enfrentamientoController = new EnfrentamientoController(enfrentamientoDAO);
            equipoController = new EquipoController(equipoDAO);
            jornadaController = new JornadaController(jornadaDAO);
            juegoController = new JuegoController(juegoDAO);
            jugadorController= new JugadorController(jugadorDAO);
            rolController = new RolController(rolDAO);
            usuarioController = new UsuarioController(usuarioDAO);
        }catch (Exception e) {
            System.out.println("ERROR EN MODELO CONTROLLER "+e.getMessage());
        }
    }

    public void setVistaController(VistaController vistaController) {
        this.vistaController = vistaController;
    }

    public boolean validarUsuario(String nombreUsuario) throws SQLException {
        usuario = usuarioController.validarUsuario(nombreUsuario);
        return usuario != null;
    }
}
