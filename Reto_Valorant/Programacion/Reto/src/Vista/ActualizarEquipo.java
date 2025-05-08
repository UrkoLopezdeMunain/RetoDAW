package Vista;

import ModeloController.VistaController;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class ActualizarEquipo extends JDialog {
    private JPanel pPrincipal;
    private JTextField tfNombreNuevo;
    private JTextField tfFechaFundNueva;
    private JLabel lNombreNuevo;
    private JLabel lPuncuacionNueva;
    private JButton bAceptar;
    private JButton bCancelar;
    protected VistaController vistaController;

    public ActualizarEquipo(VistaController vistaController) {
        setTitle("Actualizar Equipo");
        setContentPane(pPrincipal);
        setModal(true);
        setSize(450,550);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo

        bCancelar.addActionListener(i -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });


        tfNombreNuevo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    if (vistaController.validarEquipo(tfNombreNuevo.getText())){
                        vistaController.rellenarCamposEquipoUpdate(pPrincipal);

                    }else {
                        throw new Exception("No existe ese equipo en la Base de datos");
                    }
                } catch (Exception ex) {
                    tfNombreNuevo.setText(""); tfFechaFundNueva.setText("");
                    JOptionPane.showMessageDialog(pPrincipal,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        bAceptar.addActionListener(i -> {
            try {
                onOk();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(pPrincipal,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        });
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    /**Utilizo ifs para ver si se ha modificado fecha,nombre o ambos, para hacer la programacion mas simle y dividirla en funciones mas pequñas*/



    private void onOk() throws Exception {
        // Validar que los campos no estén vacíos
        if (tfNombreNuevo.getText().isEmpty() || tfFechaFundNueva.getText().isEmpty()) {
            throw new Exception("Los campos no pueden estar vacíos.");
        }

        // Validar el formato de la fecha
        LocalDate nuevaFecha;
        try {
            nuevaFecha = vistaController.validarFecha(tfFechaFundNueva.getText());
        } catch (Exception e) {
            throw new Exception("Formato de fecha inválido. Use dd-MM-yyyy");
        }

        // Obtener datos actuales del equipo
        String nombreActual = vistaController.getEquipo().getNombre();
        LocalDate fechaActual = vistaController.getEquipo().getFechaFundacion();
        String nuevoNombre = tfNombreNuevo.getText();

        // Determinar qué campos han cambiado
        boolean cambioNombre = !nuevoNombre.equalsIgnoreCase(nombreActual);
        boolean cambioFecha = !nuevaFecha.equals(fechaActual);

        // Realizar la actualización correspondiente
        if (cambioNombre && cambioFecha) {
            if (vistaController.actualizarEquipoNombre(nuevoNombre, nuevaFecha)) {
                JOptionPane.showMessageDialog(pPrincipal, "Nombre y fecha actualizados correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (cambioNombre) {
            if (vistaController.actualizarEquipoNombre(nuevoNombre, fechaActual)) {
                JOptionPane.showMessageDialog(pPrincipal, "Nombre actualizado correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (cambioFecha) {
            if (vistaController.actualizarEquipoFecha(nombreActual, nuevaFecha)) {
                JOptionPane.showMessageDialog(pPrincipal, "Fecha actualizada correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(pPrincipal, "No se realizaron cambios", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }

        dispose();
    }

    public JTextField getTfNombreNuevo() {
        return tfNombreNuevo;
    }

    public JTextField getTfFechaFundNueva() {
        return tfFechaFundNueva;
    }
}
