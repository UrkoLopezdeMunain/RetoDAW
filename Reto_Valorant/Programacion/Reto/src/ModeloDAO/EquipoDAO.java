package ModeloDAO;

import Modelo.Equipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    protected Connection con;
    protected String sql;

    public EquipoDAO(Connection c) {
        this.con = c;
    }

    public boolean crearEquipo(String nombre,String fechaFund) throws SQLException {
        sql = "INSERT INTO equipos(nombre,fecha_fundacion) VALUES(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setDate(2, validarFecha(fechaFund));
        //antes de llegar a ps.executeUpdate si salta un error entonces devolerá false, en otro caso True
        return ps.executeUpdate() != 0;
    }

    public Equipo validarEquipo(String nombreEquipo) throws SQLException {
        sql = "SELECT cod_equipo,nombre,fecha_fundacion,puntuacion FROM equipos WHERE lower(nombre) = ?";
        Equipo equipo = new Equipo();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombreEquipo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            equipo.setCodEquipo(rs.getInt("cod_equipo"));
            equipo.setNombre(rs.getString("nombre"));
            equipo.setFechaFundacion(rs.getDate("fecha_fundacion").toLocalDate());
            equipo.setPuntuacion(rs.getInt("puntuacion"));
            return equipo;
        } else {
            return null; //lo que interesa por que es boolean, para que se pase a false
        }
    }
    public boolean borrarEquipo(String nombreEquipo) throws SQLException {
        sql = "DELETE FROM equipos WHERE lower(nombre) = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombreEquipo);
        return ps.executeUpdate() != 0;
        //lanzara trigger si tiene jugadores
    }

    public boolean actualizarFechaEquipo(String nombre,String fechaFund) throws SQLException {
        sql="UPDATE equipos SET fecha_fundacion=? WHERE lower(nombre)=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setDate(2, validarFecha(fechaFund));
        return ps.executeUpdate() != 0;
    }

    public List<Equipo> getEquipos() throws SQLException {
        sql="SELECT * FROM equipos";
        List<Equipo> equipos = new ArrayList<Equipo>();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Equipo equipo = new Equipo();
            equipo.setCodEquipo(rs.getInt("cod_equipo"));
            equipo.setNombre(rs.getString("nombre"));
            equipo.setFechaFundacion(rs.getDate("fecha_fundacion").toLocalDate());
            equipo.setPuntuacion(rs.getInt("puntuacion"));
            equipos.add(equipo);
        }
        return equipos;
    }

    public java.sql.Date validarFecha(String fechaFund) {
        return java.sql.Date.valueOf(fechaFund);
    }
}