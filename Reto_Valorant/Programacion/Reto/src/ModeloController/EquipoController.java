

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


/**
 * @author Equipo tres
 * @version (2.0)
 */
public class EquipoController {

    private EquipoDAO eDAO;
    //fecha fundacion hay que meterla en Juego
    /**
    *Lista interna de equipos
    */
    private List<Equipo> equipos;
    
    /**
     * Constructor
     *
     * @param eDAO 
     */
    public EquipoController(EquipoDAO eDAO) {
        this.eDAO = eDAO;
    }

    public EquipoController() {}

    /**
     * Para decir la fecha de fundacion del juego
     * @param j jugador
     */
    public void definirFechaFundacion(Juego j) {
        LocalDate FECHAFUNDACION = j.getFechaSalida();
    }

    /**
     * Obtiene la lista de todos los equipos 
     * @return los equipos
     * @throws SQLException si hay un error 
     */
    public List<Equipo> getEquipos() throws SQLException {
        equipos = eDAO.getEquipos();
        return equipos;
    }


    /**Metodos de validacion:
     * Pasa por este metodo validando el nombre y devolviendo el String a validarEquipo()*/

    
    /**
     * Valida un equipo 
     * @param equipo equipo a validar
     * @return el equipo corectamente
     * @throws Exception si hay un error 
     */
    public Equipo validarEquipo(Equipo equipo) throws Exception {
        return eDAO.validarEquipo(equipo);
    }

    /**
     * Valida que el nombre del equipo cumpla con el patrón permitido, el definido
     * @param nombre nombre a validar
     * @return el nombre correctamente
     * @throws Exception si no cumple con el patrón definido
     */
    public String validarNombre(String nombre) throws Exception {
        Pattern p = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9 _-]{3,15}$"); //15 como mucho como en MER/MR
        Matcher matcher = p.matcher(nombre);
        if (!matcher.matches()) {
            throw new Exception("El nombre del equipo no es valido, el patron es de 3 a 15 carac. con espacios y guiones permitidos");
        }
        return nombre;
    }

    /**Se pasa a java.sql.Date en DAO*/
    /**
     * Valida la fecha cumpla con lo definido
     * @param fecha  en formato dd-MM-yyyy.
     * @return fecha válida
     * @throws Exception si el formato es incorrecto, no como lo definido
     */
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
    
    /**
     * Elimina un equipo 
     * @param equipo equipo a borrar
     * @return true si se borró correctamente el equipo
     * @throws Exception si hay error e
     */
    public boolean borrarEquipo(Equipo equipo) throws Exception {
        return eDAO.borrarEquipo(equipo);
    }
    /**Al lanzar excepcion no hace falta colocarlos dentro de un 'if' , ya que si algo sale mal directamente relanzarán a la funcion padre*/
    /**
     * Crear un nuevo equipo validando su nombre
     * @param equipo equipo a crear
     * @return true si se creó corecto
     * @throws Exception si no se crea correctamente, con la validacio
     */
    public boolean crearEquipo(Equipo equipo) throws Exception {
        validarNombre(equipo.getNombre());
        return eDAO.crearEquipo(equipo);
    }

    /**
     * Actualiza la fecha de fundación de un equipo
     * @param equipo equipo con la fecha actualizada
     * @return true si se actualiza correctamente
     * @throws Exception si la validación falla, erronea
     */
    public boolean actualizarEquipoFecha(Equipo equipo) throws Exception {
        validarNombre(equipo.getNombre());
        return eDAO.actualizarFechaEquipo(equipo);
    }

    /**
     * Actualiza el nombre del equipo despues de validarlo
     * @param equipo equipo con nuevo nombre
     * @return true si se actualiza corectamente
     * @throws Exception si el nombre es erroneno
     */
    public boolean actualizarEquipoNombre(Equipo equipo) throws Exception {
        validarNombre(equipo.getNombre());
        return eDAO.actualizarNombreEquipo(equipo);
    }

    /**
     * Obtiene un equipo teniendo en cuenta su identificador , su id
     * @param id identificador del equipo
     * @return equipo 
     * @throws Exception en caso de no encontrar
     */
    public Equipo getEquipoPorId(int id) throws Exception{
        return eDAO.getEquipoPorId(id);
    }

    /**
     * Llama al procedimiento  
     * @return resultados del procedimiento
     * @throws SQLException si hay error 
     */
    public List<String> getEquiposProcedimiento() throws SQLException {
        return eDAO.getEquiposProcedimiento();
    }
}
