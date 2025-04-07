package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatos {

    public BaseDatos() throws SQLException, ClassNotFoundException {

        //FALTA POR PONERNOS EL ARCHIVO.jar SOLO VOY A PROGRAMAR LA CONEXION
        abrirCon();
    }

    public static Connection abrirCon() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.OracleDriver");

        String url = "jdbc:oracle:thin:@SrvOracle:1521:orcl";
        String user = "aaron";
        String pass = "niIdea";
        //logicamente no conozco estos parametros

        return DriverManager.getConnection(url, user, pass);
    }
}
