package ModeloController;

import Modelo.Competicion;
import ModeloDAO.CompeticionDAO;

import java.sql.SQLException;

public class CompeticionController {
    private final CompeticionDAO competicionDAO;

    public CompeticionController(CompeticionDAO competicionDAO) {
        this.competicionDAO = competicionDAO;
    }

    public Competicion getCompeticion() throws SQLException {
        return competicionDAO.conseguirCompeticion();
    }
    public void empezarCompeticion() throws SQLException {
        competicionDAO.empezarCompeticion();
    }
}
