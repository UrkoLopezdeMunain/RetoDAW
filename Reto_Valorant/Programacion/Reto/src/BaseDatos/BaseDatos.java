package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatos {

    public BaseDatos(){}

    public static Connection abrirCon() throws SQLException{
        Connection c = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String user = "equipoDani";
            String password = "Jm12345";
            String url ="jdbc:oracle:thin:@ccsatserv.dnsdojo.com:1521/xepdb1";
            c = DriverManager.getConnection(url, user, password);
            }catch (Exception e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        return c;
    }
}
