package ModeloDAO;

import Modelo.Enfrentamiento;
import Modelo.Equipo;

import java.sql.Connection;
import java.util.ArrayList;

public class EnfrentamientoDAO {
    private static ArrayList<Enfrentamiento> enfrentamientos;
    protected Connection con;
    public EnfrentamientoDAO(Connection c) {
        this.con = c;
    }

    public void anadirEnfrentamientos(Enfrentamiento en) {

    }

    public ArrayList<Enfrentamiento> getEnfrentamientos() {
        return null;
    }

    public Enfrentamiento getEnfrentamientoPorJornada(int numJornada){
        return null;
    }



}
