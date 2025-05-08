package ModeloController;

import Modelo.*;
import Vista.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VistaController {
    protected ModeloController modeloController;
    private ConsultarEquipo consultarEquipo;
    private ActualizarEquipo actualizarEquipo;
    private ConsultarJugador consultarJugador;
    private GestionarEnfrentamientos gestionarEnfrentamientos;
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
        consultarEquipo = new ConsultarEquipo(vistaController);
        consultarEquipo.setVisible(true);
    }
    public void setBorrarEquipo(VistaController vistaController){
        BorrarEquipo borrarEquipo = new BorrarEquipo(vistaController);
        borrarEquipo.setVisible(true);
    }
    public void setBorrarJugador(VistaController vistaController){
        BorrarJugador borrarJugador = new BorrarJugador(vistaController);
        borrarJugador.setVisible(true);
    }

    public void setConsultarJugador(VistaController vistaController){
        consultarJugador = new ConsultarJugador(vistaController);
        consultarJugador.setVisible(true);
    }
    public void setActualizarJugador(VistaController vistaController){
        ActualizarJugador actualizarJugador = new ActualizarJugador(vistaController);
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
        actualizarEquipo = new ActualizarEquipo(vistaController);
        actualizarEquipo.setVisible(true);
    }
    public void setGestionarEnfrentamientos(VistaController vistaController){
        gestionarEnfrentamientos = new GestionarEnfrentamientos(vistaController);
        gestionarEnfrentamientos.obtenerJornadas();
        gestionarEnfrentamientos.rellenarConEquipos();
        gestionarEnfrentamientos.setVisible(true);
    }
    public void crearJornadas()throws SQLException{
        modeloController.crearJornadas();
    }

    public void crearEnfrentamiento() throws Exception {
        modeloController.crearEnfrentamiento();
    }

    public ConsultarEquipo getIniciarSesion() {
        return consultarEquipo;
    }

    public Equipo getEquipo() {
        return modeloController.getEquipo();
    }

    /**Metodos de validacion*/
    public List<Equipo> getEquipos() throws SQLException {
        return modeloController.getEquipos();
    }
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
    public boolean validarJugador(String nickName) throws SQLException {
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
    public boolean borrarJugador(String nickName) throws SQLException {
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

    /**El objetivo es que con cada Jornada el TA cambie tambien*/
    public void rellenarCamposGestionarEnfrentamientos(JPanel pPrincipal) throws Exception {
        List<Enfrentamiento> enfrentamientos = modeloController.getEnfrentamientos();

        pPrincipal.removeAll();
        pPrincipal.setLayout(new BoxLayout(pPrincipal, BoxLayout.Y_AXIS)); // Example layout

        // Para el TextArea
        JTextArea taEnfrentamientos = gestionarEnfrentamientos.getTaEnfrentamientos();
        taEnfrentamientos.setText("");
        for (Enfrentamiento e : enfrentamientos) {
            taEnfrentamientos.append(e.toString() + "\n");
        }
        pPrincipal.add(new JScrollPane(taEnfrentamientos));

        // Add matchup selection components
        for (Enfrentamiento enfrentamiento : enfrentamientos) {
            // Create a panel for each matchup
            JPanel matchupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            matchupPanel.setBorder(BorderFactory.createTitledBorder("Enfrentamiento: " + enfrentamiento.getIdEnfrentamiento()));

            ButtonGroup bg = new ButtonGroup();
            JRadioButton rb1 = new JRadioButton(enfrentamiento.getEquipo1().getNombre());
            JRadioButton rb2 = new JRadioButton(enfrentamiento.getEquipo2().getNombre());

            bg.add(rb1);
            bg.add(rb2);

            //guarda la seleccion del boton en caso de necesitarlo luego
            rb1.setActionCommand(enfrentamiento.getIdEnfrentamiento() + "_team1");
            rb2.setActionCommand(enfrentamiento.getIdEnfrentamiento() + "_team2");

            matchupPanel.add(rb1);
            matchupPanel.add(rb2);

            pPrincipal.add(matchupPanel);
        }

        pPrincipal.revalidate();
        pPrincipal.repaint();
    }

    public void rellenarCamposJugador(JPanel pPrincipal){
        consultarJugador.getTfNombre().setText(modeloController.jugador.getNombre());
        consultarJugador.getTfApellido().setText(modeloController.jugador.getApellido());
        consultarJugador.getTfRol().setText(modeloController.jugador.getRol());
        consultarJugador.getTfFechaNaci().setText(modeloController.jugador.getFechaNacimiento().toString());
        consultarJugador.getTfNacionalidad().setText(modeloController.jugador.getNacionalidad());
        consultarJugador.getTfSueldo().setText(String.valueOf(modeloController.jugador.getSueldo()));
        consultarJugador.getTfEquipo().setText(modeloController.jugador.getEquipo().getNombre());
        //se omite el nickname por que si ha llegado aqui es por que es correcto
        pPrincipal.revalidate();
        pPrincipal.repaint();
    }
    public void rellenarCamposEquipoUpdate(JPanel pPrincipal) {
        actualizarEquipo.getTfNombreNuevo().setText(modeloController.equipo.getNombre());
        actualizarEquipo.getTfFechaFundNueva().setText(modeloController.equipo.getFechaFundacion().toString());
        pPrincipal.revalidate(); pPrincipal.repaint();
    }

    /**Metodos de Actualizacion de datos*/
    public boolean actualizarEquipoFecha(String nombreEquipo, LocalDate fechaFund) throws Exception {
        Equipo equipo = new Equipo(nombreEquipo, validarFecha(String.valueOf(fechaFund)));
        return modeloController.actualizarEquipoFecha(equipo);
    }

    public boolean actualizarEquipoNombre(String nombreEquipo, LocalDate fechaFund) throws Exception {
        Equipo equipo = new Equipo(nombreEquipo, validarFecha(String.valueOf(fechaFund)));
        return modeloController.actualizarEquipoNombre(equipo);
    }
    public List<Jornada> obtenerJornadas() throws Exception {
        return modeloController.jornadaController.getJornadas();
    }

    public void guardarResultados(JPanel pPrincipal) throws Exception {
        Map<Integer, String> resultados = new HashMap<>();

        // 1. Recorrer todos los componentes del panel principal
        for (Component comp : pPrincipal.getComponents()) {
            if (comp instanceof JPanel panelEnfrentamiento) {

                // 2. Obtener el ID del enfrentamiento desde el panel (usando el nombre o un campo oculto)
                int idEnfrentamiento = Integer.parseInt(panelEnfrentamiento.getName()); // Ejemplo: panel.setName("123")

                // 3. Buscar el ButtonGroup y el radio button seleccionado en este panel
                ButtonGroup grupo = null;
                JRadioButton seleccionado = null;

                for (Component panelComp : panelEnfrentamiento.getComponents()) {
                    if (panelComp instanceof JRadioButton radio) {
                        if (grupo == null) {
                            // Obtener el ButtonGroup asociado al primer radio button (todos comparten grupo)
                            grupo = (ButtonGroup) radio.getClientProperty("buttonGroup");
                        }
                        if (radio.isSelected()) {
                            seleccionado = radio;
                        }
                    }
                }

                // 4. Si hay una selección, guardar el resultado
                if (seleccionado != null) {
                    String resultado = seleccionado.getActionCommand(); // "EQUIPO1", "EQUIPO2", "EMPATE"
                    resultados.put(idEnfrentamiento, resultado);
                }
            }
        }

        // 5. Llamar al controlador para guardar en la BD
        if (!resultados.isEmpty()) {
            modeloController.guardarResultados(resultados);
        } else {
            throw new Exception("No se seleccionaron resultados para guardar.");
        }
    }
}