package Vista;

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
        setSize(450,550);
        getRootPane().setDefaultButton(bAceptar);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo
        this.vistaController = vistaController;

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
        if (vistaController.validarEquipo(tfNombreEquipo.getText())){ //mira que exista ese Equipo en la bd, en caso de no, pasa a la funcion crearEquipo, que lo manda a DAO directamente
            if (vistaController.crearEquipo(tfNombreEquipo.getText(),tfFechaFund.getText())){
                JOptionPane.showMessageDialog(pPrincipal, "Equipo creada con exito");
            }
        }else {
            throw new Exception("El equipo ya existe");
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}
