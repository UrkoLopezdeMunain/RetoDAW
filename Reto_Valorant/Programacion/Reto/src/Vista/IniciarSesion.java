package Vista;

import ModeloController.VistaController;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class IniciarSesion extends JFrame{
    private JPanel pPricipal;
    private JTextField tfUsuario;
    private JTextField tfPassword;
    private JButton aceptarButton;
    protected VistaController vistaController;

    public IniciarSesion() {
        setTitle("Iniciar Sesión");
        setContentPane(pPricipal);
        tfPassword.setVisible(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 220);
        setLocationRelativeTo(null);
        setResizable(false); //para que sea de posicion y tamaño fijo


        //crear Listeners


        tfUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e); //aui hara el la petiocion a BD para saber si existe el usuario
                if (vistaController.validarUsuario(tfUsuario.getText())){
                    tfPassword.setVisible(false);
                }
            }
        });
    }



    public JPanel getpPricipal() {
        return pPricipal;
    }

    public JTextField getTfUsuario() {
        return tfUsuario;
    }

    public JTextField getTfPassword() {
        return tfPassword;
    }

    public JButton getAceptarButton() {
        return aceptarButton;
    }

    public VistaController getVistaController() {
        return vistaController;
    }
}
