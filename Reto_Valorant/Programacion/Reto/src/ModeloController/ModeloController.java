package ModeloController;

import BaseDatos.BaseDatos;
import Modelo.*;
import ModeloDAO.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ModeloController {
    protected VistaController vistaController;

    protected CompeticionController competicionController;
    protected EnfrentamientoController enfrentamientoController;
    protected EquipoController equipoController;
    protected JornadaController jornadaController;
    protected JugadorController jugadorController;
    protected UsuarioController usuarioController;

    protected Usuario usuario;
    protected Equipo equipo;
    protected Jugador jugador;

    public ModeloController() {
        try {
            //BD
            Connection c = BaseDatos.abrirCon();

            //dao + conexion a BD
            JuegoDAO jDAO = new JuegoDAO(c);
            CompeticionDAO competicionDAO = new CompeticionDAO(c);
            EnfrentamientoDAO enfrentamientoDAO = new EnfrentamientoDAO(c);
            EquipoDAO equipoDAO = new EquipoDAO(c);
            JornadaDAO jornadaDAO = new JornadaDAO(c, competicionDAO);
            JugadorDAO jugadorDAO = new JugadorDAO(c);
            UsuarioDAO usuarioDAO = new UsuarioDAO(c);

            //Controllers
            competicionController = new CompeticionController(competicionDAO);
            enfrentamientoController = new EnfrentamientoController(enfrentamientoDAO,this);
            enfrentamientoDAO.setEnfrentamientoController(enfrentamientoController);
            equipoController = new EquipoController(equipoDAO);
            jornadaController = new JornadaController(jornadaDAO,this);
            jugadorController= new JugadorController(jugadorDAO);
            usuarioController = new UsuarioController(usuarioDAO);
        }catch (Exception e) {
            System.out.println("ERROR EN MODELO CONTROLLER "+e.getMessage());
        }
    }
    /**Metodo auxiliar de Main*/
    public void setVistaController(VistaController vistaController) {
        this.vistaController = vistaController;
    }
    /**Metodos para optimizar accesos a BD*/
    public List<Equipo> getEquipos() throws SQLException {
        return equipoController.getEquipos();
    }
    public Equipo getEquipo(){
        return equipo;
    }

    public List<Enfrentamiento> getEnfrentamientos() throws Exception{
        return enfrentamientoController.getEnfrentamientos();
    }

    /**Metodos de validacion*/
    public boolean validarUsuario(Usuario u) throws SQLException {
        usuario = usuarioController.validarUsuario(u);
        return usuario != null;
    }
    public boolean validarPassWord(String passWord){
        return usuario.getPaswd().equals(passWord);
    }
    public boolean validarEquipo(Equipo eq) throws Exception {
        equipo = equipoController.validarEquipo(eq);
        return equipo != null;
    }
    public boolean validarJugador(Jugador jugador) throws SQLException {
        jugador = jugadorController.obtnerJugador(jugador);
        return jugador != null;
    }

    public void crearJornadas()throws SQLException{
        jornadaController.crearJornada();
    }

    public void crearEnfrentamiento() throws Exception {
        enfrentamientoController.crearEnfrentamientos();
    }


        /**Metodos de creacion*/
    public boolean crearEquipo(Equipo equipo) throws Exception {
        return equipoController.crearEquipo(equipo);
    }
    public boolean crearJugador(Jugador jugador) throws SQLException {
        return jugadorController.crearJugador(jugador);
    }

    /**Metodos de borrado*/
    public boolean borrarJugador(Jugador ju) throws SQLException {
        return jugadorController.borrarJugador(ju);
    }
    public boolean borrarEquipo(Equipo equipo) throws Exception {
        return equipoController.borrarEquipo(equipo);
    }

    /**Metodos de actualizacion*/
    public boolean actualizarEquipoFecha(Equipo eq) throws Exception {
        return equipoController.actualizarEquipoFecha(eq);
    }

    public List<Jornada> getJornadas() throws SQLException{
        return jornadaController.getJornadas();
    }

    public Competicion getCompeticion() throws SQLException{
        return competicionController.getCompeticion();
    }
    public Jornada getJornadaPorId(int id) throws SQLException{
        return jornadaController.getJornadaPorId(id);
    }

    public void guardarResultados(Map<Integer, String> resultados) throws Exception {
        for (Map.Entry<Integer, String> entry : resultados.entrySet()) {
            int idEnfrentamiento = entry.getKey();
            String resultado = entry.getValue();

            // Validar resultado (opcional)
            if (!resultado.equals("EQUIPO1") && !resultado.equals("EQUIPO2")) {
                throw new Exception("Resultado no válido para el enfrentamiento " + idEnfrentamiento);
            }

            // Delegar al DAO
            enfrentamientoController.actualizarResultado(idEnfrentamiento, resultados);
        }
    }
}
