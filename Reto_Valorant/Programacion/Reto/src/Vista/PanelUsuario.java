package Vista;

import ModeloController.VistaController;

import javax.swing.*;
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
    private JMenuItem iGestionarEnfrentamientos;
    private JMenuItem iConsultarTodosEquipos;
    protected VistaController vistaController;

    //constructor ADMIN
    /**
     Todos los listeners MENOS el de Infromes seránmodales, ya qeu son mas 'volatiles' y tienen una informacion que se verá en otro sitio
     */
    public PanelUsuario(VistaController vistaController) {
        setTitle("Iniciar Sesión");
        setContentPane(pPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        setResizable(false); //para que sea de posicion y tamaño fijo

        this.vistaController = vistaController ;

        //crear Listeners
        iCrearEquipo.addActionListener(i -> vistaController.setCrearEquipo(vistaController));
        iConsultarEquipo.addActionListener(i -> vistaController.setConsultarEquipo(vistaController));
        iActualizarEquipo.addActionListener(i -> vistaController.setActualizarEquipo(vistaController));
        iBorrarEquipo.addActionListener(i -> vistaController.setBorrarEquipo(vistaController));
        iCrearJugador.addActionListener(i -> {
            try {
                vistaController.setCrearJugador(vistaController);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        iConsultarJugador.addActionListener(i -> vistaController.setConsultarJugador(vistaController));
        iActualizarJugador.addActionListener(i -> vistaController.setActualizarJugador(vistaController));


        iBorrarJugador.addActionListener(i -> vistaController.setBorrarJugador(vistaController));

        iGestionarEnfrentamientos.addActionListener(i -> vistaController.setGestionarEnfrentamientos(vistaController));
        bEmpezarComp.addActionListener(i -> {
            try{
                vistaController.empezarCompeticion();
                vistaController.crearJornadas();
                vistaController.crearEnfrentamiento();
                JOptionPane.showMessageDialog(pPrincipal, "Competicion cerrada correctamente");
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
            }
        });


        }

}
