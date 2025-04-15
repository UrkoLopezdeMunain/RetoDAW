package ModeloDAO;

import Modelo.Jugador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JugadorDAO {

        private static ArrayList<Jugador> jugadores = new ArrayList<>();
        protected Connection con;
        private String sql;
        public JugadorDAO(Connection c) {
            this.con = c;
        }

        public ArrayList<Jugador> obtenerPorEquipo(int codEquipo) throws SQLException {
            sql = "SELECT cod_jugador,nombre,apellido,nacionalidad,fecha_nac,sueldo,nickname,rol FROM jugadores WHERE cod_equipo = ?";
            ArrayList<Jugador> jugadores = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(codEquipo));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Jugador j = new Jugador();
                j.setCodJugador(rs.getInt("cod_jugador"));
                j.setNombre(rs.getString("nombre"));
                j.setApellido(rs.getString("apellido"));
                j.setNacionalidad(rs.getString("nacionalidad"));
                j.setFechaNacimiento(rs.getDate("fecha_nac").toLocalDate());
                j.setSueldo(rs.getDouble("sueldo"));
                j.setNickname(rs.getString("nickname"));
                j.setRol(rs.getString("rol"));
                jugadores.add(j);
            }
            return jugadores;
        }

    public boolean crearJugador(String nombre, String apellido, String nacionalidad, String fechaNac, String sueldo, String nickName, Object nombreEquipo) throws SQLException {
        sql="INSERT INTO jugadores(nombre,apellido,nacionalidad,fecha_nac,sueldo,nickname,rol,cod_equipo) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, nacionalidad);
        ps.setString(4, fechaNac);
        ps.setString(5, sueldo);
        ps.setString(6, nickName);
        ps.setString(7, nombreEquipo.toString());
        return ps.executeUpdate() != 0;
        }
}
