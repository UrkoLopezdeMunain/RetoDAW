package ModeloController;

import ModeloDAO.JuegoDAO;

import java.time.LocalDate;

public class JuegoController {
    private static JuegoDAO juegoDAO;
    private static final LocalDate SALIDAJUEGO = LocalDate.of(1972,11,29);

    public JuegoController() {
        juegoDAO = new JuegoDAO();
    }


}
