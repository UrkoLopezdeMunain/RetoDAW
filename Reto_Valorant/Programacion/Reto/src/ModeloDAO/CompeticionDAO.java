package ModeloDAO;

import Modelo.Competicion;
import oracle.jdbc.OracleTypes;

import java.sql.*;

/**
*@author Equipo tres
*@version 2.0
*/
public class CompeticionDAO {

    /**
    *Conectar con la base de Datos
    */
    protected static Connection con;

    /**
    *Establece la conexion con la Base de datos
    */
    public CompeticionDAO(Connection con) {
        this.con = con;
    }

    /**
     *@return true si la competición se inició correctamente, false sino
     */
    public boolean empezarCompeticion() throws SQLException {
        String sql = "{call pr_empezar_competicion(?)}";
        CallableStatement stmt = con.prepareCall(sql);
        // Registrar el parámetro de salida como NUMBER
        stmt.registerOutParameter(1, OracleTypes.NUMBER);

        // Ejecutar el procedimiento
        stmt.execute();

        // Obtener el resultado y convertirlo a boolean
        return stmt.getInt(1) == 1;
    }

    /**
    *Recuperar juego asociado {@link JuegoDAO#conseguirJuego()}.
    *@throws SQLException si no se encuentra la competición 
    */
    // Versión alternativa si usas consulta SQL directa
    public Competicion conseguirCompeticion() throws SQLException {
        String sql = "SELECT cod_comp, estado " +
                "FROM competiciones WHERE cod_comp = 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Competicion(
                            rs.getInt("cod_comp"),
                            rs.getString("estado").charAt(0),
                            JuegoDAO.conseguirJuego()
                    );
                }
                rs.close(); ps.close(); //si llega aqui se prefiere que al menos se cierren los cursores y se lance la excp
            }
        }

        throw new SQLException("Competición no encontrada con código: " + 1);
    }
}
