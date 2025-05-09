package ModeloController;

import Modelo.Enfrentamiento;
import Modelo.Equipo;
import Modelo.Jornada;
import ModeloDAO.EnfrentamientoDAO;
import ModeloDAO.EquipoDAO;
import ModeloDAO.JornadaDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;



 class EnfrentamientoController {
    private EnfrentamientoDAO enfrentamientoDAO;
    private ModeloController modeloController;
    private ArrayList<Enfrentamiento> enfrentamientos = new ArrayList<>();
    private ArrayList<Enfrentamiento> enfrentamientosMitad1 = new ArrayList<>();
    private List<Jornada> jornadas;
    private List<Equipo> equipos;

     
     
    /**
     * Constructor para iniciar  controlador de enfrentamientos.
     * @param enfrentamientoDAO 
     * @param modeloController 
     */

    public EnfrentamientoController(EnfrentamientoDAO enfrentamientoDAO, ModeloController modeloController) {
        this.enfrentamientoDAO = enfrentamientoDAO;
        this.modeloController = modeloController;
    }
    /**
    *Crea los enfrentamientos de las jornadas dividiendolos en dos partes
    *@throws Exception Si ocurre un error al crear los enfrentamientos.
    */
    public void crearEnfrentamientos() throws Exception{
            jornadas = modeloController.getJornadas();
            primeraMitad();
            segundaMitad();
    }

    /**
     * Crea los enfrentamientos para la primera mitad 
     * @throws SQLException Si ocurre un error en la base de datos
     */
    private void primeraMitad() throws SQLException {
        for (int p = 0; p < jornadas.size()/2; p++) {
            equipos = modeloController.getEquipos();
            hacerEnfrentamiento(p);
            for(Enfrentamiento enfrentamiento : enfrentamientosMitad1){
                equipos.add(enfrentamiento.getEquipo1());
                equipos.add(enfrentamiento.getEquipo2());
            }
        }
    }

    /**
     * En este los crea  para la segunda mitad 
     * @throws SQLException Si ocurre un error en la base de datos 
     */
    private void segundaMitad() throws SQLException {
        Random rand = new Random();
        for (int p = jornadas.size()/2; p < jornadas.size(); p++) {
            Enfrentamiento enfrentamiento = new Enfrentamiento();
            enfrentamiento.setHora(elegirHora());
            enfrentamiento.setJornada(jornadas.get(p));
            int enfre = rand.nextInt(enfrentamientosMitad1.size());
            enfrentamiento.setEquipo1(enfrentamientosMitad1.get(enfre).getEquipo2());
            enfrentamiento.setEquipo2(enfrentamientosMitad1.get(enfre).getEquipo1());
            enfrentamientosMitad1.remove(enfre);
            enfrentamientoDAO.anadirEnfrentamientos(enfrentamiento);
        }
    }

    /**
     *Asigna los enfrentamientos de la jornada
     * @param p para el principio de la jornada 
     * @throws SQLException Si ocurre un error en la base de datos 
     */
    private void hacerEnfrentamiento(int p) throws SQLException{
        for (int i = 0; i <= equipos.size()/2; i++) {
            Enfrentamiento enfrentamiento = new Enfrentamiento();
            enfrentamiento.setHora(elegirHora());
            enfrentamiento.setEquipo1(elegirEquipo(equipos));
            equipos.remove(enfrentamiento.getEquipo1());

            noSeHanEnfrentado(enfrentamiento);

            equipos.remove(enfrentamiento.getEquipo2());
            enfrentamiento.setJornada(jornadas.get(p));
            enfrentamientoDAO.anadirEnfrentamientos(enfrentamiento);
            enfrentamientosMitad1.add(enfrentamiento);
            enfrentamientos.add(enfrentamiento);
        }
    }

     
    /**
     * Verificar equipos no se hayan enfrentado previamente
     * @param enfrentamiento el enfrentamiento a verificar
     */
    private void noSeHanEnfrentado(Enfrentamiento enfrentamiento){
        boolean yes = false;
        do {
            enfrentamiento.setEquipo2(elegirEquipo(equipos));
            try{
                for (int j = 1; j <= enfrentamientos.size(); j++) {
                    for (int o = 0; o <= enfrentamientos.size(); o++)
                        if (o != j) {
                            if (enfrentamientos.get(j).getEquipo1() == enfrentamientos.get(o).getEquipo1()
                                    && enfrentamientos.get(j).getEquipo2() == enfrentamientos.get(o).getEquipo2()) {
                                yes = true;
                            } else {
                                o = enfrentamientos.size() + 1;
                                j = enfrentamientos.size() + 1;
                                yes = false;
                            }
                        }
                }
            }catch (Exception e){
                System.out.println();
            }
        }while (yes);
    }

     /**
     *Elegir hora aleatoria para el enfrentamient
     *
     * @return  hora elegida para el enfrentamiento.
     */
    private LocalTime elegirHora() {
        Random rand = new Random();
        int hora = rand.nextInt(15);
        return LocalTime.of(7, 0, 0).plusHours(hora);
    }

    /**
     *Eliger  equipo aleatorio de la lista
     * @param equipos La lista de equipos 
     * @return Un equipo aleatorio.
     */
    private Equipo elegirEquipo(List<Equipo> equipos) {
        Random rand = new Random();
        int eq1 = rand.nextInt(equipos.size());
        return equipos.get(eq1);
    }
    /*
    public void verEnfrentamientosJornada(){
        Integer[] nombres = jornadas.stream().map(Jornada::getNumJornada).toArray(Integer[]::new);
        do {
            int jornadaElegida = (Integer) JOptionPane.showInputDialog(null,
                    "Elija el numero de la jornada de la que quiere ver sus enfrentamientos",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    nombres,
                    nombres[0]
            );
            ArrayList<Enfrentamiento> en = new ArrayList<>();
            enfrentamientos.stream().filter(enfrentamiento -> enfrentamiento.getJornada().getNumJornada() == jornadaElegida).forEach(en::add);

            for (Enfrentamiento enfrentamiento : en) {
                Object[] options = { "OK", "CANCEL"};
                JOptionPane.showOptionDialog(null, enfrentamiento.toString(), "Continuar",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
            }
        }while (JOptionPane.showConfirmDialog(null,"quiere continuar viendo enfrentamientos por jornada?") == 0);
    }
    public void verEnfrentamientosEquipo(){
        String[] nombres = equipos.stream().map(Equipo::getNombre).toArray(String[]::new);
        do {
            String equipoElegido = (String) JOptionPane.showInputDialog(null,
                    "Elija el numero de la jornada de la que quiere ver sus enfrentamientos",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    nombres,
                    nombres[0]
            );
            if (equipoElegido == null || equipoElegido.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,"El numero no puede ser nulo");
            }else {
                ArrayList<Enfrentamiento> en = new ArrayList<>();
                enfrentamientos.stream().filter(enfrentamiento -> enfrentamiento.getEquipo1().getNombre().equals(equipoElegido) || enfrentamiento.getEquipo2().getNombre().equals(equipoElegido)).forEach(en::add);

                for (Enfrentamiento enfrentamiento : en) {
                    Object[] options = { "OK", "CANCEL"};
                    JOptionPane.showOptionDialog(null, enfrentamiento.toString(), "Continuar",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, options[0]);
                }
            }
        }while (JOptionPane.showConfirmDialog(null,"quiere continuar viendo enfrentamientos por equipo?") == 0);
    }
    */
    private void ponerResultados(int resultadoEq1, int resultadoEq2) throws Exception{
        if (resultadoEq1 == resultadoEq2) {
            throw new Exception("Los equipos no pueden empatar");
        }else if (resultadoEq1 > 13 || resultadoEq2 > 13) {
            if (resultadoEq1 - resultadoEq2 != 2 && resultadoEq2 - resultadoEq1 != 2) {
                throw new Exception("Si los equipos han hecho mas de 12 rondas, tiene que haber diferencia de 2 rondas ganadas entre ellos.");
            }
        }
    }
    public List<Enfrentamiento> getEnfrentamientos() throws Exception{
        return enfrentamientoDAO.getEnfrentamientos();
    }
    public Jornada getJornadaPorId(int id) throws SQLException{
        return modeloController.getJornadaPorId(id);
    }
    public void actualizarResultado(Enfrentamiento enfrentamiento) throws Exception{
        ponerResultados(enfrentamiento.getResultadosEq1(), enfrentamiento.getResultadosEq2());
        enfrentamientoDAO.actualizarEnfrentamiento(enfrentamiento);
    }
    public List<Enfrentamiento> enfrentamientos(int j) throws Exception {
        return enfrentamientoDAO.getEnfrentamientoPorJornada(j);
    }
    public Equipo getEquipoPorId(int id) throws Exception{
        return modeloController.getEquipoPorId(id);
    }
}
