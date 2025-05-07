package ModeloDAO;

import Modelo.Juego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JuegoDAO {
    private final ArrayList<Juego> listaJuegos = new ArrayList<>();
    protected static Connection con;
    public JuegoDAO(Connection c) {
        this.con = c;
    }

    public static Juego conseguirJuego() throws SQLException{
        String sql = "SELECT * FROM juegos WHERE cod_juego = 1";
        Juego j = new Juego();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            j.setCodJuego(1);
            j.setNombre(rs.getString("nombre"));
            j.setFechaSalida(rs.getDate("fecha_salida").toLocalDate());
            return j;
        } else {
            return null; //lo que interesa por que es boolean, para que se pase a false
        }
    }
}
