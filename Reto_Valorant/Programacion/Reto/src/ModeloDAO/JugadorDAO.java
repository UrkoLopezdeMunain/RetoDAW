package ModeloDAO;

import Modelo.Equipo;
import Modelo.Jugador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Equipo tres
 * @version (2.0)
 * @see Jugador}.
 */
public class JugadorDAO {

        
        /**
         * Lista  almacena los jugadores obtenidos por equipo.
         */
        private static final ArrayList<Jugador> jugadores = new ArrayList<>();
        
        /**
         * Conexión a la base de datos.
         */
        protected Connection con;
        private String sql;

        /**
         * @param c conexión 
         */
        public JugadorDAO(Connection c) {
            this.con = c;
        }


        /**
         * Obtiene todos los jugadores que pertenecen a un equipo 
         * @param equipo el equipo del cual se quieren obtener jugadores
         * @return lista de jugadores 
         * @throws SQLException si ocurre un error 
         */
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


   /**
     * Crea un nuevo jugador
     * @param jugador 
     * @return true si la operación bien; false sino
     * @throws SQLException si ocurre un error al insertar
     */
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

   /**
     * Elimina un jugador 
     * @param jugador el jugador a eliminar
     * @return  true si el jugador fue eliminado correctamente;  false sino
     * @throws SQLException si ocurre un error 
     */
    public boolean borrarJugador(Jugador jugador) throws SQLException {
            sql="DELETE FROM jugadores WHERE lower(nickname) = lower(?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, jugador.getNickname());
            return ps.executeUpdate() != 0;
    }

   /**
     * Recupera un jugador utilizando nickname.
     * @param jugador jugador q buscar con el nickname
     * @return jugador
     * @throws SQLException si ocurre un error 
     */
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

   /**
     * Actualiza los datos de un jugador 
     * @param jugador el jugador con la información actualizada.
     * @return true si la actualización fue echa correctamnete; false de lo contrario
     * @throws SQLException si ocurre un error 
     */
    public boolean actualizarJugador(Jugador jugador) throws SQLException {
        sql = "UPDATE jugadores SET nombre=?, apellido=?, nacionalidad=?, fecha_nac=?, sueldo=?, nickname=?, rol=?, cod_equipo=? WHERE cod_jugador=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, jugador.getNombre());
        ps.setString(2, jugador.getApellido());
        ps.setString(3, jugador.getNacionalidad());
        ps.setDate(4, java.sql.Date.valueOf(jugador.getFechaNacimiento()));
        ps.setDouble(5, jugador.getSueldo());
        ps.setString(6, jugador.getNickname());
        ps.setString(7, jugador.getRol());
        ps.setInt(8, jugador.getEquipo().getCodEquipo());
        ps.setInt(9, jugador.getCodJugador());
        return ps.executeUpdate() != 0;
    }

}
