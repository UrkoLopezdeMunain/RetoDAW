package Vista;

import Modelo.Jornada;
import ModeloController.VistaController;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class VerEnfrentamientos extends JDialog {
    private VistaController vistaController;
    private JPanel pPrincipal;
    private JButton bAceptar;
    private JComboBox cbJornada;
    private JPanel pEnfrentamientos;
    private JPanel pJornadas;
    private JButton buttonCancel;

    public VerEnfrentamientos(VistaController vistaController) {
        this.vistaController = vistaController;
        setTitle("Gestionar Enfrentamientos");
        setModal(true);
        getRootPane().setDefaultButton(bAceptar);
        setSize(450,550);
        setContentPane(pPrincipal);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo
        pEnfrentamientos.setVisible(false);

        bAceptar.addActionListener(e -> onOK());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        pPrincipal.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            if (pJornadas.isVisible()) {
                vistaController.enfrentamientosProcedimiento(cbJornada.getSelectedItem().toString(),pEnfrentamientos);
                pJornadas.setVisible(false);
                pEnfrentamientos.setVisible(true);
            } else
                dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(pPrincipal,"ERROR: " + e.getMessage());
            dispose();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(pPrincipal,"ERROR: " + e.getMessage());
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    /*
    public void rellenarConEquipos(){
        try {
            vistaController.rellenarCamposGestionarEnfrentamientos(pPrincipal);
        }catch (Exception e){
            JOptionPane.showMessageDialog(pPrincipal,"ERROR: " + e.getMessage());
        }
    }

     */
    public void obtenerJornadas(){
        try {
            for (Jornada jornada : vistaController.obtenerJornadas()){
                cbJornada.addItem(jornada.getNumJornada());
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(pPrincipal,"ERROR: " + e.getMessage());
        }
    }

    public JButton getbAceptar() {
        return bAceptar;
    }

    public void setbAceptar(JButton bAceptar) {
        this.bAceptar = bAceptar;
    }
}
