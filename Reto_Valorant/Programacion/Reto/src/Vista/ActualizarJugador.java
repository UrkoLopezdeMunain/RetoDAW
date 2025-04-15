package Vista;

import ModeloController.VistaController;

import javax.swing.*;

public class ActualizarJugador extends JDialog{
    private JPanel pPrincipal;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton aceptarButton;
    private JButton cancelarButton;

    public ActualizarJugador(VistaController vistaController) {
        setContentPane(pPrincipal);
        setModal(true);
        setSize(450,550);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo
    }
}
