package Vista;

import ModeloController.VistaController;

import javax.swing.*;
import java.sql.SQLException;

public class PanelUsuario extends JFrame{
    private JPanel pPrincipal;
    private JButton bEmpezarComp;
    private JMenuItem iConsultarJugadoresPorEquipo;
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
    private JMenuItem iVerEnfrentamientos;
    private JMenuItem iVerTodosEquipos;
    private JMenuItem iVerJugadoresEquipo;
    protected VistaController vistaController;
    private String tipoUsuario;

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

        this.tipoUsuario = tipoUsuario;
        this.vistaController = vistaController;

        //crear Listeners
        try{
            quePoner(vistaController.estadoCompeticion());
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

        iCrearEquipo.addActionListener(i -> vistaController.setCrearEquipo(vistaController));
        iActualizarEquipo.addActionListener(i -> vistaController.setActualizarEquipo(vistaController));
        iConsultarEquipo.addActionListener(i -> vistaController.setConsultarEquipo(vistaController));
        iBorrarEquipo.addActionListener(i -> vistaController.setBorrarEquipo(vistaController));
        iCrearJugador.addActionListener(i -> {
            try {
                vistaController.setCrearJugador(vistaController);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
            }
        });
        iActualizarJugador.addActionListener(i -> vistaController.setActualizarJugador(vistaController));
        iBorrarJugador.addActionListener(i -> vistaController.setBorrarJugador(vistaController));
        iGestionarEnfrentamientos.addActionListener(i -> vistaController.setGestionarEnfrentamientos(vistaController));
        iVerEnfrentamientos.addActionListener(i -> vistaController.setVerEnfrentamientos(vistaController,tipoUsuario));
        iVerTodosEquipos.addActionListener(i -> vistaController.setVerTodosEquipos(vistaController));
        iConsultarJugadoresPorEquipo.addActionListener(i -> vistaController.setConsultarJugadoresPorEquipo(vistaController));
        bEmpezarComp.addActionListener(i -> {
            try{
                vistaController.empezarCompeticion();
                vistaController.crearJornadas();
                vistaController.crearEnfrentamiento();
                JOptionPane.showMessageDialog(pPrincipal, "Competicion cerrada correctamente");
                quePoner(vistaController.estadoCompeticion());
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
            }
        });


        }
        public void quePoner(char estado) {
            try {
                if (estado == 'a') {
                    if (tipoUsuario.equalsIgnoreCase("n")) {
                        JOptionPane.showMessageDialog(null, "ERROR: " +
                                "un usuario normal no puede entrar a la aplicación si la competición sigue" +
                                "abierta.");
                        System.exit(0);
                    }else {
                        iGestionarEnfrentamientos.setEnabled(false);
                    }
                }else{
                    bEmpezarComp.setEnabled(false);
                    iCrearEquipo.setEnabled(false);
                    iActualizarEquipo.setEnabled(false);
                    iBorrarEquipo.setEnabled(false);
                    iCrearJugador.setEnabled(false);
                    iActualizarJugador.setEnabled(false);
                    iBorrarJugador.setEnabled(false);
                    if (tipoUsuario.equalsIgnoreCase("n")) {
                        iGestionarEnfrentamientos.setEnabled(false);
                    }else{
                        iGestionarEnfrentamientos.setEnabled(true);
                    }
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
        }

}
