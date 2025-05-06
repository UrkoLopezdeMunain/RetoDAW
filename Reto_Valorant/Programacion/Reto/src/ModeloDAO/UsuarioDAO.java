package ModeloDAO;

import Modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private String sql;
    protected Connection con;

    public UsuarioDAO(Connection c) {
        this.con = c;
    }

    //crud
    /** hecho de esta manera para que pueda lanzar la exception en IniciarSesion.class */
    public Usuario validarUsuario(Usuario usuario) throws SQLException {
        sql="SELECT * FROM usuarios WHERE lower(nombre) = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, usuario.getNombreUsuario());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            usuario.setNombreUsuario(rs.getString("nombre"));
            usuario.setPaswd(rs.getString("contraseña"));
            usuario.setTipoUsuario(rs.getString("tipo_usuario"));
            return usuario;
        }
        return null;
    }
}
