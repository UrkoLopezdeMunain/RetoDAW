package ModeloDAO;

import Modelo.Equipo;
import Modelo.Jugador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JugadorDAO {

        private static final ArrayList<Jugador> jugadores = new ArrayList<>();
        protected Connection con;
        private String sql;
        public JugadorDAO(Connection c) {
            this.con = c;
        }

        public ArrayList<Jugador> obtenerPorEquipo(Equipo equipo) throws SQLException {
            sql = "SELECT * FROM jugadores WHERE cod_equipo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(equipo));
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

    public boolean crearJugador(Jugador jugador) throws SQLException {
        sql="INSERT INTO jugadores(nombre,apellido,nacionalidad,fecha_nac,sueldo,nickname,rol,cod_equipo) VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, jugador.getNombre());
        ps.setString(2, jugador.getApellido());
        ps.setString(3, jugador.getNacionalidad());
        ps.setString(4, jugador.getFechaNacimiento().toString());
        ps.setString(5, String.valueOf(jugador.getSueldo()));
        ps.setString(6, jugador.getNickname());
        ps.setString(7, jugador.getRol());
        ps.setInt(8, jugador.getEquipo().getCodEquipo());

        return ps.executeUpdate() != 0;
    }
    public boolean borrarJugador(Jugador jugador) throws SQLException {
            sql="DELETE FROM jugadores WHERE lower(nickname) = lower(?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, jugador.getNickname());
            return ps.executeUpdate() != 0;
    }
    public Jugador obtenerJugador(Jugador jugador)throws SQLException {
        sql="SELECT * FROM jugadores WHERE lower(nickname) = lower(?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, jugador.getNickname());
        ResultSet rs = ps.executeQuery();
        Jugador j = new Jugador();
        if (rs.next()) {
            j.setCodJugador(rs.getInt("cod_jugador"));
            j.setNombre(rs.getString("nombre"));
            j.setApellido(rs.getString("apellido"));
            j.setNacionalidad(rs.getString("nacionalidad"));
            j.setFechaNacimiento(rs.getDate("fecha_nac").toLocalDate());
            j.setSueldo(rs.getDouble("sueldo"));
            j.setNickname(rs.getString("nickname"));
            j.setRol(rs.getString("rol"));
        }
        return j;
    }
}
