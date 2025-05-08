package ModeloTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;

public class CompeticionTest {

    @ParameterizedTest
    @CsvSource({
            "101, A, FIFA, 2020-01-01", // Test 1
            "102, B, PES, 2021-12-12",  // Test 2
            "103, C, NBA, 2022-07-25"   // Test 3
    })
    public void testConstructorYGetters(int codCompeticion, char estado, String juegoNombre, String juegoFecha) {
        // Convertir fecha de String a LocalDate
        LocalDate fecha = LocalDate.parse(juegoFecha);

        // Crear el objeto Juego
        Juego juego = new Juego();
        juego.setNombre(juegoNombre);
        juego.setFechaSalida(fecha);

        // Crear el objeto Competicion
        Competicion c = new Competicion(codCompeticion, estado, juego);

        // Verificar los valores con los parámetros
        assertEquals(codCompeticion, c.getCodCompeticion());
        assertEquals(estado, c.getEstado());
        assertEquals(juegoNombre, c.getJuego().getNombre());
        assertEquals(juego, c.getJuego());
    }
}