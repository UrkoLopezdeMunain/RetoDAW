package Vista;

import ModeloController.VistaController;

import javax.swing.*;

public class ConsultarJugador extends JDialog {
    private JPanel pPrincipal;
    private JButton bAceptar;
    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfNacionalidad;
    private JTextField tfFechaNaci;
    private JTextField tfSueldo;
    private JTextField tfRol;
    private JTextField tfEquipo;
    private JLabel lNombre;
    private JLabel lApellido;
    private JLabel lNacionalidad;
    private JLabel lFechaNaci;
    private JLabel lSueldo;
    private JLabel lRol;
    private JLabel lEquipo;

    public ConsultarJugador(VistaController vistaController) {
        setContentPane(pPrincipal);
        setModal(true);
        setSize(450,550);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo
    }
}
