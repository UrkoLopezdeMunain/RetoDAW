package Vista;

import ModeloController.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class PanelUsuario extends JFrame{
    private JPanel pPrincipal;
    private JButton bEmpezarComp;
    private JMenuItem iCrearEquipo;
    private JMenuItem iConsultarEquipo;
    private JMenuItem iActualizarEquipo;
    private JMenuItem iBorrarEquipo;
    private JMenuItem iCrearJugador;
    private JMenuItem iConsultarJugador;
    private JMenuItem iActualizarJugador;
    private JMenuItem iBorrarJugador;
    private JMenu mInformes;
    protected VistaController vistaController;

    //constructor ADMIN
    public PanelUsuario(VistaController vistaController) {
        setTitle("Iniciar Sesión");
        setContentPane(pPrincipal);
        bEmpezarComp.setVisible(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        setResizable(false); //para que sea de posicion y tamaño fijo

        this.vistaController = vistaController ;

        /**
         Todos los listeners MENOS el de Infromes seránmodales, ya qeu son mas 'volatiles' y tienen una informacion que se verá en otro sitio
         */

        //crear Listeners
        iCrearEquipo.addActionListener(e -> {
            vistaController.setCrearEquipo(vistaController);
        });
        iConsultarEquipo.addActionListener(e -> {
            vistaController.setConsultarEquipo(vistaController);
        });
        iActualizarEquipo.addActionListener(e -> {
            vistaController.setActualizarEquipo(vistaController);
        });
        iBorrarEquipo.addActionListener(e -> {
            vistaController.setBorrarEquipo(vistaController);
        });
        iCrearJugador.addActionListener(e -> {
            //vistaController.setCrearJugador(this);
        });
        iConsultarJugador.addActionListener(e -> {
            //vistaController.setConsultarJugador(this);
        });
        iActualizarJugador.addActionListener(e -> {
            //vistaController.setActualizarJugador(this);
        });
        iBorrarJugador.addActionListener(e -> {
            //vistaController.setBorrarJugador(this);
        });

        mInformes.addActionListener(e -> {
              //vistaController.setInformes(this);
        });

        bEmpezarComp.addActionListener(e -> {
            // Empezar competición
            try {
                if (vistaController.iniciarCompeticion()) {
                    JOptionPane.showMessageDialog(this, "Competición iniciada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al iniciar la competición.");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
    /*
    public PanelUsuario(VistaController vistaController) {
        setTitle("Iniciar Sesión");
        setContentPane(pPrincipal);
        bEmpezarComp.setVisible(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 220);
        setLocationRelativeTo(null);
        setResizable(false); //para que sea de posicion y tamaño fijo

        this.vistaController = vistaController ;
        //listeners pertinentes a la vista
        mInformes.addActionListener(e -> {
            // Generar informes);
        });
        getJMenuBar().setVisible(false);
    }

     */

}
