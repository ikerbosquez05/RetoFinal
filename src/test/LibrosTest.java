package test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import dao.LibroDAO;
import clases.Libro;

/**
 * Clase de pruebas unitarias para la gestión de libros.
 * <p>
 * Esta clase valida el correcto funcionamiento del DAO de libros
 * ({@link LibroDAO}), comprobando distintos comportamientos mediante las
 * aserciones de JUnit:
 * <ul>
 * <li>Verificación de existencia de libros.</li>
 * <li>Inserción y recuperación de datos.</li>
 * <li>Comprobación de valores nulos y no nulos.</li>
 * <li>Comparación de referencias de objetos.</li>
 * <li>Gestión de excepciones.</li>
 * </ul>
 * </p>
 *
 * @version 1.0
 * @see LibroDAO
 * @see Libro
 */
public class LibrosTest {

	/**
	 * Comprueba que un libro existente devuelve {@code true}.
	 * <p>
	 * Se consulta un libro con ID conocido (por ejemplo, 1) y se verifica que el
	 * DAO indica correctamente su existencia.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testAssertTrue() throws Exception {
		LibroDAO dao = new LibroDAO();

		boolean existe = dao.existeLibro(1);

		assertTrue(existe);
	}

	/**
	 * Comprueba que un libro inexistente devuelve {@code false}.
	 * <p>
	 * Se utiliza un ID inválido (-1) para verificar que el DAO responde
	 * correctamente indicando que no existe.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testAssertFalse() throws Exception {
		LibroDAO dao = new LibroDAO();

		boolean existe = dao.existeLibro(-1);

		assertFalse(existe);
	}

	/**
	 * Comprueba la inserción de un libro y su posterior lectura.
	 * <p>
	 * Inserta un libro de prueba si no existe previamente y luego verifica que el
	 * título almacenado coincide con el esperado.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la operación.
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
	 * Comprueba que un valor inexistente devuelve {@code null}.
	 * <p>
	 * Se intenta acceder a un libro con ID inexistente y se verifica que el
	 * resultado sea nulo.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testAssertNull() throws Exception {
		LibroDAO dao = new LibroDAO();

		Map<Integer, String[]> libros = dao.verLibros();

		assertNull(libros.get(-1));
	}

	/**
	 * Comprueba que la colección de libros no es {@code null}.
	 * <p>
	 * Garantiza que el DAO devuelve una estructura válida.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testAssertNotNull() throws Exception {
		LibroDAO dao = new LibroDAO();

		Map<Integer, String[]> libros = dao.verLibros();

		assertNotNull(libros);
	}

	/**
	 * Comprueba que dos referencias apuntan al mismo objeto.
	 * <p>
	 * Se asigna una referencia a otra y se verifica que ambas apuntan a la misma
	 * instancia en memoria.
	 * </p>
	 */
	@Test
	public void testAssertSame() {
		Libro libro = new Libro(1, "Titulo", "Autor", "2020-01-01");

		Libro ref = libro;

		assertSame(libro, ref);
	}

	/**
	 * Comprueba que dos objetos distintos no son la misma referencia.
	 * <p>
	 * Se crean dos objetos independientes y se verifica que no ocupan la misma
	 * posición en memoria.
	 * </p>
	 */
	@Test
	public void testAssertNotSame() {
		Libro libro1 = new Libro(1, "Titulo", "Autor", "2020-01-01");
		Libro libro2 = new Libro(2, "Titulo", "Autor", "2020-01-01");

		assertNotSame(libro1, libro2);
	}

	/**
	 * Comprueba que se lanza una excepción en caso de datos inválidos.
	 * <p>
	 * Se intenta insertar un libro con una fecha incorrecta, esperando que se
	 * produzca una excepción. Si no se lanza, la prueba falla.
	 * </p>
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