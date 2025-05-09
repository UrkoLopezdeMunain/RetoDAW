package ModeloDAO;


import Modelo.Jornada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* @author Equipo tres
* @version (2.0)
* @see Jornada
*/
public class JornadaDAO {

    /**
     * Lista estática de jornadas (no usada activamente en este DAO, pero declarada).
     */
    private static final ArrayList<Jornada> listaJornada = new ArrayList<>();
    /**
     * Conexión a la base de datos.
     */
    protected Connection con;
    protected CompeticionDAO cDAO;

    /**
     * @param c conexión a la base de datos.
     * @param cDAO instancia de {@link CompeticionDAO} .
     */
    public JornadaDAO(Connection c, CompeticionDAO cDAO) {
        this.con = c;
        this.cDAO = cDAO;
    }

    /**
     * Inserta una nueva jornada
     * @param j la jornada a insertar.
     * @throws SQLException si ocurre un error
     */
    public void anadirJornada(Jornada j) throws SQLException {
        String sql = "INSERT INTO jornadas(num_jornada,fecha_inicio,cod_comp) VALUES(default,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, validarFecha(j.getFechaInicio().toString()));
        ps.setInt(2, j.getCompeticion().getCodCompeticion());
        ps.executeUpdate();
    }

  
    public java.sql.Date validarFecha(String fechaIni) {
        return java.sql.Date.valueOf(fechaIni);
    }

    /**
     * Obtiene todas las jornadas registradas 
     * @return lista de jornadas
     * @throws SQLException si ocurre un error 
     */
    public List<Jornada> getJornadas() throws SQLException {
        String sql = "SELECT * FROM jornadas";
        List<Jornada> jornadas = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Jornada j = new Jornada();
            j.setNumJornada(rs.getInt("num_jornada"));
            j.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
            j.setCompeticion(cDAO.conseguirCompeticion());
            jornadas.add(j);
        }
        ps.close(); rs.close();
        return jornadas;
    }

    /**
     * Obtiene una jornada específica por su identificador.
     *
     * @param id número de jornada a buscar.
     * @return  Jornada 
     * @throws SQLException si ocurre un error 
     */
    public Jornada getJornadaPorId(int id) throws SQLException {
        String sql = "SELECT * FROM jornadas where num_jornada = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Jornada j = new Jornada();
        if (rs.next()) {
            j.setNumJornada(rs.getInt("num_jornada"));
            j.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
            j.setCompeticion(cDAO.conseguirCompeticion());
        }
        return j;
    }
}
