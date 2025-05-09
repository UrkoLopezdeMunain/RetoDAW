package ModeloController;

import Modelo.Competicion;
import ModeloDAO.CompeticionDAO;

import java.sql.SQLException;

/**
 * @author Equipo tres
 * @version (2.0)
 * @see Competicion
 * @see CompeticionDAO
 */
public class CompeticionController {
    private final CompeticionDAO competicionDAO;

    /**
     * El constructor
     * @param competicionDAO 
     */
    public CompeticionController(CompeticionDAO competicionDAO) {
        this.competicionDAO = competicionDAO;
    }

    /**
     * Recupera la competición 
     * @return competcicon
     * @throws SQLException si ocurre un error a la base
     */
    public Competicion getCompeticion() throws SQLException {
        return competicionDAO.conseguirCompeticion();
    }

    /**
     * Inicio de la competicion
     * @throws SQLException si ocurre un error en la base
     */
    public void empezarCompeticion() throws SQLException {
        competicionDAO.empezarCompeticion();
    }

}
