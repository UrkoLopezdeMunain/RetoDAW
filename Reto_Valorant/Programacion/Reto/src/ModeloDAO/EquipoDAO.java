package ModeloDAO;

import Modelo.Equipo;
import Modelo.Jugador;

import java.util.ArrayList;

public class EquipoDAO {

    private static ArrayList<Equipo> listaEquipos = new ArrayList<>();

    public EquipoDAO() {}

    public void crearEquipo(Equipo e) {
        listaEquipos.add(e);
    }

    public ArrayList<Equipo> obtenerTodosLosEquipos() {
        return listaEquipos;
        //Devuelve un ArrayList nuevo para la seguridad de datos
    }

    public Equipo obtenerEquipoPorCodigo(int codEquipo) {
        return new Equipo();
    }
    public void actualizarEquipo(Equipo nuevoEquipo) {

    }
    //hechos asi para que no afecten a nada en especial, queda hacer las SELECT, UPDATE o DELETE
    public boolean eliminarEquipo(int codEquipo) {
        return false;
    }
    public boolean anadirJugador(Equipo eq, Jugador j) {
        return false;
    }
}