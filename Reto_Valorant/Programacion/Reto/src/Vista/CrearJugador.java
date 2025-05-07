package Vista;

import Modelo.Equipo;
import ModeloController.VistaController;
import Nacionalidades.Country;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.Arrays;

public class CrearJugador extends  JDialog{
    private JTextField tfNombreJugador;
    private JTextField tfApellidoJugador;
    private JButton bAceptar;
    private JButton bCancelar;
    private JTextField tfFechaNaci;
    private JTextField tfSueldo;
    private JTextField tfRol;
    private JPanel pPrincipal;
    private JComboBox cbPaises;
    private JComboBox cbEquiposDisp;
    private JTextField tfNickName;

    private JLabel lNombreJugador;
    private JLabel lApellidoJugador;
    private JLabel lFechaNaci;
    private JLabel lSueldo;
    private JLabel lRol;
    private JLabel lEquipo;
    private JLabel lNacionalidad;

    private final VistaController vistaController;

    /**
     en el bAcepat, obtengo el codigo OSI del pais seleccionaldo de la ComboBox, para que se siga un Standar(GER,ESP,etc...)
     */
    public CrearJugador(VistaController vistaController) throws SQLException {
        this.vistaController = vistaController;
        nacionalidades();
        equiposDisp();

        setContentPane(pPrincipal);
        setModal(true);
        setSize(450,550);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo
        //listeners
        tfNombreJugador.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    if (vistaController.validarNomYAp(tfNombreJugador.getText())) {
                        tfNombreJugador.requestFocus();
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });

        tfApellidoJugador.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    if (vistaController.validarNomYAp(tfApellidoJugador.getText())) {
                        tfApellidoJugador.requestFocus();
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(pPrincipal,ex.getMessage());
                }
            }
        });


        tfFechaNaci.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    if (!vistaController.validarFechaNac(tfFechaNaci.getText())) {
                        tfFechaNaci.requestFocus();
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });
        tfSueldo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!vistaController.validarSueldo(tfSueldo.getText())) {
                    tfSueldo.requestFocus();
                    JOptionPane.showMessageDialog(pPrincipal,"EL sueldo debe de ser como minimo de 1184");
                }
            }
        });
        tfNickName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    if (!vistaController.validarNik(tfSueldo.getText())) {
                        tfNickName.requestFocus();
                        JOptionPane.showMessageDialog(pPrincipal,"NickName no soportado");
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });

        bAceptar.addActionListener(i -> {
            try {
                if (!vistaController.crearJugador(tfNombreJugador.getText().toLowerCase(),
                        tfApellidoJugador.getText().toLowerCase(),obtenerCod3(),tfFechaNaci.getText(),
                        tfSueldo.getText(),tfRol.getText().toLowerCase(),tfNickName.getText().toLowerCase(),
                        obtenerEquipo())){
                  throw new Exception("Algo ha ido mal con la creacion del jugador");
                }
                JOptionPane.showMessageDialog(pPrincipal,"Jugador creado con éxito!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(pPrincipal,"ERROR: " + ex.getMessage());
            }
        });
        bCancelar.addActionListener(i-> dispose());
    }

    private void nacionalidades(){
        //cbPaises.addItem(Country.values()); ¿? porbar
        for (Country c : Country.values()) cbPaises.addItem(c.getName());
    }
    private void equiposDisp() throws SQLException {
        for (Equipo eq : vistaController.getEquipos()) cbEquiposDisp.addItem(eq.getNombre());
    }

    private String obtenerCod3() {
        Object selectedItem = cbPaises.getSelectedItem();
        if (selectedItem == null) return "No seleccionado";

        String nombrePais = selectedItem.toString();
        return Arrays.stream(Country.values())
                .filter(e -> e.getName().equalsIgnoreCase(nombrePais))
                .map(Country::getThreeDigitsCode)
                .findFirst()
                .orElse("No encontrado");
    }

    /**
     * No hace falta el Object.requireNonNull ya que el stream si no devuelve null, a lo que el sql lanza excepcion si algo va mal
     * */
    private Equipo obtenerEquipo() throws SQLException {

        return vistaController.getEquipos().stream()
                .filter(e -> e.getNombre().equals(cbEquiposDisp.getSelectedItem()))
                .findFirst()
                .orElse(null);
    }
}

