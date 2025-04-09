import ModeloController.*;
import ModeloDAO.JuegoDAO;

import javax.swing.*;

public class Main {
    public static JugadorController jugadorController;
    public static EquipoController equipoController;
    public static JornadaController jornadaController;
    public static CompeticionController competicionController;
    public static EnfrentamientoController enfrentamientoController;
    public static String[] opcionesSinJornadas;
    public static String[] opcionesConJornadas;

    public static void main(String[] args) {
        declararVariables();
        opcionesSinJornadas();
        opcionesConJornadas();
    }

    public static void declararVariables() {
        jugadorController = new JugadorController();
        equipoController = new EquipoController();
        jornadaController = new JornadaController();
        competicionController = new CompeticionController();
        enfrentamientoController = new EnfrentamientoController();


        opcionesSinJornadas = new String[]{
                "01. Crear un Jugador",
                "02. Crear un Equipo",
                "03. Modificar un Jugador",
                "04. Modificar un Equipo",
                "05. Eliminar un Jugador",
                "06. Eliminar un Equipo",
                "07. Ver todos los jugadores",
                "08. Ver todos los equipos",
                "09. Ver informacion de un jugador en concreto",
                "10. Ver informacion de un equipo en concreto",
                "11. Ver los jugadores de un equipo",
                "12. Crear las jornadas"
        };

        opcionesConJornadas = new String[]{
                "01. Modificar un Jugador",
                "02. Modificar un Equipo",
                "03. Ver todos los jugadores",
                "04. Ver todos los equipos",
                "05. Ver informacion de un jugador en concreto",
                "06. Ver informacion de un equipo en concreto",
                "07. Ver los jugadores de un equipo",
                "08. Ver los enfrentamientos de una jornada",
                "09. Ver los enfrentamientos de un equipo",
                "10. Añadir un resultado a un enfrentamiento",
                "11. Ver la puntuacion de un equipo"
        };
    }

    public static boolean validarCrearCompeticion(){
        boolean yes = true;
        JuegoDAO juegoDAO = new JuegoDAO();
        if (juegoDAO.obtenerTodosJuegos().isEmpty())
            JOptionPane.showMessageDialog(null, "¡No puedes continuar porque no hay ningun juego!",
                    "No hay juegos", JOptionPane.WARNING_MESSAGE);
        else
            yes = false;
        return yes;
    }

    public static void opcionesSinJornadas() {
        boolean yes = true;
        do {
            try {
                String o = (String) JOptionPane.showInputDialog(null, "Elige las opciones", "",
                        JOptionPane.PLAIN_MESSAGE, null, opcionesSinJornadas, opcionesSinJornadas[0]);
                if (o == null) return;
                int opcion = Integer.parseInt(o.substring(0, 2));

                switch (opcion) {
                    case 1 -> jugadorController.dataValidation();
                    case 2 -> equipoController.validarDatosEquipo();
                    case 3 -> jugadorController.modificarJugador();
                    case 4 -> equipoController.modificarEquipo();
                    case 5 -> jugadorController.eliminarJugador();
                    case 6 -> equipoController.eliminarEquipo();
                    case 7 -> jugadorController.verTodosJugadores();
                    case 8 -> equipoController.verTodosEquipos();
                    case 9 -> jugadorController.verPorNombre();
                    case 10 -> equipoController.verPorNombre();
                    case 11 -> equipoController.verJugadores();
                    case 12 -> {yes = jornadaController.validarCreacionJornada();
                        if (!yes) enfrentamientoController.crearEnfrentamientos();}
                    default -> JOptionPane.showMessageDialog(null, "Opción no permitida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (yes);
    }

    public static void opcionesConJornadas() {
        boolean yes = true;
        do {
            try {
                String o = (String) JOptionPane.showInputDialog(null, "Elige las opciones", "",
                        JOptionPane.PLAIN_MESSAGE, null, opcionesConJornadas, opcionesConJornadas[0]);
                if (o == null) return;
                int opcion = Integer.parseInt(o.substring(0, 2));

                switch (opcion) {
                    case 1 -> jugadorController.modificarJugador();
                    case 2 -> equipoController.modificarEquipo();
                    case 3 -> jugadorController.verTodosJugadores();
                    case 4 -> equipoController.verTodosEquipos();
                    case 5 -> jugadorController.verPorNombre();
                    case 6 -> equipoController.verPorNombre();
                    case 7 -> equipoController.verJugadores();
                    case 8 -> enfrentamientoController.verEnfrentamientosJornada();
                    case 9 -> enfrentamientoController.verEnfrentamientosEquipo();
                    case 10 -> enfrentamientoController.anadirResultado();
                    case 11 -> equipoController.verPuntuacionEquipo();
                    default -> yes = false;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (yes);
    }
}
