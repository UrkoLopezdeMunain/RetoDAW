package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatos {

    public BaseDatos(){}

    public static Connection abrirCon() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.OracleDriver");

        String url = "jdbc:oracle:thin:@SrvOracle:1521:orcl";
        String user = "daw09";
        String pass = "daw09";

        return DriverManager.getConnection(url, user, pass);
    }
}
