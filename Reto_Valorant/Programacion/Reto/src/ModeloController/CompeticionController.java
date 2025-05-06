package ModeloController;

import ModeloDAO.CompeticionDAO;

import java.sql.SQLException;

public class CompeticionController {
    private final CompeticionDAO cDAO;

    public CompeticionController(CompeticionDAO cDAO) {
        this.cDAO = cDAO;
    }

    public boolean iniciarCompeticion() throws SQLException {
        return cDAO.empezarCompeticio();
    }

}
