package ModeloController;

import Modelo.Equipo;
import Modelo.Jornada;
import ModeloDAO.CompeticionDAO;
import ModeloDAO.EquipoDAO;
import ModeloDAO.JornadaDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class JornadaController {
    private final JornadaDAO jornadaDAO;
    private final ModeloController modeloController;

    private static final int[] meses31 = {1,3,5,7,8,10,12};

    public JornadaController(JornadaDAO jornadaDAO, ModeloController modeloController) {
        this.jornadaDAO = jornadaDAO;
        this.modeloController = modeloController;
    }

    public void crearJornada() throws SQLException{
        List<Equipo> equipos = modeloController.getEquipos();
        for (int i = 0; i < equipos.size(); i++){
            Jornada jornada = new Jornada();
                jornada.setFechaInicio(elegirFecha());
                jornada.setCompeticion(modeloController.getCompeticion());
            jornadaDAO.anadirJornada(jornada);
        }
    }
    private LocalDate elegirFecha() throws SQLException{
        int mes;
        int dia;
        int year;
        try {
            LocalDate fecha = elegirDia();
            mes = fecha.getMonthValue();
            dia = fecha.getDayOfMonth();
            year = fecha.getYear();
        }catch (NullPointerException | NoSuchElementException e){
            mes = elegirMes();
            dia = elegirDiaInicial(mes);
            year = 2025;
        }
        return LocalDate.of(year,mes,dia);
    }
    private int elegirMes(){
        Random random = new Random();
        return random.nextInt(11)+1;
    }
    private int elegirDiaInicial(int mes){
        Random random = new Random();
        int randomDia = 0;
        if (mes == 2)
            randomDia = random.nextInt(27)+1;
        else{
            for (int i : meses31) {
                if (mes == i)
                    randomDia = random.nextInt(30)+1;
                else
                    randomDia = random.nextInt(29)+1;
            }
        }
        return randomDia;
    }
    private LocalDate elegirDia() throws SQLException {
        Random random = new Random();
        int randomDia;
        randomDia = random.nextInt(7);
        DayOfWeek diaJornada = jornadaDAO.getJornadas().getLast().getFechaInicio().getDayOfWeek();
        int diasHastaDomingo = DayOfWeek.SUNDAY.getValue() - diaJornada.getValue();
        // Si el día actual no es domingo, suma los días necesarios
        if (diasHastaDomingo < 0) {
            diasHastaDomingo += 7;
        }
        return jornadaDAO.getJornadas().getLast().getFechaInicio().plusDays(diasHastaDomingo).plusDays(randomDia);
    }
    public List<Jornada> getJornadas() throws SQLException{
        return jornadaDAO.getJornadas();
    }
    public Jornada getJornadaPorId(int id) throws SQLException{
        return jornadaDAO.getJornadaPorId(id);
    }
}
