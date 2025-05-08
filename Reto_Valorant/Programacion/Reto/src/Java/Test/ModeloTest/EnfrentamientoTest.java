package ModeloTest;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class EnfrentamientoTest {

    @Test
    public void testConstructorYGetters() {
        Equipo eq1 = new Equipo("Rojo");
        Equipo eq2 = new Equipo("Azul");
        Jornada jornada = new Jornada();
        jornada.setNumJornada(3);

        Enfrentamiento enf = new Enfrentamiento(2, 1, LocalTime.of(15, 30), jornada, eq1, eq2, 101);

        assertEquals(2, enf.getResultadosEq1());
        assertEquals(1, enf.getResultadosEq2());
        assertEquals("Rojo", enf.getEquipo1().getNombre());
        assertEquals("Azul", enf.getEquipo2().getNombre());
        assertEquals(3, enf.getJornada().getNumJornada());
        assertEquals(LocalTime.of(15, 30), enf.getHora());
        assertEquals(101, enf.getIdEnfrentamiento());
    }

    @Test
    public void testToStringContieneDatosClave() {
        Equipo eq1 = new Equipo("Rojo");
        Equipo eq2 = new Equipo("Azul");
        Jornada jornada = new Jornada();
        jornada.setNumJornada(5);

        Enfrentamiento enf = new Enfrentamiento(3, 3, LocalTime.of(18, 0), jornada, eq1, eq2, 202);

        String texto = enf.toString();

        assertTrue(texto.contains("Rojo"));
        assertTrue(texto.contains("Azul"));
        assertTrue(texto.contains("Id del enfrentamiento: 202"));
        assertTrue(texto.contains("Jornada: 5"));
    }
}
