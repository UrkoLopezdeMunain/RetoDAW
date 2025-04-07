package ModeloController;

import Vista.IniciarSesion;

public class VistaController {
    protected ModeloController modeloController;

    public VistaController(ModeloController modeloController) {
        this.modeloController = modeloController;
        setIniciarSesion();
    }
    public void setIniciarSesion() {
        IniciarSesion iniciarSesion = new IniciarSesion();
        iniciarSesion.setVisible(true);
        validarDatos(iniciarSesion);
    }
    public void validarDatos(IniciarSesion iniciarSesion) {

    }
    public boolean validarUsuario(String nombreUsuario){
        return modeloController.validarUsuario(nombreUsuario);
    }


}
