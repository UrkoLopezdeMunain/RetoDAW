package Vista;

import ModeloController.VistaController;

import javax.swing.*;

public class BorrarEquipo extends JDialog{
    private JPanel pPrincipal;
    private JButton bCancelar;
    private JButton bAceptar;
    private JTextField tfNombre;
    private JTextField tfFechaFund;
    private JLabel lFechaFund;
    private JLabel lNombre;

    public BorrarEquipo(VistaController vistaController) {
        setContentPane(pPrincipal);
        setModal(true);
        setSize(450,550);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo
    }
}
