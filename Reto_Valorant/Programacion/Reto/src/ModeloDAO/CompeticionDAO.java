package ModeloDAO;

import Modelo.Competicion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompeticionDAO {
    protected Connection con;
    protected JuegoDAO jDAO;

    public CompeticionDAO(Connection con,JuegoDAO jDAO) {
        this.con = con;
        this.jDAO = jDAO;
    }

    public Competicion conseguirCompeticion(int codComp) throws SQLException {
        String sql = "SELECT * FROM competiciones WHERE cod_comp = ?";
        Competicion c = new Competicion();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,codComp);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            c.setCodCompeticion(codComp);
            c.setEstado(rs.getString("estado").charAt(1));
            c.setJuego(jDAO.conseguirJuego(rs.getInt("cod_juego")));
            return c;
        } else {
            return null; //lo que interesa por que es boolean, para que se pase a false
        }
    }
}
