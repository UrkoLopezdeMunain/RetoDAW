package Modelo;

import java.time.LocalDate;

public class Jugador {
    private int codJugador;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private double sueldo;
    private String rol;
    private String nickname;
    private Equipo equipo;

    public Jugador(int codJugador, String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento, int sueldo, String rol, String nickname, Equipo equipo) {
        this.codJugador = codJugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.sueldo = sueldo;
        this.rol = rol;
        this.nickname = nickname;
        this.equipo = equipo;
    }

    public Jugador(int codJugador, String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento, double sueldo, String rol, String nickname) {
        this.codJugador = codJugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.sueldo = sueldo;
        this.rol = rol;
        this.nickname = nickname;
    }
    public Jugador(int codJugador, String nombre, String apellido, String nacionalidad) {
        this.codJugador = codJugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
    }

    public Jugador() {
    }

    public Jugador(String nombre, String apellido, String nacionalidad, String fechaNac, String sueldo, String rol, String nickName, Equipo codEquipo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = LocalDate.parse(fechaNac);
        this.sueldo = Double.parseDouble(sueldo);
        this.rol = rol;
        this.nickname = nickName;
        this.equipo = codEquipo;
    }

    public Jugador(String nickName) {
        this.nickname = nickName;
    }

    public Jugador(int codJugador, String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento, int sueldo, String rol) {
        this.codJugador = codJugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.sueldo = sueldo;
        this.rol = rol;
    }


    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public int getCodJugador() {
        return codJugador;
    }

    public void setCodJugador(int codJugador) {
        this.codJugador = codJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString(){
        return nombre + " " + apellido + " " + rol + " " + sueldo;
    }
}
