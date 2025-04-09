package ModeloDAO;

import Modelo.Equipo;
import Modelo.Jugador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EquipoDAO {

    protected Connection con;
    protected String sql;
    public EquipoDAO(Connection c) {
        this.con = c;
    }

    public boolean crearEquipo(String nombre, String fechaFund) throws SQLException {
        sql = "INSERT INTO equipo VALUES(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setDate(2, validarFecha(fechaFund));
        //antes de llegar a ps.executeUpdate si salta un error entonces devolerá false, en otro caso True
        return ps.executeUpdate() != 0;
    }


    public Equipo validarEquipo(String nombreEquipo) throws SQLException {
        sql = "SELECT * FROM equipos WHERE nombre = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombreEquipo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            throw new SQLException("El equipo ya existe");
        } else {
            return new Equipo(rs.getInt("cod_equipo"),
                    rs.getString("nombre"),
                    rs.getDate("fecha_fundacion").toLocalDate(),
                    rs.getInt("puntuacion")
            );
        }
    }

    public java.sql.Date validarFecha(String fechaFund) {
        return java.sql.Date.valueOf(fechaFund);
    }
}