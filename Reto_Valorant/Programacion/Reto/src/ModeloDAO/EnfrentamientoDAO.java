package ModeloDAO;

import Modelo.Enfrentamiento;
import Modelo.Equipo;
import Modelo.Jornada;
import ModeloController.EnfrentamientoController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnfrentamientoDAO {
    private static ArrayList<Enfrentamiento> enfrentamientos;
    protected Connection con;
    protected String sql;
    protected EquipoDAO equipoDAO;
    protected JornadaDAO jornadaDAO;
    protected EnfrentamientoController enfrentamientoController;
    public EnfrentamientoDAO(Connection c) {
        this.con = c;
    }
    public void setEnfrentamientoController(EnfrentamientoController enfrentamientoController){
        this.enfrentamientoController = enfrentamientoController;
    }

    public boolean anadirEnfrentamientos(Enfrentamiento en) throws SQLException {
        String sql = "INSERT INTO enfrentamientos (id_enfrentamiento, resultados_eq_1, resultados_eq_2, hora, cod_equ_1, cod_equ_2, num_jornada) " +
                    "VALUES (default, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, en.getResultadosEq1());
            ps.setInt(2, en.getResultadosEq2());
            ps.setTime(3, Time.valueOf(en.getHora())); //
            ps.setInt(4, en.getEquipo1().getCodEquipo()); // cod_eq_1
            ps.setInt(5, en.getEquipo2().getCodEquipo()); // cod_eq_2
            ps.setInt(6, en.getJornada().getNumJornada()); // num_jornada

        ps.close();
        return ps.executeUpdate() > 0;
    }


    public ArrayList<Enfrentamiento> getEnfrentamientos() throws SQLException{
        ArrayList<Enfrentamiento> enfrentamientos = new ArrayList<>();
        String sql = "SELECT * FROM enfrentamientos";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Enfrentamiento e = new Enfrentamiento();
                e.setIdEnfrentamiento(rs.getInt("id_enfrentamiento"));
                e.setEquipo1(equipoDAO.getEquipoPorId(rs.getInt("equipo1_id")));
                e.setEquipo2(equipoDAO.getEquipoPorId(rs.getInt("equipo2_id")));
                e.setHora(rs.getTime("hora").toLocalTime());
                e.setJornada(jornadaDAO.getJornadaPorId(rs.getInt("jornada_id")));
                e.setResultadosEq1(rs.getInt("resultado_eq1"));
                e.setResultadosEq2(rs.getInt("resultado_eq2"));

                enfrentamientos.add(e);
            }
        return enfrentamientos;
    }

    public Enfrentamiento getEnfrentamientoPorId(int id) throws SQLException{
        String sql = "SELECT * FROM enfrentamientos WHERE id_enfrentamiento = ?";
        PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
        Enfrentamiento e = new Enfrentamiento();
        if (rs.next()) {
                e.setIdEnfrentamiento(rs.getInt("id_enfrentamiento"));
                e.setEquipo1(equipoDAO.getEquipoPorId(rs.getInt("equipo1_id")));
                e.setEquipo2(equipoDAO.getEquipoPorId(rs.getInt("equipo2_id")));
                e.setHora(rs.getTime("hora").toLocalTime());
                e.setJornada(jornadaDAO.getJornadaPorId(rs.getInt("jornada_id")));
                e.setResultadosEq1(rs.getInt("resultado_eq1"));
                e.setResultadosEq2(rs.getInt("resultado_eq2"));
            }
                rs.close(); ps.close();
                return e;

    }


    public Enfrentamiento getEnfrentamientoPorEquipos(Equipo eq1, Equipo eq2) throws SQLException{
        String sql = "SELECT * FROM enfrentamientos WHERE cod_equ_1 = ? AND cod_equ_2 = ?";
        PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, eq1.getCodEquipo());
            ps.setInt(2, eq2.getCodEquipo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Enfrentamiento e = new Enfrentamiento();
                e.setIdEnfrentamiento(rs.getInt("id_enfrentamiento"));
                e.setEquipo1(eq1);
                e.setEquipo2(eq2);
                e.setHora(rs.getTime("hora").toLocalTime());
                e.setJornada(enfrentamientoController.getJornadaPorId(rs.getInt("jornada_id")));
                e.setResultadosEq1(rs.getInt("resultado_eq1"));
                e.setResultadosEq2(rs.getInt("resultado_eq2"));
                return e;
            }else
                return null;
    }
    public void actualizarEnfrentamiento(Enfrentamiento enfrentamiento) throws SQLException {
        sql="UPDATE enfrentamientos SET (?,?) WHERE id_enfrentamiento = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, enfrentamiento.getResultadosEq1());
        ps.setInt(2, enfrentamiento.getResultadosEq2());
        ps.setInt(3, enfrentamiento.getIdEnfrentamiento());
        ps.executeUpdate();
    }
    public List<Enfrentamiento> getEnfrentamientoPorJornada(Jornada jornada) throws Exception {
        List<Enfrentamiento> enfrentamientos = new ArrayList<>();
        String sql = "SELECT * FROM enfrentamientos WHERE num_jornada = ?";
        PreparedStatement ps = con.prepareStatement(sql);
             ps.setInt(1,jornada.getNumJornada());
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Enfrentamiento e = new Enfrentamiento();
                e.setIdEnfrentamiento(rs.getInt("id_enfrentamiento"));
                e.setEquipo1(equipoDAO.getEquipoPorId(rs.getInt("equipo1_id")));
                e.setEquipo2(equipoDAO.getEquipoPorId(rs.getInt("equipo2_id")));
                e.setHora(rs.getTime("hora").toLocalTime());
                e.setJornada(jornadaDAO.getJornadaPorId(rs.getInt("jornada_id")));
                e.setResultadosEq1(rs.getInt("resultado_eq1"));
                e.setResultadosEq2(rs.getInt("resultado_eq2"));

                enfrentamientos.add(e);
            }
            rs.close(); ps.close();
        return enfrentamientos;
    }




}