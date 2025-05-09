package Vista;

import Modelo.Equipo;
import Modelo.Jugador;
import ModeloController.VistaController;
import ModeloController.JugadorController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class ActualizarJugador extends JDialog {
    private JPanel pPrincipal;
    private JTextField tfNickName;
    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfNacionalidad;
    private JTextField tfFecha;
    private JTextField tfSueldo;
    private JTextField tfRol;
    private JComboBox<Equipo> cbEquipos;
    private JButton bAceptar;
    private JButton bCancelar;
    private Jugador jugador;
    private JugadorController jugadorController;


    protected VistaController vistaController;

    public ActualizarJugador(VistaController vistaController) {
        this.vistaController = vistaController;

        setTitle("Actualizar Jugador");
        setContentPane(pPrincipal);
        setModal(true);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        bCancelar.addActionListener(e -> onCancel());

        // Cargar equipos al iniciar
        try {
            vistaController.cargarEquiposEnComboBox(cbEquipos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tfNickName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    if (vistaController.validarJugador(tfNickName.getText())) {
                        vistaController.rellenarCamposJugadorUpdate(pPrincipal);
                    } else {
                        throw new Exception("No existe un jugador con ese nickname.");
                    }
                } catch (Exception ex) {
                    tfNombre.setText(""); tfApellido.setText(""); tfNacionalidad.setText("");
                    tfFecha.setText(""); tfSueldo.setText(""); tfRol.setText("");
                    JOptionPane.showMessageDialog(pPrincipal, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        bAceptar.addActionListener(e -> {
            try {
                onOk();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(pPrincipal, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }


    public boolean validarJugador(String nickname) throws SQLException {
        Jugador j = jugadorController.obtenerJugadorPorNickname(nickname);
        if (j != null && j.getNombre() != null) {
            this.jugador = j;
            return true;
        }
        return false;
    }

    private void onOk() throws Exception {
        if (camposVacios()) {
            throw new Exception("Los campos no pueden estar vacíos.");
        }

        LocalDate fechaNac;
        try {
            fechaNac = vistaController.validarFecha(tfFecha.getText());
        } catch (Exception e) {
            throw new Exception("Formato de fecha inválido. Use dd-MM-yyyy");
        }

        double sueldo;
        try {
            sueldo = Double.parseDouble(tfSueldo.getText());
        } catch (NumberFormatException e) {
            throw new Exception("Sueldo inválido. Debe ser numérico.");
        }

        Jugador j = vistaController.getJugador();
        j.setNombre(tfNombre.getText());
        j.setApellido(tfApellido.getText());
        j.setNacionalidad(tfNacionalidad.getText());
        j.setFechaNacimiento(fechaNac);
        j.setSueldo(sueldo);
        j.setRol(tfRol.getText());
        j.setEquipo((Equipo) cbEquipos.getSelectedItem());

        if (vistaController.actualizarJugador(j)) {
            JOptionPane.showMessageDialog(pPrincipal, "Jugador actualizado correctamente.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(pPrincipal, "No se realizaron cambios.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void limpiarCampos() {
        tfNombre.setText("");
        tfApellido.setText("");
        tfNacionalidad.setText("");
        tfFecha.setText("");
        tfSueldo.setText("");
        tfRol.setText("");
        cbEquipos.setSelectedIndex(-1);
    }

    private boolean camposVacios() {
        return tfNickName.getText().isEmpty() ||
                tfNombre.getText().isEmpty() ||
                tfApellido.getText().isEmpty() ||
                tfNacionalidad.getText().isEmpty() ||
                tfFecha.getText().isEmpty() ||
                tfSueldo.getText().isEmpty() ||
                tfRol.getText().isEmpty();
    }

    public JTextField getTfNickname() { return tfNickName; }
    public JTextField getTfNombre() { return tfNombre; }
    public JTextField getTfApellido() { return tfApellido; }
    public JTextField getTfNacionalidad() { return tfNacionalidad; }
    public JTextField getTfFechaNacimiento() { return tfFecha; }
    public JTextField getTfSueldo() { return tfSueldo; }
    public JTextField getTfRol() { return tfRol; }
    public JComboBox<Equipo> getCbEquipos() { return cbEquipos; }
}
