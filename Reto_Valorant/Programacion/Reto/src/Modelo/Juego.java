package Modelo;

import java.time.LocalDate;

public class Juego {
    private int codJuego;
    private LocalDate fechaSalida;
    private String nombre;

    public int getCodJuego() {
        return codJuego;
    }

    public void setCodJuego(int codJuego) {
        this.codJuego = codJuego;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
