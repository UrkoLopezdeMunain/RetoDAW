package ModeloController;

import Modelo.Juego;
import ModeloDAO.JuegoDAO;

import java.sql.SQLException;
import java.time.LocalDate;

public class JuegoController {
    private JuegoDAO juegoDAO;

    public JuegoController(JuegoDAO juegoDAO) {
        //rolController = new RolController();
        this.juegoDAO = juegoDAO;
    }
}
