package test;

import dao.PrestamoDAO;
import interfaces.InterfazPrestamo;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la gestión de préstamos.
 * <p>
 * Esta clase valida el correcto funcionamiento del DAO de préstamos
 * ({@link PrestamoDAO}), comprobando las operaciones principales:
 * <ul>
 * <li>Inserción de préstamos.</li>
 * <li>Consulta de préstamos.</li>
 * <li>Modificación de datos.</li>
 * <li>Eliminación de préstamos.</li>
 * </ul>
 * Las pruebas están ordenadas para simular un flujo real de uso de la
 * aplicación (crear → consultar → modificar → eliminar).
 * </p>
 *
 * @version 1.0
 * @see PrestamoDAO
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPrestamo {

	/** DAO utilizado para interactuar con la base de datos de préstamos. */
	private InterfazPrestamo dao;

	/**
	 * Inicializa el DAO antes de cada prueba.
	 * <p>
	 * Este método se ejecuta automáticamente antes de cada test, garantizando una
	 * instancia limpia del DAO.
	 * </p>
	 */
	@BeforeEach
	void setup() {
		dao = new PrestamoDAO();
	}

	/**
	 * 1. Inserta un préstamo válido en la base de datos.
	 * <p>
	 * Se crea un préstamo con ID 100 y sin fecha de devolución. La prueba verifica
	 * que la operación se ejecuta sin errores.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la inserción.
	 */
	@Test
	@Order(1)
	void testInsertarPrestamo() throws Exception {
		dao.insertarPrestamo(100, 1, 1, "2025-01-01", "2025-01-10", null);

		assertTrue(true);
	}

	/**
	 * 2. Verifica que la consulta de préstamos devuelve datos válidos.
	 * <p>
	 * Se comprueba que el resultado no sea nulo y que el tamaño del mapa sea
	 * coherente (mayor o igual a cero).
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	@Order(2)
	void testVerPrestamos() throws Exception {
		Map<Integer, String[]> datos = dao.verPrestamos();

		assertNotNull(datos);
		assertTrue(datos.size() >= 0);
	}

	/**
	 * 3. Comprueba que el préstamo insertado previamente existe.
	 * <p>
	 * Se valida que el ID 100 esté presente en la colección de préstamos.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	@Order(3)
	void testPrestamoExiste() throws Exception {
		Map<Integer, String[]> datos = dao.verPrestamos();

		assertTrue(datos.containsKey(100));
	}

	/**
	 * 4. Modifica un préstamo existente.
	 * <p>
	 * Se actualizan los datos del préstamo con ID 100, incluyendo usuario, libro y
	 * fechas.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la modificación.
	 */
	@Test
	@Order(4)
	void testModificarPrestamo() throws Exception {
		dao.modificarPrestamo(100, 2, 2, "2025-01-02", "2025-01-15", "2025-01-05");

		assertTrue(true);
	}

	/**
	 * 5. Verifica que la modificación se ha aplicado correctamente.
	 * <p>
	 * Se comprueba que los nuevos valores (usuario y libro) coinciden con los datos
	 * actualizados.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	@Order(5)
	void testModificarVerificacion() throws Exception {
		Map<Integer, String[]> datos = dao.verPrestamos();

		String[] p = datos.get(100);

		assertEquals("2", p[0]);
		assertEquals("2", p[1]);
	}

	/**
	 * 6. Inserta un préstamo con fecha de devolución nula.
	 * <p>
	 * Valida que el sistema permite préstamos sin devolución (libro no devuelto).
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la inserción.
	 */
	@Test
	@Order(6)
	void testInsertarConFechaNull() throws Exception {
		dao.insertarPrestamo(101, 1, 1, "2025-01-01", "2025-01-10", null);

		assertTrue(true);
	}

	/**
	 * 7. Elimina un préstamo existente.
	 * <p>
	 * Se elimina el préstamo con ID 100 y se comprueba que ya no existe.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la eliminación.
	 */
	@Test
	@Order(7)
	void testBorrarPrestamo() throws Exception {
		dao.borrarPrestamo(100);

		Map<Integer, String[]> datos = dao.verPrestamos();

		assertFalse(datos.containsKey(100));
	}

	/**
	 * 8. Intenta eliminar un préstamo inexistente.
	 * <p>
	 * Verifica que el sistema no falla al intentar eliminar un registro que no
	 * existe en la base de datos.
	 * </p>
	 *
	 * @throws Exception si ocurre un error inesperado.
	 */
	@Test
	@Order(8)
	void testBorrarInexistente() throws Exception {
		dao.borrarPrestamo(9999);

		assertTrue(true);
	}
}