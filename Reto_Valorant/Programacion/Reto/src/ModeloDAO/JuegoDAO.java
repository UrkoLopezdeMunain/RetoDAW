package ModeloDAO;

import Modelo.Juego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
* @author Equipo tres
* @version (2.0)
* @see Juego
*/
public class JuegoDAO {

    /**
     * Conexión a la base de datos
     * @param c conexion
     */
    protected static Connection con;
    public JuegoDAO(Connection c) {
        this.con = c;
    }

    /**
     * Para recuperar un juego específico de codigo = 1
     * @return nuevo Juego.
     * @throws SQLException si ocurre un error
     */
    public static Juego conseguirJuego() throws SQLException{
        String sql = "SELECT * FROM juegos WHERE cod_juego = 1";
        Juego j = new Juego();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            j.setCodJuego(1);
            j.setNombre(rs.getString("nombre"));
            j.setFechaSalida(rs.getDate("fecha_salida").toLocalDate());
        }
        rs.close(); ps.close();
        return j;
    }
}
