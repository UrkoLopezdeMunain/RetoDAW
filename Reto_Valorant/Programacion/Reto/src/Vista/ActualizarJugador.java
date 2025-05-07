package Vista;

import ModeloController.VistaController;

import javax.swing.*;

public class ActualizarJugador extends JDialog{
    private JPanel pPrincipal;
    private JTextField tfApellido;
    private JTextField tfNacionalidad;
    private JTextField tfFecha;
    private JTextField tfSueldo;
    private JTextField tfRol;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JComboBox cbEquipos;
    private JTextField tfNicnkame;
    private JTextField tfNombre;

    public ActualizarJugador(VistaController vistaController) {
        setContentPane(pPrincipal);
        setModal(true);
        setSize(450,550);
        setLocationRelativeTo(pPrincipal.getRootPane());
        setResizable(false); //para que sea de posicion y tamaño fijo
    }
}
