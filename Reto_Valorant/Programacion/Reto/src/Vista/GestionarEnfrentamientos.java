package Vista;

import Modelo.Jornada;
import ModeloController.VistaController;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class GestionarEnfrentamientos extends JDialog {
    private JPanel pPrincipal;
    private JButton bAceptar;
    private JTextArea taEnfrentamientos;
    private JComboBox cbJornada;
    private VistaController vistaController;

    public GestionarEnfrentamientos(VistaController vistaController) {
        this.vistaController = vistaController;
        setTitle("Gestionar Enfrentamientos");
        setModal(true);
        getRootPane().setDefaultButton(bAceptar);
        setSize(450,550);
        setContentPane(pPrincipal);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo

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
            vistaController.guardarResultados(pPrincipal);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pPrincipal,"ERROR: " + e.getMessage());
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    public void rellenarConEquipos(){
        try {
            vistaController.rellenarCamposGestionarEnfrentamientos(pPrincipal);
        }catch (Exception e){
            JOptionPane.showMessageDialog(pPrincipal,"ERROR: " + e.getMessage());
        }
    }
    public void obtenerJornadas(){
        try {
            List<Jornada> jornadas = vistaController.obtenerJornadas();
            if(!jornadas.isEmpty()) {
                for (int i = 0; i < jornadas.size(); i++) {
                    cbJornada.addItem(vistaController.obtenerJornadas().get(i).getNumJornada() + "º JORNADA: ");
                }
            }else
                throw new Exception("No es posible obtener las jornadas");
        }catch (Exception e){
            JOptionPane.showMessageDialog(pPrincipal,"ERROR: " + e.getMessage());
        }
    }

    public JTextArea getTaEnfrentamientos() {
        return taEnfrentamientos;
    }

    public void setTaEnfrentamientos(JTextArea taEnfrentamientos) {
        this.taEnfrentamientos = taEnfrentamientos;
    }

    public JButton getbAceptar() {
        return bAceptar;
    }

    public void setbAceptar(JButton bAceptar) {
        this.bAceptar = bAceptar;
    }
}
