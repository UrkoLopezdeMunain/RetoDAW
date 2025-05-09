package ModeloDAO;

import Modelo.Equipo;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    /**
     * Conexión a la base de datos.
     */
    protected Connection con;
    protected String sql;

    /**
     * El Constructor que recibe una conexión a la base de datos.
     * @param c conexión a la base de datos.
     */
    public EquipoDAO(Connection c) {
        this.con = c;
    }

    
   /**
    * Constructor por defecto.
    */
    public EquipoDAO() {

    }
    
    /**
     * Crea un nuevo equipo 
     * @param equipo el equipo que hay que crear
     * @return true si se insertó correctamente,distinto a 0, hay equipo insertado, false sino
     * @throws SQLException si ocurre un error
    */
    public boolean crearEquipo(Equipo equipo) throws SQLException {
        sql = "INSERT INTO equipos(nombre,fecha_fundacion) VALUES(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, equipo.getNombre());
        ps.setDate(2, validarFecha(String.valueOf(equipo.getFechaFundacion())));
        //antes de llegar a ps.executeUpdate si salta un error entonces devolerá false, en otro caso True
        return ps.executeUpdate() != 0;
    }


     /**
      * Valida si un equipo existe en y da su informacion
      * @param equipo equipo con el nombre a validar
      * @return el uquipo, con su informacion
      * @throws SQLException si ocurre un error 
     */
    public Equipo validarEquipo(Equipo equipo) throws SQLException {
        sql = "SELECT cod_equipo,nombre,fecha_fundacion,puntuacion FROM equipos WHERE lower(nombre) = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, equipo.getNombre());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            equipo.setCodEquipo(rs.getInt("cod_equipo"));
            equipo.setNombre(rs.getString("nombre"));
            equipo.setFechaFundacion(rs.getDate("fecha_fundacion").toLocalDate());
            equipo.setPuntuacion(rs.getInt("puntuacion"));
        }  ; //lo que interesa por que es boolean, para que se pase a false
        rs.close(); ps.close();
        return equipo;
    }


    /**
     * Borrar un equpio ya existente
     * @param equipo el equipo a borrar
     * @return true si se eliminó correctamente, false sino
     * @throws SQLException si ocurre un error 
     */
    public boolean borrarEquipo(Equipo equipo) throws SQLException {
        sql = "DELETE FROM equipos WHERE lower(nombre) = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, equipo.getNombre());
        return ps.executeUpdate() != 0;
        //lanzara trigger si tiene jugadores
    }


    /**
     * Actualizar fecha de fundacion de equipo
     * @param equipo equipo con la nueva fecha de fundación
     * @return true si se actualizó correctamente, false sino
     * @throws SQLException si ocurre un error 
     */
    public boolean actualizarFechaEquipo(Equipo equipo) throws SQLException {
        sql="UPDATE equipos SET fecha_fundacion=? WHERE lower(nombre)=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, validarFecha(String.valueOf(equipo.getFechaFundacion()))); // Fecha primero
        ps.setString(2, equipo.getNombre().toLowerCase()); // Nombre después
        return ps.executeUpdate() != 0;
    }

    /**
     * Actualiza el nombre de un equipo.
     * @param equipo equipo con nuevo nombre
     * @return true si se actualizó correctamente,false sino
     * @throws SQLException si ocurre un error 
     */
    public boolean actualizarNombreEquipo(Equipo equipo) throws SQLException {
        sql = "UPDATE equipos SET nombre=? WHERE lower(nombre)=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, equipo.getNombre());
            ps.setString(2, equipo.getNombre().toLowerCase()); // Buscar por el nombre original (en minúsculas)
            return ps.executeUpdate() != 0;
        }
    }

    
    /**
     * Para obtener la  lista de todos los equipos 
     * @return lista de equipos
     * @throws SQLException si ocurre un error
     */
    public List<Equipo> getEquipos() throws SQLException {
        sql="SELECT * FROM EQUIPOS";
        List<Equipo> equipos = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Equipo equipo = new Equipo();
            equipo.setCodEquipo(rs.getInt("cod_equipo"));
            equipo.setNombre(rs.getString("nombre"));
            equipo.setFechaFundacion(rs.getDate("fecha_fundacion").toLocalDate());
            equipo.setPuntuacion(rs.getInt("puntuacion"));
            equipos.add(equipo);
        }
        ps.close(); rs.close();
        return equipos;
    }

    /**
     * Obtiene un equipo por su I
     * @param id identificador del equipo.
     * @return equipo con los datos 
     * @throws SQLException si ocurre un error
     */
    public Equipo getEquipoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM equipos WHERE cod_equipo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Equipo equipo = new Equipo();
            if (rs.next()) {
                equipo.setCodEquipo(id);
                equipo.setNombre(rs.getString("nombre"));
                // Configura otros atributos según tu modelo
                ps.close(); rs.close();
            }
            rs.close(); ps.close();
            return equipo;
        }
    }

    
    /**
     * Obtiene información de equipos usando procedimiento
     * @return lista de información de cada equipo
     * @throws SQLException si ocurre un error 
     */
    public List<String> getEquiposProcedimiento() throws SQLException {
        ResultSet rs = null;
        String sql = "{call pr_conseguir_info_equipos(?)}";
        CallableStatement stmt = con.prepareCall(sql);
        // Registrar el parámetro de salida como CURSOR
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        List<String> equipos = new ArrayList<>();
        // Ejecutar el procedimiento
        stmt.execute();

        // Obtener el resultado y convertirlo Lista de equipos
        rs = (java.sql.ResultSet)stmt.getObject(2);
        while (rs.next()) {
            StringBuilder sb = new StringBuilder();
            sb.append(rs.getString("nombre"));
            sb.append("\t-\t");
            sb.append(rs.getString("fecha_fundacion"));
            sb.append("\t-\t");
            sb.append(rs.getString("cantidad_jugadores"));
            sb.append("\t-\t");
            sb.append(rs.getString("salario_maximo"));
            sb.append("\t-\t");
            sb.append(rs.getString("salario_minimo"));
            sb.append("\t-\t");
            sb.append(rs.getString("salario_medio"));
            sb.append("\n");
            equipos.add(sb.toString());
        }
        rs.close();
        return equipos;
    }


    public java.sql.Date validarFecha(String fechaFund) {
        return java.sql.Date.valueOf(fechaFund);
    }
}
