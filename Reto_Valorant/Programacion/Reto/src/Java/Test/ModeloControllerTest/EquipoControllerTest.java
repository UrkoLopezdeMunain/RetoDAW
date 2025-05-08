package ModeloControllerTest;

import ModeloControllerTest.EquipoController;
import ModeloDAOTest.EquipoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EquipoControllerTest {


    private EquipoController controller;

    @BeforeEach
    void setUp() {
        controller = new EquipoController(new EquipoDAO());
    }

    @Test
    void testNombreValido() throws Exception {
        String nombre = "Team_A";
        assertEquals(nombre, controller.validarNombre(nombre));
    }

    @Test
    void testNombreInvalido() {
        String nombre = "??";
        Exception exception = assertThrows(Exception.class, () -> {
            controller.validarNombre(nombre);
        });
        assertTrue(exception.getMessage().contains("no es valido"));
    }

    @Test
    void testFechaValida() throws Exception {
        String fecha = "01-01-2020";
        assertEquals("2020-01-01", controller.validarFecha(fecha));
    }

    @Test
    void testFechaInvalidaFormato() {
        String fecha = "2020/01/01";
        assertThrows(Exception.class, () -> controller.validarFecha(fecha));
    }

    @Test
    void testFechaFutura() {
        String fecha = "01-01-3000";
        Exception exception = assertThrows(Exception.class, () -> {
            controller.validarFecha(fecha);
        });
        assertTrue(exception.getMessage().contains("no puede ser de anterior"));
    }
}
