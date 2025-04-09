package ModeloDAO;

import Modelo.Equipo;
import Modelo.Jugador;

<<<<<<< HEAD
import java.awt.desktop.AppReopenedEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
=======
>>>>>>> d346b49428ff32fc290b6f2d5226b355f0c4daaa
import java.util.ArrayList;

public class EquipoDAO {

    private static ArrayList<Equipo> listaEquipos = new ArrayList<>();
<<<<<<< HEAD
    protected Connection con;
    protected String sql;
    public EquipoDAO(Connection c) {
        this.con = c;
    }
=======

    public EquipoDAO() {}
>>>>>>> d346b49428ff32fc290b6f2d5226b355f0c4daaa

    public void crearEquipo(Equipo e) {
        listaEquipos.add(e);
    }

    public ArrayList<Equipo> obtenerTodosLosEquipos() {
        return listaEquipos;
        //Devuelve un ArrayList nuevo para la seguridad de datos
    }

    public Equipo obtenerEquipoPorCodigo(int codEquipo) {
        return listaEquipos.stream()
                .filter(e -> e.getCodEquipo() == codEquipo)
                .findFirst().orElse(null);
    }
    public void actualizarEquipo(Equipo nuevoEquipo) {
        listaEquipos.replaceAll(e -> e.getCodEquipo() == nuevoEquipo.getCodEquipo() ? nuevoEquipo: e);
            //si e.getCodEquipo == nuevoEquipo.getCodEquipo, sustituimos, si no, dejamos a 'e' igual
    }
    public boolean eliminarEquipo(int codEquipo) {
        return listaEquipos.removeIf(e -> e.getCodEquipo() == codEquipo);
        //quita el equipo en caso de encontrarlo por su codigo
        //devuelve boolean para confirmar en Controller
    }
    public void anadirJugador(Equipo eq, Jugador j) {
        eq.agregarJugador(j);
    }
    public Equipo validarEquipo(String nombreEquipo, String fechaFund) throws Exception {
        sql = "SELECT * FROM equipos WHERE nombre = ? AND fecha_fund = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombreEquipo);
        ps.setString(2, fechaFund);
        ResultSet re = ps.executeQuery();
        if (re.next()) {
            return new Equipo(re.getInt("cod_equipo"),
                    re.getString("nombre"),
                    re.getDate("fecha_fund").toLocalDate(),
                    re.getInt("puntuacion"));
        } else {
            throw new Exception("El equipo no existe");
        }
    }
    public java.sql.Date validarFecha(String fechaFund) {
        return java.sql.Date.valueOf(fechaFund);
    }
}