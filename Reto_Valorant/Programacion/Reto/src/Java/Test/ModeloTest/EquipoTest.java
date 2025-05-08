package ModeloTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EquipoTest {

    @Test
    public void testAgregarJugador() {
        Equipo equipo = new Equipo();
        Jugador jugador = new Jugador();

        equipo.agregarJugador(jugador);

        assertTrue(equipo.getListaJugadores().contains(jugador));
    }

    @Test
    public void testEliminarJugador() {
        Equipo equipo = new Equipo();
        Jugador jugador = new Jugador();

        equipo.agregarJugador(jugador);
        equipo.eliminarJugador(jugador);

        assertFalse(equipo.getListaJugadores().contains(jugador));
    }

    @Test
    public void testConstructorConNombreYFecha() {
        Equipo equipo = new Equipo("Tiburones", "2020-01-01");

        assertEquals("Tiburones", equipo.getNombre());
        assertEquals(LocalDate.of(2020, 1, 1), equipo.getFechaFundacion());
    }

    @Test
    public void testToStringIncluyeNombreYPuntuacion() {
        Equipo equipo = new Equipo(1, "Águilas", LocalDate.of(2010, 5, 10), 12);
        String texto = equipo.toString();

        assertTrue(texto.contains("Águilas"));
        assertTrue(texto.contains("12"));
        assertTrue(texto.contains("Codigo de equipo: 1"));
        assertTrue(texto.contains("Fecha de fundacion: 2010-05-10"));
    }

    @Test
    public void testToStringCuandoNoHayJugadores() {
        Equipo equipo = new Equipo(1, "Águilas", LocalDate.of(2010, 5, 10), 12);
        String texto = equipo.toString();

        // para q no muestre lista vacia
        assertFalse(texto.contains("[]"));
    }
}
