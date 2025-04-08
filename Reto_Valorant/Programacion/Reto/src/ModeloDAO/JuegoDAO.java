package ModeloDAO;

import Modelo.Equipo;
import Modelo.Juego;

import java.util.ArrayList;

public class JuegoDAO {
    private final ArrayList<Juego> listaJuegos = new ArrayList<>();

    public JuegoDAO() {}

    public void crearJuego(Juego j) {
        listaJuegos.add(j);
    }


}
