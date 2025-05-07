package ModeloDAO;

import Modelo.Equipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    protected Connection con;
    protected String sql;
    public EquipoDAO(Connection c) {
        this.con = c;
    }

    public boolean crearEquipo(Equipo equipo) throws SQLException {
        sql = "INSERT INTO equipos(nombre,fecha_fundacion) VALUES(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, equipo.getNombre());
        ps.setDate(2, validarFecha(equipo.getFechaFundacion()));
        //antes de llegar a ps.executeUpdate si salta un error entonces devolerá false, en otro caso True
        return ps.executeUpdate() != 0;
    }

    public java.sql.Date validarFecha(LocalDate fechaFund) {
        return java.sql.Date.valueOf(fechaFund);
    }


    public Equipo validarEquipo(Equipo equipo) throws SQLException {
        sql = "SELECT cod_equipo,nombre,fecha_fundacion,puntuacion FROM equipos WHERE lower(nombre) = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, equipo.getNombre());
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
    public boolean borrarEquipo(Equipo equipo) throws SQLException {
        sql = "DELETE FROM equipos WHERE lower(nombre) = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, equipo.getNombre());
        return ps.executeUpdate() != 0;
        //lanzara trigger si tiene jugadores
    }

    public boolean actualizarFechaEquipo(Equipo equipo) throws SQLException {
        sql="UPDATE equipos SET fecha_fundacion=? WHERE lower(nombre)=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, equipo.getNombre());
        ps.setDate(2, validarFecha(equipo.getFechaFundacion()));
        return ps.executeUpdate() != 0;
    }


    public ArrayList<Equipo> obtenerTodosLosEquipos() throws Exception {
        ArrayList<Equipo> todosLosEquipos = new ArrayList<>();

        String sql = "SELECT * FROM equipos";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            LocalDate fechaFundacion = LocalDate.parse(rs.getString("fecha_fundacion"));

            Equipo equipo = new Equipo(
                    rs.getInt("cod_equipo"),
                    rs.getString("nombre"),
                    rs.getDate("fecha_fundacion").toLocalDate(),
                    rs.getInt("puntuacion")
            );

            todosLosEquipos.add(equipo);
        }

        rs.close();
        ps.close();

        return todosLosEquipos;
    }


}