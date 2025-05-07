package ModeloController;

import Modelo.Equipo;
import Modelo.Jugador;
import Modelo.Usuario;
import Vista.*;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VistaController {
    protected ModeloController modeloController;
    private ConsultarEquipo consultarEquipo;
    private ActualizarEquipo actualizarEquipo;
    private ConsultarJugador consultarJugador;
    private final LocalDate muyPeque = LocalDate.now().minusYears(16);
    private final LocalDate muyMayor = LocalDate.now().plusYears(65);

    public VistaController(ModeloController modeloController) {
        this.modeloController = modeloController;
        setIniciarSesion();
    }

    /**Metodos de construccion de ventanas*/
    public void setIniciarSesion() {
        IniciarSesion iniciarSesion = new IniciarSesion(this);
        iniciarSesion.setVisible(true);
    }
    public void setConsultarEquipo(VistaController vistaController){
        consultarEquipo = new ConsultarEquipo(this);
        consultarEquipo.setVisible(true);
    }
    public void setBorrarEquipo(VistaController vistaController){
        BorrarEquipo borrarEquipo = new BorrarEquipo(this);
        borrarEquipo.setVisible(true);
    }
    public void setBorrarJugador(VistaController vistaController){
        BorrarJugador borrarJugador = new BorrarJugador(this);
        borrarJugador.setVisible(true);
    }
    public void setConsultarJugador(VistaController vistaController){
        consultarJugador = new ConsultarJugador(this);
        consultarJugador.setVisible(true);
    }
    public void setActualizarJugador(VistaController vistaController){
        ActualizarJugador actualizarJugador = new ActualizarJugador(this);
        actualizarJugador.setVisible(true);
    }
    public void setCrearJugador(VistaController vistaController) throws SQLException {
        CrearJugador crearJugador = new CrearJugador(vistaController);
        crearJugador.setVisible(true);
    }
    public void setCrearEquipo(VistaController vistaController){
        CrearEquipo crearEquipo = new CrearEquipo(vistaController);
        crearEquipo.setVisible(true);
    }
    public void setActualizarEquipo(VistaController vistaController){
        actualizarEquipo = new ActualizarEquipo(this);
        actualizarEquipo.setVisible(true);
    }

    public ConsultarEquipo getIniciarSesion() {
        return consultarEquipo;
    }
    public Equipo getEquipo() {
        return modeloController.getEquipo();
    }
    public List<Equipo> getEquipos() throws Exception {
        return modeloController.getEquipos();
    }

    /**Metodos de validacion*/
    public boolean validarUsuario(String nombreUsuario) throws SQLException {
        Usuario usuario = new Usuario(nombreUsuario);
        return modeloController.validarUsuario(usuario);
    }
    public boolean validarPassWord(String passWord) throws SQLException {
        return modeloController.validarPassWord(passWord);
    }
    public boolean validarEquipo(String nombreEquipo) throws Exception {
        //Hecho con toLowerCase para consultas y transacciones con BD
        Equipo equipo = new Equipo(nombreEquipo.toLowerCase());
        return modeloController.validarEquipo(equipo);
    }
    public boolean validarJugador(String nickName) throws Exception {
        if(validarNik(nickName)){
            Jugador jugador = new Jugador(nickName);
            return modeloController.validarJugador(jugador);
        }
        return false;
    }
    public boolean validarSueldo(String sueldo){
        return Double.parseDouble(sueldo) >= 1184.00;
    }
    public boolean validarNik(String nickName){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{3,20}$");
        Matcher matcher = pattern.matcher(nickName);
        return matcher.matches();
    }
    public boolean validarNomYAp(String nombreJugador)throws Exception {
        final Pattern pattern = Pattern.compile("^[A-Za-zñÑáéíóúÁÉÍÓÚüÜ ]{2,20}$");
        final Matcher matcher = pattern.matcher(nombreJugador);
        if (!matcher.matches()) {
            throw new Exception("El campo rellenado debe ser minimo de 2 a 20 caracteres");
        }
        return false;
    }
    public  boolean validarFechaNac(String fechaNaci) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha = LocalDate.parse(fechaNaci, formatter);

        if (fecha.isAfter(muyPeque) || fecha.isBefore(muyMayor)) {
            return fecha.isBefore(LocalDate.now());
        }
        throw new Exception("La edad debe estar comprendida entre los 16 y 65 años");
    }

    /**Metodos de creacion*/
    public boolean crearEquipo(String nombre,String fechaFund) throws Exception {
        Equipo equipo = new Equipo(nombre,validarFecha(fechaFund));
        return modeloController.crearEquipo(equipo);
    }

    public boolean crearJugador(String nombre, String apellido, String nacionalidad, String fechaNac, String sueldo, String rol, String nickName, Equipo equipo) throws Exception {
        Jugador jugador = new Jugador(nombre,apellido,nacionalidad,fechaNac,sueldo,rol,nickName,equipo);

        return modeloController.crearJugador(jugador);
    }

    /**Metodos de borrado*/
    public boolean borrarJugador(String nickName) throws Exception {
        Jugador jugador = new Jugador(nickName);
        return modeloController.borrarJugador(jugador);
    }
    public boolean borrarEquipo(String nombreEquipo) throws Exception {
        Equipo equipo = new Equipo(nombreEquipo.toLowerCase());
        return modeloController.borrarEquipo(equipo);
    }

    public LocalDate validarFecha(String fecha) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate fechaLocalDate = LocalDate.parse(fecha.trim(), formatter);
            if (fechaLocalDate.isAfter(LocalDate.now())) {
                throw new Exception("La fecha de fundacion no puede ser de anterior a la del juego");
            }
            return fechaLocalDate;
        }catch (DateTimeParseException e){
            throw new DateTimeParseException("La fecha no sigue un formato valido (dd-mm-aaaa)", fecha,0);
        }
    }

    /**Metodos de consulta*/
    public void rellenarCamposEquipo(JPanel pPrincipal) {
        //entre otros
        consultarEquipo.getTfNombreEquipo().setText(modeloController.equipo.getNombre());
        consultarEquipo.getTfCodEquipo().setText(String.valueOf(modeloController.equipo.getCodEquipo()));
        consultarEquipo.getTfFechaFundacion().setText(modeloController.equipo.getFechaFundacion().toString());
        consultarEquipo.getTfPuntuacionTotal().setText(String.valueOf(modeloController.equipo.getPuntuacion()));
        pPrincipal.revalidate();
        pPrincipal.repaint();

    }

    public void rellenarCamposJugador(JPanel pPrincipal){
        /*
        consultarJugador.getTfNombre().setText(modeloController.jugador.getNombre());
        consultarJugador.getTfApellido().setText(modeloController.jugador.getApellido());
        consultarJugador.getTfRol().setText(modeloController.jugador.getRol());
        consultarJugador.getTfFechaNaci().setText(modeloController.jugador.getFechaNacimiento().toString());
        consultarJugador.getTfNacionalidad().setText(modeloController.jugador.getNacionalidad());
        consultarJugador.getTfSueldo().setText(String.valueOf(modeloController.jugador.getSueldo()));
        consultarJugador.getTfEquipo().setText(modeloController.jugador.getEquipo().getNombre());
        //se omite el nickname por que si ha llegado aqui es por que es correcto
        pPrincipal.revalidate();
        pPrincipal.repaint();*/
    }
    public void rellenarCamposEquipoUpdate(JPanel pPrincipal) {
        actualizarEquipo.getTfNombreNuevo().setText(modeloController.equipo.getNombre());
        actualizarEquipo.getTfFechaFundNueva().setText(modeloController.equipo.getFechaFundacion().toString());
        pPrincipal.revalidate(); pPrincipal.repaint();
    }

    /**Metodos de Actualizacion de datos*/
    public boolean actualizarEquipoFecha(String nombreEquipo, String fechaFund) throws Exception {
        Equipo equipo = new Equipo(nombreEquipo, validarFecha(fechaFund));
        return modeloController.actualizarEquipoFecha(equipo);
    }


}