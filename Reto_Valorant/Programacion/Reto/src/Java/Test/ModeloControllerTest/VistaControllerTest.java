package ModeloControllerTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

public class VistaControllerTest {

    private VistaController vistaController;
    private ModeloController modeloController;

    @BeforeEach
    public void setUp() {
        // Crear instancias reales de las clases dependientes
        modeloController = new ModeloController();

        // Instanciar VistaController con ModeloController real
        vistaController = new VistaController(modeloController);
    }

    // 1. Test para la validación de usuario
    @Test
    public void testValidarUsuario() throws SQLException {
        String nombreUsuario = "testUser";

        // Crear usuario
        Usuario usuario = new Usuario(nombreUsuario);

        // Llamar al método
        boolean resultado = vistaController.validarUsuario(nombreUsuario);

        // Verificar si el resultado es el esperado
        assertTrue(resultado, "El usuario debería ser válido.");
    }

    // 2. Test para la validación de contraseña
    @Test
    public void testValidarPassWord() throws SQLException {
        String passWord = "123456";

        // `modeloController` ya tiene un usuario validado correctamente.
        boolean resultado = vistaController.validarPassWord(passWord);

        // Verificar si la contraseña es válida
        assertTrue(resultado, "La contraseña debería ser válida.");
    }

    // 3. Test para la validación de equipo
    @Test
    public void testValidarEquipo() throws Exception {
        String nombreEquipo = "EquipoTest";

        // Crear un objeto Equipo
        Equipo equipo = new Equipo(nombreEquipo.toLowerCase());

        // Llamar al metodo de VistaController para validar el equipo
        boolean resultado = vistaController.validarEquipo(nombreEquipo);

        // Verificar que la validación se haya realizado correctamente
        assertTrue(resultado, "El equipo debería ser válido.");
    }

    // 4. Test para la validación de jugador
    @Test
    public void testValidarJugador() throws SQLException {
        String nickName = "JugadorTest";

        // Crear un objeto Jugador
        Jugador jugador = new Jugador(nickName);

        // Llamar al metodo de VistaController para validar el jugador
        boolean resultado = vistaController.validarJugador(nickName);

        // Verificar que el jugador es válido
        assertTrue(resultado, "El jugador debería ser válido.");
    }

    // 5. Test para la creación de equipo
    @Test
    public void testCrearEquipo() throws Exception {
        String nombre = "EquipoTest";
        String fechaFund = "01-01-2000";

        // Llamar al metodo de VistaController para crear un equipo
        boolean resultado = vistaController.crearEquipo(nombre, fechaFund);

        // Verificar que el equipo se ha creado correctamente
        assertTrue(resultado, "El equipo debería haberse creado.");
    }

    // 6. Test para la creación de jugador
    @Test
    public void testCrearJugador() throws Exception {
        String nombre = "JugadorTest";
        String apellido = "ApellidoTest";
        String nacionalidad = "NacionalidadTest";
        String fechaNac = "01-01-1990";
        String sueldo = "1500.00";
        String rol = "Delantero";
        String nickName = "Jugador123";
        Equipo equipo = new Equipo("EquipoTest", "01-01-2000");

        // Llamar al metodo de VistaController para crear un jugador
        boolean resultado = vistaController.crearJugador(nombre, apellido, nacionalidad, fechaNac, sueldo, rol, nickName, equipo);

        // Verificar que el jugador se haya creado correctamente
        assertTrue(resultado, "El jugador debería haberse creado.");
    }

    // 7. Test para la eliminación de jugador
    @Test
    public void testBorrarJugador() throws SQLException {
        String nickName = "JugadorTest";

        // Crear un objeto Jugador
        Jugador jugador = new Jugador(nickName);

        // Llamar al metodo real de VistaController para borrar el jugador
        boolean resultado = vistaController.borrarJugador(nickName);

        // Verificar que el jugador se haya borrado correctamente
        assertTrue(resultado, "El jugador debería haberse borrado.");
    }

    // 8. Test para la eliminación de equipo
    @Test
    public void testBorrarEquipo() throws Exception {
        String nombreEquipo = "EquipoTest";

        // Crear un objeto Equipo
        Equipo equipo = new Equipo(nombreEquipo.toLowerCase());

        // Llamar al metodo  de VistaController para borrar el equipo
        boolean resultado = vistaController.borrarEquipo(nombreEquipo);

        // Verificar que el equipo se haya borrado correctamente
        assertTrue(resultado, "El equipo debería haberse borrado.");
    }

}

