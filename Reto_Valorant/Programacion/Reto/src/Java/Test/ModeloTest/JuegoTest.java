package ModeloTest;

import Modelo.Juego;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JuegoTest {

    @ParameterizedTest
    @CsvSource({
            "1, FIFA, 2022-10-01",
            "2, PES, 2021-06-15",
            "3, NBA, 2023-03-20"
    })
    public void testGettersAndSetters(int codJuego, String nombre, String fechaSalidaStr) {
        Juego juego = new Juego();
        juego.setCodJuego(codJuego);
        juego.setNombre(nombre);
        juego.setFechaSalida(LocalDate.parse(fechaSalidaStr));

        assertEquals(codJuego, juego.getCodJuego());
        assertEquals(nombre, juego.getNombre());
        assertEquals(LocalDate.parse(fechaSalidaStr), juego.getFechaSalida());
    }

    @Test
    public void testConstructorSinArgumentos() {
        Juego juego = new Juego();

        assertEquals(0, juego.getCodJuego());
        assertNull(juego.getNombre());
        assertNull(juego.getFechaSalida());
    }
}