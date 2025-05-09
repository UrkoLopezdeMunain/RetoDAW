package ModeloDAO;

import Modelo.Competicion;
import oracle.jdbc.OracleTypes;

import java.sql.*;

public class CompeticionDAO {
    protected static Connection con;

    public CompeticionDAO(Connection con) {
        this.con = con;
    }

    public boolean empezarCompeticion() throws SQLException {
        String sql = "{call pr_empezar_competicion(?)}";
        try (CallableStatement stmt = con.prepareCall(sql)) {
            // Registrar el parámetro de salida como NUMBER
            stmt.registerOutParameter(1, OracleTypes.NUMBER);

            // Ejecutar el procedimiento
            stmt.execute();

            // Obtener el resultado y convertirlo a boolean
            return stmt.getInt(1) == 1;
        }
    }

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
