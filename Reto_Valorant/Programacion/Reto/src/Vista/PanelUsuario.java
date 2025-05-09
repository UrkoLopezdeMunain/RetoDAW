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
    public PanelUsuario(VistaController vistaController, String tipoUsuario) {
        setTitle("Iniciar Sesión");
        setContentPane(pPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        setResizable(false); //para que sea de posicion y tamaño fijo

        this.vistaController = vistaController ;

        //crear Listeners
        try {
            if (tipoUsuario.equalsIgnoreCase("n") && vistaController.estadoCompeticion() == 'a') {
                JOptionPane.showMessageDialog(null, "ERROR: " +
                        "un usuario normal no puede entrar a la aplicación si la competición sigue" +
                        "abierta.");
                System.exit(0);
            }else if (tipoUsuario.equalsIgnoreCase("n") && vistaController.estadoCompeticion() == 'c'){

            }else {

            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }iCrearEquipo.addActionListener(i -> vistaController.setCrearEquipo(vistaController));
        iActualizarEquipo.addActionListener(i -> vistaController.setActualizarEquipo(vistaController));
        iConsultarEquipo.addActionListener(i -> vistaController.setConsultarEquipo(vistaController));
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
                iCrearEquipo.setEnabled(false);
                iActualizarEquipo.setEnabled(false);
                iBorrarEquipo.setEnabled(false);
                iCrearJugador.setEnabled(false);
                iActualizarJugador.setEnabled(false);
                iBorrarJugador.setEnabled(false);
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
            }
        });


        }

}
