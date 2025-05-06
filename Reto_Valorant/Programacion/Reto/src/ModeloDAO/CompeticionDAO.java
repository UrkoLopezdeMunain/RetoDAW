package ModeloDAO;

import Modelo.Competicion;
import oracle.jdbc.OracleTypes;

import java.sql.*;

public class CompeticionDAO {
    protected static Connection con;

    public CompeticionDAO(Connection con) {
        this.con = con;
    }

    public static boolean empezarCompeticio() throws SQLException {
        String sql = "{call pr_empezar_competicion()}";
        try (CallableStatement stmt = con.prepareCall(sql)) {
            // Registrar el parámetro de salida como NUMBER
            stmt.registerOutParameter(1,Types.NUMERIC);

            // Ejecutar el procedimiento
            stmt.execute();

            // Obtener el resultado y convertirlo a boolean
            return stmt.getInt(1) == 1;
        }
    }

    // Versión alternativa si usas consulta SQL directa
    public static Competicion conseguirCompeticion(int codCompeticion) throws SQLException {
        String sql = "SELECT cod_competicion, estado " +
                      "FROM competiciones WHERE cod_competicion = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, codCompeticion);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Competicion(
                            rs.getInt("cod_competicion"),
                            rs.getString("estado").charAt(0)
                    );
                }
            }
        }
        throw new SQLException("Competición no encontrada con código: " + codCompeticion);
    }
}
