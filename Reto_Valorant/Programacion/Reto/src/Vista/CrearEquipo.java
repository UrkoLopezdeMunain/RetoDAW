package Vista;

import ModeloController.JornadaController;
import ModeloController.VistaController;
import ModeloDAO.JugadorDAO;

import javax.swing.*;
import java.awt.event.*;

public class CrearEquipo extends JDialog {
    private JPanel pPrincipal;
    private JTextField tfFechaFund;
    private JTextField tfNombreEquipo;
    private JButton bAceptar;
    private JButton bCancelar;
    protected VistaController vistaController;
    public CrearEquipo(VistaController vistaController) {
        setContentPane(pPrincipal);
        setModal(true);
        setSize(400,450);
        getRootPane().setDefaultButton(bAceptar);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo
        this.vistaController = vistaController;

        //listeners
        tfNombreEquipo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    if (vistaController.validarEquipo(tfNombreEquipo.getText())){
                        bAceptar.setEnabled(false);
                        tfNombreEquipo.setText("");
                        throw new Exception("El equipo ya existe");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pPrincipal,ex);
                }
            }
        });
        bAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        bCancelar.addActionListener(new ActionListener() {
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
    }

    private void onOK() throws Exception {
            try {
                if (vistaController.crearEquipo(tfNombreEquipo.getText(),tfFechaFund.getText())){
                    JOptionPane.showMessageDialog(pPrincipal, "Equipo creada con exito");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}
