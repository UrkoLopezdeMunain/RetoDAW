package ModeloControllerTest;



import ModeloControllerTest.JornadaController;
import ModeloDAOTest.JornadaDAO;
import ModeloTest.Jornada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JornadaControllerTest {

    JornadaDAO jornadaDAO;
    JornadaController controller;

    @BeforeEach
    void setUp() {
        jornadaDAO = mock(JornadaDAO.class);
        controller = new JornadaController(jornadaDAO);
    }

    @Test
    void testElegirMesDentroDeRango() {
        for (int i = 0; i < 100; i++) {
            int mes = controller.elegirMes();
            assertTrue(mes >= 1 && mes <= 12, "Mes fuera de rango: " + mes);
        }
    }

    @Test
    void testElegirDiaInicial_Febrero() {
        int dia = controller.elegirDiaInicial(2);
        assertTrue(dia >= 1 && dia <= 28, "Día de febrero fuera del rango válido: " + dia);
    }

    @Test
    void testElegirDiaInicial_Mes31() {
        int dia = controller.elegirDiaInicial(7); // Mes --> julio, 31 dias
        assertTrue(dia >= 1 && dia <= 31, "Día de julio fuera del rango válido: " + dia);
    }

    @Test
    void testElegirFechaFallback() throws Exception {

        when(jornadaDAO.getJornadas()).thenThrow(new NoSuchElementException());

        for (int i = 0; i < 100; i++) {
            LocalDate fecha = controller.elegirFecha();
            assertNotNull(fecha, "Fecha generada es nula");
        }
    }

    @Test
    void testElegirDiaCorrecto() throws Exception {
        Jornada j = new Jornada();
        j.setFechaInicio(LocalDate.of(2025, 5, 5)); // Dia --> Lunes
        LinkedList<Jornada> jornadas = new LinkedList<>();
        jornadas.add(j);
        when(jornadaDAO.getJornadas()).thenReturn(jornadas);

        LocalDate fecha = controller.elegirDia();
        assertNotNull(fecha, "La fecha elegida es nula");
        assertTrue(fecha.isAfter(j.getFechaInicio()), "La fecha elegida no es posterior a la fecha de la jornada");
    }
}

