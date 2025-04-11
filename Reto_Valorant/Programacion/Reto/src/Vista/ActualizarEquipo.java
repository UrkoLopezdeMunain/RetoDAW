package Vista;

import ModeloController.VistaController;

import javax.swing.*;

public class ActualizarEquipo extends JDialog {
    private JPanel pPrincipal;
    private JTextField tfNombreActual;
    private JTextField tfNombreNuevo;
    private JTextField tfPuntuacionNueva;
    private JButton bAceptar;
    private JButton bCancelar;
    private JLabel lTitulo;
    private JLabel lNombreActual;
    private JLabel lNombreNuevo;
    private JLabel lPuncuacionNueva;

    public ActualizarEquipo(VistaController vistaController) {
        setContentPane(pPrincipal);
        setModal(true);
        setSize(450,550);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo
    }
}
