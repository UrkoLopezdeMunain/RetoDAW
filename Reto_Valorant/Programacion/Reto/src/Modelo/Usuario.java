package Modelo;

public class Usuario {

    private String nombreUsuario;
    private String paswd;
    private String tipoUsuario;

    public Usuario(String nombreUsuario, String paswd) {
        this.nombreUsuario = nombreUsuario;
        this.paswd = paswd;
    }

    public Usuario(String nombreUsuario, String paswd, String tipoUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.paswd = paswd;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario() {}

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPaswd() {
        return paswd;
    }

    public void setPaswd(String paswd) {
        this.paswd = paswd;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
