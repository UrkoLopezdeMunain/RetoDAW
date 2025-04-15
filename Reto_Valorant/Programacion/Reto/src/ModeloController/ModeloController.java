package ModeloController;

import BaseDatos.BaseDatos;
import Modelo.Equipo;
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
    public ModeloController() {
        try {
            //BD
            Connection c = BaseDatos.abrirCon();

            //dao + conexion a BD
            CompeticionDAO competicionDAO = new CompeticionDAO(c);
            EnfrentamientoDAO enfrentamientoDAO = new EnfrentamientoDAO(c);
            EquipoDAO equipoDAO = new EquipoDAO(c);
            JornadaDAO jornadaDAO = new JornadaDAO(c);
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
    public boolean validarUsuario(String nombreUsuario) throws SQLException {
        usuario = usuarioController.validarUsuario(nombreUsuario);
        return usuario != null;
    }
    public boolean validarPassWord(String passWord){
        return usuario.getPaswd().equals(passWord);
    }

    public boolean validarEquipo(String nombreEquipo) throws Exception {
        //falta meter Patron aqui para el nombre del equipo
        equipo = equipoController.validarEquipo(nombreEquipo);

        if (equipo != null){
            equipo.setListaJugadores(jugadorController.obtenerJugadores(equipo.getCodEquipo()));
            //para poder aprovechar directamente todos sus atributos lo relleno ya
        }
        return equipo != null;
    }
    public boolean crearEquipo(String nombre,String fechaFund) throws Exception {
        return equipoController.crearEquipo(nombre, fechaFund);
    }
    public boolean borrarEquipo(String nombreEquipo) throws Exception {
        return equipoController.borrarEquipo(nombreEquipo);
    }
    public boolean actualizarEquipoFecha(String nombreEquipo, String fechaFund) throws Exception {
        return equipoController.actualizarEquipoFecha(nombreEquipo,fechaFund);
    }
    public List<Equipo> getEquipos() throws SQLException {
        return equipoController.getEquipos();
    }

    public boolean crearJugador(String nombre, String apellido, String nacionalidad, String fechaNac, String sueldo, String nickName, Object nombreEquipo) throws SQLException {
        return jugadorController.crearJugador(nombre,apellido,nacionalidad,fechaNac,sueldo,nickName,nombreEquipo);
    }
}
