package ModeloDAO;

import Modelo.Enfrentamiento;
import Modelo.Equipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnfrentamientoDAO {
    private static ArrayList<Enfrentamiento> enfrentamientos;
    protected Connection con;
    protected String sql;
    public EnfrentamientoDAO(Connection c) {
        this.con = c;
    }

    public boolean anadirEnfrentamientos(Enfrentamiento en) throws SQLException {
        sql = "INSERT INTO enfrentamientos(nombre,fecha_fundacion) VALUES(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, en.getIdEnfrentamiento());
        ps.setDate(2, en.getResultadosEq1());
        //antes de llegar a ps.executeUpdate si salta un error entonces devolerá false, en otro caso True
        return ps.executeUpdate() != 0;
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



}
