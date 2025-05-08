package ModeloTest;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JornadaTest {

    @Test
    public void testConstructorYGetters() {
        Competicion competicion = new Competicion(1, 'A', null);
        LocalDate fecha = LocalDate.of(2024, 5, 1);
        Jornada jornada = new Jornada(3, fecha, competicion);

        assertEquals(3, jornada.getNumJornada());
        assertEquals(fecha, jornada.getFechaInicio());
        assertEquals(competicion, jornada.getCompeticion());
    }

    @Test
    public void testSetters() {
        Jornada jornada = new Jornada();
        jornada.setNumJornada(5);
        jornada.setFechaInicio(LocalDate.of(2023, 1, 1));
        Competicion competicion = new Competicion(2, 'B', null);
        jornada.setCompeticion(competicion);

        assertEquals(5, jornada.getNumJornada());
        assertEquals(LocalDate.of(2023, 1, 1), jornada.getFechaInicio());
        assertEquals(competicion, jornada.getCompeticion());
    }

}