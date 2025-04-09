package ModeloController;

import Vista.IniciarSesion;

import java.sql.SQLException;

public class VistaController {
    protected ModeloController modeloController;

    public VistaController(ModeloController modeloController) {
        this.modeloController = modeloController;
        setIniciarSesion();
    }
    public void setIniciarSesion() {
        IniciarSesion iniciarSesion = new IniciarSesion(this);
        iniciarSesion.setVisible(true);

    }

    public boolean validarUsuario(String nombreUsuario) throws SQLException {
        return modeloController.validarUsuario(nombreUsuario);
    }
    public boolean validarPassWord(String passWord) throws SQLException {
        return modeloController.validarPassWord(passWord);
    }


}
