package ModeloDAO;

import Modelo.Enfrentamiento;
import Modelo.Equipo;

import java.util.ArrayList;

public class EnfrentamientoDAO {
    private static ArrayList<Enfrentamiento> enfrentamientos;

    public boolean anadirEnfrentamientos(Enfrentamiento en) {
        //para la comunicacion con Vista
        return false;
    }

    public ArrayList<Enfrentamiento> getEnfrentamientos() {
        return null;
    }

    public Enfrentamiento getEnfrentamientoPorId(int id) {
        return null;
    }
    public Enfrentamiento getEnfrentamientoPorEquipos(Equipo eq1,Equipo eq2){
        return null;
    }

    /*
        No hace falta nada mas por que segun ejercicio:
            Una vez se cierra periodo de inscripcion de equipos no se puede alterar los enfrentamientos.
    */


}
