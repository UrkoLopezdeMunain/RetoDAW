package Vista;

import Modelo.Jornada;
import ModeloController.VistaController;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class GestionarEnfrentamientos extends JDialog {
    private JPanel pPrincipal;
    private JButton bAceptar;
    private JTextArea taEnfrentamientos;
    private JComboBox cbJornada;
    private JComboBox cbEnfrentamientos;
    private JPanel pJornadas;
    private JPanel pEnfrentamientos;
    private JPanel pResultados;
    private JLabel lEq1;
    private JTextField tfEq1;
    private JTextField tfEq2;
    private JLabel lEq2;
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
        pEnfrentamientos.setVisible(false);
        pResultados.setVisible(false);

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
                List<String> enfrentamientos = vistaController.enfrentamientos(cbJornada.getSelectedItem().toString());
                for (String enfrentamiento : enfrentamientos){
                    cbEnfrentamientos.addItem(enfrentamiento);
                }
                pJornadas.setVisible(false);
                pEnfrentamientos.setVisible(true);

            } else if (pEnfrentamientos.isVisible()) {
                vistaController.enfrentamientoElegido(cbEnfrentamientos.getSelectedItem().toString());
                lEq1.setText(vistaController.equipo1());
                tfEq1.setText(String.valueOf(vistaController.equipo1Res()));
                lEq2.setText(vistaController.equipo2());
                tfEq2.setText(String.valueOf(vistaController.equipo2Res()));
                pEnfrentamientos.setVisible(false);
                pResultados.setVisible(true);
            }else {
                vistaController.guardarResultados(tfEq1.getText(),tfEq2.getText());
                JOptionPane.showMessageDialog(null,"Se ha guardado el resultado correctamente.");
                dispose();
            }
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
