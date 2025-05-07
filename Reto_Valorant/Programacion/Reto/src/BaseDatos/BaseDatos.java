package BaseDatos;

import java.sql.*;

public class BaseDatos {

    public BaseDatos(){}

    public static Connection abrirCon() throws SQLException{
        Connection c = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            /*
            String user = "equipoDani";
            String password = "Jm12345";
            String url ="jdbc:oracle:thin:@ccsatserv.dnsdojo.com:1521/xepdb1";

             */

            String user = "eqdaw03";//poner user y contra de la bd de oracle
            String password = "eqdaw03";
            String url ="jdbc:oracle:thin:"+ user +"/"+password+"@172.20.225.114:1521:orcl";

            c = DriverManager.getConnection(url, user, password);º
            }catch (Exception e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        return c;
    }
}
