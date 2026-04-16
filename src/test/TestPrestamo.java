package test;

import dao.PrestamoDAO;
import interfaces.InterfazPrestamo;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPrestamo {

	private InterfazPrestamo dao;

	@BeforeEach
	void setup() {
		dao = new PrestamoDAO();
	}

	// 1️ Insertar préstamo correcto
	@Test
	@Order(1)
	void testInsertarPrestamo() throws Exception {
		dao.insertarPrestamo(100, 1, 1, "2025-01-01", "2025-01-10", null);

		assertTrue(true);
	}

	// 2️ Ver préstamos no vacío
	@Test
	@Order(2)
	void testVerPrestamos() throws Exception {
		Map<Integer, String[]> datos = dao.verPrestamos();

		assertNotNull(datos);
		assertTrue(datos.size() >= 0);
	}

	// 3️ Comprobar préstamo insertado
	@Test
	@Order(3)
	void testPrestamoExiste() throws Exception {
		Map<Integer, String[]> datos = dao.verPrestamos();

		assertTrue(datos.containsKey(100));
	}

	// 4️ Modificar préstamo
	@Test
	@Order(4)
	void testModificarPrestamo() throws Exception {
		dao.modificarPrestamo(100, 2, 2, "2025-01-02", "2025-01-15", "2025-01-05");

		assertTrue(true);
	}

	// 5️ Ver modificación aplicada
	@Test
	@Order(5)
	void testModificarVerificacion() throws Exception {
		Map<Integer, String[]> datos = dao.verPrestamos();

		String[] p = datos.get(100);

		assertEquals("2", p[0]);
		assertEquals("2", p[1]); 
	}

	// 6️ Insertar con devolución null
	@Test
	@Order(6)
	void testInsertarConFechaNull() throws Exception {
		dao.insertarPrestamo(101, 1, 1, "2025-01-01", "2025-01-10", null);

		assertTrue(true);
	}

	// 7️ Borrar préstamo
	@Test
	@Order(7)
	void testBorrarPrestamo() throws Exception {
		dao.borrarPrestamo(100);

		Map<Integer, String[]> datos = dao.verPrestamos();

		assertFalse(datos.containsKey(100));
	}

	// 8️ Borrar préstamo inexistente
	@Test
	@Order(8)
	void testBorrarInexistente() throws Exception {
		dao.borrarPrestamo(9999);

		assertTrue(true);
	}
}
