package ModeloTest;

import Modelo.Jugador;
import Modelo.Equipo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JugadorTest {

    //para el constructor, con parametros
    @Test
    public void testConstructorCompleto() {
        Equipo equipo = new Equipo("Tiburones", LocalDate.of(2020, 1, 1));
        Jugador jugador = new Jugador(1, "Juan", "Pérez", "Española", LocalDate.of(1995, 5, 10), 50000, "Centinela", "Juanito", equipo);

        assertEquals(1, jugador.getCodJugador());
        assertEquals("Juan", jugador.getNombre());
        assertEquals("Pérez", jugador.getApellido());
        assertEquals("Española", jugador.getNacionalidad());
        assertEquals(LocalDate.of(1995, 5, 10), jugador.getFechaNacimiento());
        assertEquals(50000, jugador.getSueldo());
        assertEquals("Centinela", jugador.getRol());
        assertEquals("Juanito", jugador.getNickname());
        assertEquals(equipo, jugador.getEquipo());
    }

  //para los getters and setters
    @Test
    public void testSettersYGetters() {
        Jugador jugador = new Jugador();
        jugador.setCodJugador(2);
        jugador.setNombre("Carlos");
        jugador.setApellido("Gómez");
        jugador.setNacionalidad("Mexicana");
        jugador.setFechaNacimiento(LocalDate.of(1994, 3, 20));
        jugador.setSueldo(60000);
        jugador.setRol("Controlador");
        jugador.setNickname("Carli");

        assertEquals(2, jugador.getCodJugador());
        assertEquals("Carlos", jugador.getNombre());
        assertEquals("Gómez", jugador.getApellido());
        assertEquals("Mexicana", jugador.getNacionalidad());
        assertEquals(LocalDate.of(1994, 3, 20), jugador.getFechaNacimiento());
        assertEquals(60000, jugador.getSueldo());
        assertEquals("Iniciador", jugador.getRol());
        assertEquals("Carli", jugador.getNickname());
    }

    // Para el toString()
    @Test
    public void testToString() {
        Jugador jugador = new Jugador(3, "Luis", "Martínez", "Argentina", LocalDate.of(1990, 7, 15), 40000, "Duelista", "Lucho", null);

        String resultadoEsperado = "Luis Martínez Duelista 40000.0";
        assertEquals(resultadoEsperado, jugador.toString());
    }

    // para el contrusctor, sin parametros
    @Test
    public void testConstructorSinParametros() {
        Jugador jugador = new Jugador();

        assertNull(jugador.getNombre());
        assertNull(jugador.getApellido());
        assertNull(jugador.getNacionalidad());
        assertNull(jugador.getFechaNacimiento());
        assertEquals(0.0, jugador.getSueldo());
        assertNull(jugador.getRol());
        assertNull(jugador.getNickname());
    }

    // para el constructor solo con nickname
    @Test
    public void testConstructorConNickname() {
        Jugador jugador = new Jugador("Lucho");

        assertEquals("Lucho", jugador.getNickname());
        assertNull(jugador.getNombre());
        assertNull(jugador.getApellido());
    }
}
