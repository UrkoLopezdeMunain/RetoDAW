package ModeloController;

import BaseDatos.BaseDatos;
import Modelo.Equipo;
import Modelo.Jugador;
import Modelo.Usuario;
import ModeloDAO.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ModeloController {
    protected VistaController vistaController;

    protected CompeticionController competicionController;
    protected EnfrentamientoController enfrentamientoController;
    protected EquipoController equipoController;
    protected JornadaController jornadaController;
    protected JugadorController jugadorController;
    private UsuarioController usuarioController;

    protected Usuario usuario;
    protected Equipo equipo;
    protected Jugador jugador;
    public ModeloController() {
        try {
            //BD
            Connection c = BaseDatos.abrirCon();

            //dao + conexion a BD
            CompeticionDAO competicionDAO = new CompeticionDAO(c);
            EnfrentamientoDAO enfrentamientoDAO = new EnfrentamientoDAO(c);
            EquipoDAO equipoDAO = new EquipoDAO(c);
            JornadaDAO jornadaDAO = new JornadaDAO(c, competicionDAO);
            JugadorDAO jugadorDAO = new JugadorDAO(c);
            UsuarioDAO usuarioDAO = new UsuarioDAO(c);

            //Controllers
            competicionController = new CompeticionController(competicionDAO);
            enfrentamientoController = new EnfrentamientoController(enfrentamientoDAO);
            equipoController = new EquipoController(equipoDAO);
            jornadaController = new JornadaController(jornadaDAO);
            jugadorController= new JugadorController(jugadorDAO);
            usuarioController = new UsuarioController(usuarioDAO);
        }catch (Exception e) {
            System.out.println("ERROR EN MODELO CONTROLLER "+e.getMessage());
        }
    }

    public void setVistaController(VistaController vistaController) {
        this.vistaController = vistaController;
    }
    public Equipo getEquipo(){
        return equipo;
    }
    public List<Equipo> getEquipos() throws Exception{
        return equipoController.getEquipos();
    }
    public boolean validarUsuario(Usuario usuario) throws SQLException {
        usuario = usuarioController.validarUsuario(usuario);
        return usuario != null;
    }
    public boolean validarPassWord(String passWord){
        return usuario.getPaswd().equals(passWord);
    }

    public boolean validarEquipo(Equipo equipo) throws Exception {
        //falta meter Patron aqui para el nombre del equipo
        equipo = equipoController.validarEquipo(equipo);

        if (equipo != null){
            equipo.setListaJugadores(jugadorController.obtenerJugadores(equipo));
            //para poder aprovechar directamente todos sus atributos lo relleno ya
        }
        return equipo != null;
    }
    public boolean crearEquipo(Equipo equipo) throws Exception {
        return equipoController.crearEquipo(equipo);
    }
    public boolean borrarEquipo(Equipo equipo) throws Exception {
        return equipoController.borrarEquipo(equipo);
    }
    public boolean actualizarEquipoFecha(Equipo equipo) throws Exception {
        return equipoController.actualizarEquipoFecha(equipo);
    }
    public boolean validarJugador(Jugador j) throws Exception {
        jugador = jugadorController.validarJugador(j);
        return jugador != null;
    }
    public boolean crearJugador(Jugador j) throws Exception {
        return jugadorController.crearJugador(j);
    }
    public boolean borrarJugador(Jugador j) throws Exception {
        return jugadorController.borrarJugador(j);
    }

    public boolean iniciarCompeticion() throws SQLException {
        return competicionController.iniciarCompeticion();
    }
}
