package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;


public class Equipo {

    private int codEquipo; // Código único del equipo (ej: 00001)
    private int puntuacion; // Puntuación actual del equipo
    private String nombre; // Nombre del equipo
    private LocalDate fechaFundacion; // Fecha de fundación del equipo
    private ArrayList<Jugador> listaJugadores; // Lista de jugadores del equipo
    /*
      Constructor sin argumentos. Inicializa la lista de jugadores.
     */
    public Equipo() {
        this.listaJugadores = new ArrayList<>();
    }


    public Equipo(int codEquipo, String nombre, LocalDate fechaFundacion, int puntuacion) {
        this.codEquipo = codEquipo;
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.puntuacion = puntuacion;
    }

    public Equipo(int codEquipo, String nombre, LocalDate fechaFundacion, int puntuacion, ArrayList<Jugador> listaJugadores) {
        this.codEquipo = codEquipo;
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.puntuacion = puntuacion;
        this.listaJugadores = listaJugadores;
    }

    public Equipo(String nombre, LocalDate fechaFund) {
        this.nombre = nombre;
        this.fechaFundacion = fechaFund;
    }
    public Equipo(int codEquipo,String nombre) {
        this.codEquipo = codEquipo;
        this.nombre = nombre;
    }

    public Equipo(String nombreEquipo) {
        this.nombre = nombreEquipo;
    }

    // Getters y setters

    public int getCodEquipo() {
        return codEquipo;
    }

    public void setCodEquipo(int codEquipo) {
        this.codEquipo = codEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public ArrayList<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(ArrayList<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    // Metodos que podrian ser necesarios

    /*
      Agrega un jugador a la lista del equipo.
     */
    public void agregarJugador(Jugador jugador) {
        this.listaJugadores.add(jugador);
    }

    /*
      Elimina un jugador de la lista del equipo.
     */
    public void eliminarJugador(Jugador jugador) {
        this.listaJugadores.remove(jugador);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(getNombre())
                .append("\n").append("Codigo de equipo: ").append(getCodEquipo())
                .append("\n").append("Fecha de fundacion: ").append(getFechaFundacion().toString())
                .append("\n").append("Puntuacion: ").append(getPuntuacion());
        if (!listaJugadores.isEmpty()) sb.append(getListaJugadores().toString());
    return sb.toString();

    }

}