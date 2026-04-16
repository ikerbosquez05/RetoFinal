package test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import dao.LibroDAO;
import clases.Libro;

/**
 * Tests unitarios para LibroDAO
 */
public class LibrosTest {

	/**
	 * Comprueba que un libro existente devuelve true
	 */
	@Test
	public void testAssertTrue() throws Exception {
		LibroDAO dao = new LibroDAO();

		boolean existe = dao.existeLibro(1);

		assertTrue(existe);
	}

	/**
	 * Comprueba que un libro inexistente devuelve false
	 */
	@Test
	public void testAssertFalse() throws Exception {
		LibroDAO dao = new LibroDAO();

		boolean existe = dao.existeLibro(-1);

		assertFalse(existe);
	}

	/**
	 * Comprueba inserción y lectura
	 */
	@Test
	public void testAssertEquals() throws Exception {
		LibroDAO dao = new LibroDAO();

		int id = 5000;

		if (!dao.existeLibro(id)) {
			dao.insertarLibro(id, "Libro Test", "Autor", "2020-01-01");
		}

		Map<Integer, String[]> libros = dao.verLibros();

		assertEquals("Libro Test", libros.get(id)[0]);
	}

	/**
	 * Comprueba null
	 */
	@Test
	public void testAssertNull() throws Exception {
		LibroDAO dao = new LibroDAO();

		Map<Integer, String[]> libros = dao.verLibros();

		assertNull(libros.get(-1));
	}

	/**
	 * Comprueba que no es null
	 */
	@Test
	public void testAssertNotNull() throws Exception {
		LibroDAO dao = new LibroDAO();

		Map<Integer, String[]> libros = dao.verLibros();

		assertNotNull(libros);
	}

	/**
	 * Comprueba referencias iguales
	 */
	@Test
	public void testAssertSame() {
		Libro libro = new Libro(1, "Titulo", "Autor", "2020-01-01");

		Libro ref = libro;

		assertSame(libro, ref);
	}

	/**
	 * Comprueba referencias distintas
	 */
	@Test
	public void testAssertNotSame() {
		Libro libro1 = new Libro(1, "Titulo", "Autor", "2020-01-01");
		Libro libro2 = new Libro(2, "Titulo", "Autor", "2020-01-01");

		assertNotSame(libro1, libro2);
	}

	/**
	 * Comprueba excepción
	 */
	@Test
	public void testAssertThrows() {

		try {
			LibroDAO dao = new LibroDAO();
			dao.insertarLibro(1, "Libro", "Autor", "fecha-mal");
			fail("Debería lanzar excepción");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}