package ModeloDAO;

import Modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Equipo tres
 * @version (2.0)
 * @see Usuario
 */
public class UsuarioDAO {
    private String sql;
    /**
     * Conexión a la base de datos.
     */
    protected Connection con;

    /**
     * Inicia conexion a la base de datos
     * @param c conexión 
     */
    public UsuarioDAO(Connection c) {
        this.con = c;
    }

    //crud
    /** hecho de esta manera para que pueda lanzar la exception en IniciarSesion.class */
    /**
     * Valida un usuario
     * @param usuario el usuario que se quiere validar 
     * @return Usuario con los datos obtenidos
     * @throws SQLException si ocurre un error en la base
     */
    public Usuario validarUsuario(Usuario usuario) throws SQLException {
        sql="SELECT * FROM usuarios WHERE lower(nombre) = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, usuario.getNombreUsuario());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            usuario.setNombreUsuario(rs.getString("nombre"));
            usuario.setPaswd(rs.getString("contraseña"));
            usuario.setTipoUsuario(rs.getString("tipo_usuario"));
        }
        rs.close(); ps.close();
        return usuario;
    }
}
