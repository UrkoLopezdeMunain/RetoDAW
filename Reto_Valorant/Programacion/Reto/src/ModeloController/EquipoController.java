

package ModeloController;

import Modelo.Equipo;
import Modelo.Juego;
import ModeloDAO.EquipoDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquipoController {

    private final EquipoDAO eDAO;
    private LocalDate FECHAFUNDACION;
    //fecha fundacion hay que meterla en Juego
    private List<Equipo> equipos;

    public EquipoController(EquipoDAO eDAO) {
        this.eDAO = eDAO;
    }

    public void definirFechaFundacion(Juego j) {
        FECHAFUNDACION = j.getFechaSalida();
    }
    public List<Equipo> getEquipos() throws SQLException {
        equipos = eDAO.getEquipos();
        return equipos;
    }


    /**Metodos de validacion:
     * Pasa por este metodo validando el nombre y devolviendo el String a validarEquipo()*/
    public Equipo validarEquipo(Equipo equipo) throws Exception {
        return eDAO.validarEquipo(equipo);
    }
    public String validarNombre(String nombre) throws Exception {
        Pattern p = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9 _-]{3,15}$"); //15 como mucho como en MER/MR
        Matcher matcher = p.matcher(nombre);
        if (!matcher.matches()) {
            throw new Exception("El nombre del equipo no es valido, el patron es de 3 a 15 carac. con espacios y guiones permitidos");
        }
        return nombre;
    }
    /**Se pasa a java.sql.Date en DAO*/
    public LocalDate validarFecha(String fecha) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate fechaLocalDate = LocalDate.parse(fecha.trim(), formatter);
            if (fechaLocalDate.isAfter(LocalDate.now())) {
                throw new Exception("La fecha de fundacion no puede ser de anterior a la del juego");
            }
            return fechaLocalDate;
        }catch (DateTimeParseException e){
            throw new DateTimeParseException("La fecha no sigue un formato valido (dd-mm-aaaa)", fecha,0);
        }
    }

    /**CRUD de EquipoController*/
    public boolean borrarEquipo(Equipo equipo) throws Exception {
        return eDAO.borrarEquipo(equipo);
    }
    /**Al lanzar excepcion no hace falta colocarlos dentro de un 'if' , ya que si algo sale mal directamente relanzarán a la funcion padre*/
    public boolean crearEquipo(Equipo equipo) throws Exception {
        validarNombre(equipo.getNombre());
        return eDAO.crearEquipo(equipo);
    }
    public boolean actualizarEquipoFecha(Equipo equipo) throws Exception {
        validarNombre(equipo.getNombre());
        return eDAO.actualizarFechaEquipo(equipo);
    }
}
