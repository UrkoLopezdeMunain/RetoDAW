package ModeloController;

import Modelo.Equipo;
import Modelo.Jugador;
import Vista.*;

import javax.swing.*;
import java.sql.SQLException;

public class VistaController {
    protected ModeloController modeloController;
    private ConsultarEquipo consultarEquipo;
    private ActualizarEquipo actualizarEquipo;
    private BorrarEquipo borrarEquipo;
    public VistaController(ModeloController modeloController) {
        this.modeloController = modeloController;
        setIniciarSesion();
    }
    public void setIniciarSesion() {
        IniciarSesion iniciarSesion = new IniciarSesion(this);
        iniciarSesion.setVisible(true);
    }
    public void setConsultarEquipo(VistaController vistaController){
        consultarEquipo = new ConsultarEquipo(this);
        consultarEquipo.setVisible(true);
    }
    public void setBorrarEquipo(VistaController vistaController){
        this.borrarEquipo = new BorrarEquipo(this);
        borrarEquipo.setVisible(true);
    }
    public ConsultarEquipo getIniciarSesion() {
        return consultarEquipo;
    }
    public void setActualizarEquipo(VistaController vistaController){
        actualizarEquipo = new ActualizarEquipo(this);
        actualizarEquipo.setVisible(true);
    }
    public Equipo getEquipo() {
        return modeloController.getEquipo();
    }

    public boolean validarUsuario(String nombreUsuario) throws SQLException {
        return modeloController.validarUsuario(nombreUsuario);
    }
    public boolean validarPassWord(String passWord) throws SQLException {
        return modeloController.validarPassWord(passWord);
    }
    public void setCrearEquipo(VistaController vistaController){
        CrearEquipo crearEquipo = new CrearEquipo(vistaController);
        crearEquipo.setVisible(true);
    }
    public boolean validarEquipo(String nombreEquipo) throws Exception {
        //Hecho con toLowerCase para consultas y transacciones con BD
        return modeloController.validarEquipo(nombreEquipo.toLowerCase());
    }
    public boolean crearEquipo(String nombre,String fechaFund) throws Exception {
        return modeloController.crearEquipo(nombre,fechaFund);
    }
    public boolean borrarEquipo(String nombreEquipo) throws Exception {
        return modeloController.borrarEquipo(nombreEquipo);
    }

    public void rellenarCamposEquipo(JPanel pPrincipal) {
        //entre otros
        consultarEquipo.getTfNombreEquipo().setText(modeloController.equipo.getNombre());
        consultarEquipo.getTfCodEquipo().setText(String.valueOf(modeloController.equipo.getCodEquipo()));
        consultarEquipo.getTfFechaFundacion().setText(modeloController.equipo.getFechaFundacion().toString());
        consultarEquipo.getTfPuntuacionTotal().setText(String.valueOf(modeloController.equipo.getPuntuacion()));
        if (modeloController.equipo.getListaJugadores().isEmpty()) {
            consultarEquipo.getTaJugadores().setText("No tiene ningun jugador");
        } else {
            StringBuilder jugadores = new StringBuilder();
            for (Jugador j : modeloController.equipo.getListaJugadores()) {
                jugadores.append(j.toString()).append("\n");
                //pone en columnas los jugadores del objeto equipo devuelto
            }
            consultarEquipo.getTaJugadores().setText(jugadores.toString());
            //los visualiza en el textArea

            pPrincipal.revalidate();
            pPrincipal.repaint();
        }
    }
    public boolean actualizarEquipoFecha(String nombreEquipo, String fechaFund) throws Exception {
        return modeloController.actualizarEquipoFecha(nombreEquipo,fechaFund);
    }
    public void rellenarCamposEquipoUpdate(JPanel pPrincipal) {
        actualizarEquipo.getTfNombreNuevo().setText(modeloController.equipo.getNombre());
        actualizarEquipo.getTfFechaFundNueva().setText(modeloController.equipo.getFechaFundacion().toString());
        pPrincipal.revalidate(); pPrincipal.repaint();
    }
}
