package Vista;

import ModeloController.VistaController;

import javax.swing.*;
import java.awt.event.*;

public class ConsultarEquipo extends JDialog {
    private JPanel pPrincipal;
    private JTextField tfNombreEquipo;
    private JTextField tfFechaFundacion;
    private JTextField tfPuntuacionTotal;
    private JTextArea taJugadores;
    private JButton bAtras;
    private VistaController vistaController;

    public ConsultarEquipo(VistaController vistaController) {
        setContentPane(pPrincipal);
        setModal(true);
        setSize(400,450);
        getRootPane().setDefaultButton(bAtras);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo

        tfFechaFundacion.setEditable(false);
        tfPuntuacionTotal.setEditable(false);
        taJugadores.setEditable(false);

        this.vistaController = vistaController;

        bAtras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        pPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        tfNombreEquipo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    if (vistaController.validarEquipo(tfNombreEquipo.getText())){
                        //rellenar los demas campo
                        vistaController.rellenarCamposEquipo(pPrincipal);
                    }else {
                        JOptionPane.showMessageDialog(null, "El nombre del equipo no existe");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public JTextField getTfNombreEquipo() {
        return tfNombreEquipo;
    }

    public JTextField getTfFechaFundacion() {
        return tfFechaFundacion;
    }

    public JTextField getTfPuntuacionTotal() {
        return tfPuntuacionTotal;
    }

    public JTextArea getTaJugadores() {
        return taJugadores;
    }
}
