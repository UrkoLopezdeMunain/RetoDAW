package ModeloController;

import Modelo.Equipo;
import Vista.*;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VistaController {
    protected ModeloController modeloController;
    private ConsultarEquipo consultarEquipo;
    private ActualizarEquipo actualizarEquipo;
    private BorrarEquipo borrarEquipo;
    private CrearJugador crearJugador;
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

    public void setCrearJugador(VistaController vistaController) throws SQLException {
        this.crearJugador = new CrearJugador(vistaController);
        crearJugador.setVisible(true);
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
    public List<Equipo> getEquipos() throws SQLException {
        return modeloController.getEquipos();
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
    public boolean validarSueldo(String sueldo){
        return Double.parseDouble(sueldo) >= 1184.00;
    }
    public boolean validarNik(String nickName){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{3,20}$");
        Matcher matcher = pattern.matcher(nickName);
        return matcher.matches();
    }

    public void rellenarCamposEquipo(JPanel pPrincipal) {
        //entre otros
        consultarEquipo.getTfNombreEquipo().setText(modeloController.equipo.getNombre());
        consultarEquipo.getTfCodEquipo().setText(String.valueOf(modeloController.equipo.getCodEquipo()));
        consultarEquipo.getTfFechaFundacion().setText(modeloController.equipo.getFechaFundacion().toString());
        consultarEquipo.getTfPuntuacionTotal().setText(String.valueOf(modeloController.equipo.getPuntuacion()));
        //Comentadfo por que se pide en otro metodo(de BD)
        pPrincipal.revalidate();
        pPrincipal.repaint();
    }

    public boolean validarNomYAp(String nombreJugador) {
        final Pattern pattern = Pattern.compile("^[a-z ]{2,20}+$");
        final Matcher matcher = pattern.matcher(nombreJugador);
        return matcher.matches();
    }

    public  boolean validarFechaNac(String fechaNaci) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return LocalDate.parse(fechaNaci, formatter).isAfter(LocalDate.now());
    }

    public boolean actualizarEquipoFecha(String nombreEquipo, String fechaFund) throws Exception {
        return modeloController.actualizarEquipoFecha(nombreEquipo,fechaFund);
    }
    public void rellenarCamposEquipoUpdate(JPanel pPrincipal) {
        actualizarEquipo.getTfNombreNuevo().setText(modeloController.equipo.getNombre());
        actualizarEquipo.getTfFechaFundNueva().setText(modeloController.equipo.getFechaFundacion().toString());
        pPrincipal.revalidate(); pPrincipal.repaint();
    }

    public boolean crearJugador(String nombre, String apellido, String nacionalidad, String fechaNac, String sueldo, String rol, String nickName, int codEquipo) throws SQLException {
        return modeloController.crearJugador(nombre,apellido,nacionalidad,fechaNac,sueldo,rol,nickName,codEquipo);
    }
}