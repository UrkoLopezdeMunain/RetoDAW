package Vista;

import Modelo.Equipo;
import ModeloController.VistaController;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class ConsultarJugadoresPorEquipo extends JDialog {
    private VistaController vistaController;
    private JPanel pPrincipal;
    private JButton bAceptar;
    private JComboBox cbEquipos;
    private JPanel pJugadores;
    private JPanel pEquipos;

    public ConsultarJugadoresPorEquipo(VistaController vistaController) {
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
            if (pEquipos.isVisible()) {
                List<String> jugadores = vistaController.jugadores(cbEquipos.getSelectedItem().toString(),pJugadores);
                for (String jugador : jugadores) {
                    JLabel label = new JLabel(jugador);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    JPanel panel = new JPanel();
                    panel.add(label);
                    pJugadores.add(panel);
                }
                pEquipos.setVisible(false);
                pJugadores.setVisible(true);

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
    public void obtenerEquipos(){
        try {
            for (Equipo equipo : vistaController.getEquipos()) {
                cbEquipos.addItem(equipo.getNombre());
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
