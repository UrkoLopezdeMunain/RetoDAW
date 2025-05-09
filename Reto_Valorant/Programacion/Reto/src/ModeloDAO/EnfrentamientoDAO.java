package ModeloDAO;

import Modelo.Enfrentamiento;
import Modelo.Equipo;
import Modelo.Jornada;
import ModeloController.EnfrentamientoController;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Equipo tres
 * @version (2.0)
 * @see Enfrentamiento
 */
public class EnfrentamientoDAO {
    protected Connection con;
    protected String sql;
    protected EnfrentamientoController enfrentamientoController;

    /**
     * Inicia la conexion a la base
     * @param c conexión 
     */
    public EnfrentamientoDAO(Connection c) {
        this.con = c;
    }

    /**
     * Asigna el controlador 
     * @param enfrentamientoController 
     */
    public void setEnfrentamientoController(EnfrentamientoController enfrentamientoController){
        this.enfrentamientoController = enfrentamientoController;
    }
    
    /**
     * Inserta un nuevo enfrentamiento 
     * @param en enfrentamiento a insertar
     * @return true si la inserción fue correcta
     * @throws SQLException en caso de error 
     */
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

        boolean si = ps.executeUpdate() > 0;
        ps.close();
        return si;
    }

    /**
     * Obtiene todos los enfrentamientos almacenados 
     * @return la lista de los enfrentamientos
     * @throws Exception si ocurre un error
     */
    public ArrayList<Enfrentamiento> getEnfrentamientos() throws Exception{
        ArrayList<Enfrentamiento> enfrentamientos = new ArrayList<>();
        String sql = "SELECT * FROM enfrentamientos";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Enfrentamiento e = new Enfrentamiento();
                e.setIdEnfrentamiento(rs.getInt("id_enfrentamiento"));
                e.setEquipo1(enfrentamientoController.getEquipoPorId(rs.getInt("cod_equ_1")));
                e.setEquipo2(enfrentamientoController.getEquipoPorId(rs.getInt("cod_equ_1")));
                e.setHora(rs.getTime("hora").toLocalTime());
                e.setJornada(enfrentamientoController.getJornadaPorId(rs.getInt("jornada_id")));
                e.setResultadosEq1(rs.getInt("resultados_eq_1"));
                e.setResultadosEq2(rs.getInt("resultados_eq_2"));

                enfrentamientos.add(e);
            }
        return enfrentamientos;
    }

    /**
     * Obtiener enfrentamientos de una jornada 
     * @param j número de jornada.
     * @return la lista de los  enfrentamientos
     * @throws Exception en caso de error
     */
    public ArrayList<Enfrentamiento> enfrentamientos(int j) throws Exception{
        ArrayList<Enfrentamiento> enfrentamientos = new ArrayList<>();
        String sql = "SELECT * FROM enfrentamientos where num_jornada = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, j);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Enfrentamiento e = new Enfrentamiento();
            e.setIdEnfrentamiento(rs.getInt("id_enfrentamiento"));
            e.setEquipo1(enfrentamientoController.getEquipoPorId(rs.getInt("cod_equ_1")));
            e.setEquipo2(enfrentamientoController.getEquipoPorId(rs.getInt("cod_equ_1")));
            e.setHora(rs.getTime("hora").toLocalTime());
            e.setJornada(enfrentamientoController.getJornadaPorId(rs.getInt("jornada_id")));
            e.setResultadosEq1(rs.getInt("resultados_eq_1"));
            e.setResultadosEq2(rs.getInt("resultados_eq_2"));

            enfrentamientos.add(e);
        }
        return enfrentamientos;
    }
    /**
     * Ejecuta un procedimiento 
     * @param j número de jornada
     * @return lista de enfrentamientos 
     * @throws Exception si ocurre un error
     */
    public List<String> enfrentamientosProcedimiento(int j) throws Exception {
        ResultSet rs = null;
        String sql = "{call pr_conseguir_info_equipos(?)}";
        CallableStatement stmt = con.prepareCall(sql);
        // Registrar el parámetro de salida como CURSOR
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        List<String> enfrentamientos = new ArrayList<>();
        // Ejecutar el procedimiento
        stmt.execute();

        // Obtener el resultado y convertirlo Lista de equipos
        rs = (java.sql.ResultSet)stmt.getObject(2);
        while (rs.next()) {
            StringBuilder sb = new StringBuilder();
            sb.append(rs.getString("equipo1"));
            sb.append("\t");
            sb.append(rs.getString("reseq1"));
            sb.append("\t-\t");
            sb.append(rs.getString("reseq2"));
            sb.append("\t");
            sb.append(rs.getString("equipo2"));
            sb.append("\t-\t");
            sb.append(rs.getString("hora"));
            sb.append("\n");
            enfrentamientos.add(sb.toString());
        }
        rs.close();
        return enfrentamientos;
    }

    /**
     * Obtiene un enfrentamiento teniendo en cuenat su ID.
     * @param id identificador del enfrentamiento
     * @return Enfrentamiento
     * @throws Exception si no se encuentra o hay algun error
     */
    public Enfrentamiento getEnfrentamientoPorId(int id) throws Exception{
        String sql = "SELECT * FROM enfrentamientos WHERE id_enfrentamiento = ?";
        PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
        Enfrentamiento e = new Enfrentamiento();
        if (rs.next()) {
                e.setIdEnfrentamiento(rs.getInt("id_enfrentamiento"));
                e.setEquipo1(enfrentamientoController.getEquipoPorId(rs.getInt("cod_equ_1")));
                e.setEquipo2(enfrentamientoController.getEquipoPorId(rs.getInt("cod_equ_2")));
                e.setHora(rs.getTime("hora").toLocalTime());
                e.setJornada(enfrentamientoController.getJornadaPorId(rs.getInt("jornada_id")));
                e.setResultadosEq1(rs.getInt("resultados_eq_1"));
                e.setResultadosEq2(rs.getInt("resultados_eq_2"));
            }
                rs.close(); ps.close();
                return e;

    }

    /**
     * Para buscar  enfrentamiento entre dos equipos 
     * @param eq1 para el equipo 1
     * @param eq2 para el equipo 2
     * @return enfrentamiento o null
     * @throws SQLException si hay error 
     */
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
                e.setResultadosEq1(rs.getInt("resultados_eq_1"));
                e.setResultadosEq2(rs.getInt("resultados_eq_2"));
                return e;
            }else
                return null;
    }

    /**
     * Actualiza los resultados de un enfrentamiento
     * @param enfrentamiento 
     * @throws SQLException si ocurre un error 
     */
    public void actualizarEnfrentamiento(Enfrentamiento enfrentamiento) throws SQLException {
        sql="UPDATE enfrentamientos SET resultados_eq_1 = ? WHERE id_enfrentamiento = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, enfrentamiento.getResultadosEq1());
        ps.setInt(2, enfrentamiento.getIdEnfrentamiento());
        ps.executeUpdate();
        sql="UPDATE enfrentamientos SET resultados_eq_2 = ? WHERE id_enfrentamiento = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, enfrentamiento.getResultadosEq2());
        ps.setInt(2, enfrentamiento.getIdEnfrentamiento());
        ps.executeUpdate();
    }

    /**
     * Obtiene todos los enfrentamientos de una jornada 
     * @param jornada para el número de jornada
     * @return enfrentamientos
     * @throws Exception si ocurre un error
     */
    public List<Enfrentamiento> getEnfrentamientoPorJornada(int jornada) throws Exception {
        List<Enfrentamiento> enfrentamientos = new ArrayList<>();
        String sql = "SELECT * FROM enfrentamientos WHERE num_jornada = ?";
        PreparedStatement ps = con.prepareStatement(sql);
             ps.setInt(1,jornada);
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Enfrentamiento e = new Enfrentamiento();
                e.setIdEnfrentamiento(rs.getInt("id_enfrentamiento"));
                e.setEquipo1(enfrentamientoController.getEquipoPorId(rs.getInt("cod_equ_1")));
                e.setEquipo2(enfrentamientoController.getEquipoPorId(rs.getInt("cod_equ_2")));
                e.setHora(rs.getTime("hora").toLocalTime());
                e.setJornada(enfrentamientoController.getJornadaPorId(rs.getInt("num_jornada")));
                e.setResultadosEq1(rs.getInt("resultados_eq_1"));
                e.setResultadosEq2(rs.getInt("resultados_eq_2"));

                enfrentamientos.add(e);
            }
            rs.close(); ps.close();
        return enfrentamientos;
    }




}
