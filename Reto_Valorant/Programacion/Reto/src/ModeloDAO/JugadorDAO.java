package ModeloDAO;

import Modelo.Jugador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

        private static ArrayList<Jugador> jugadores = new ArrayList<>();
        protected Connection con;
        private String sql;
        public JugadorDAO(Connection c) {
            this.con = c;
        }


        public void agregar(Jugador jugador) {
            jugadores.add(jugador);
        }


        public ArrayList<Jugador> obtenerTodos(){
            return new ArrayList<>(jugadores);
        }

        public void modificar(int codJugador, Jugador nuevoJugador) {
            jugadores.replaceAll(j -> j.getCodJugador() == nuevoJugador.getCodJugador() ? nuevoJugador : j);
        }


        public boolean eliminar(int codJugador) {
            return jugadores.removeIf(j -> j.getCodJugador() == codJugador);
        }


        public Jugador obtenerPorCodigo(int codJugador) {
            return jugadores.stream().filter(j -> j.getCodJugador() == codJugador).findFirst().get();
        }


        public ArrayList<Jugador> obtenerPorEquipo(String codEquipo) throws SQLException {
            sql = "SELECT cod_jugador,nombre,apellido,nacionalidad FROM jugadores WHERE codEquipo = ?";
            ArrayList<Jugador> jugadores = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codEquipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Jugador j = new Jugador();
                j.setCodJugador(rs.getInt("cod_jugador"));
                j.setNombre(rs.getString("nombre"));
                j.setApellido(rs.getString("apellido"));
                j.setNacionalidad(rs.getString("nacionalidad"));
                jugadores.add(j);
            }
            return jugadores;
        }
}
