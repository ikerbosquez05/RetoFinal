package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import modelo.AccesoBD;
import ventanas.Libro;

/**
 * Clase de pruebas unitarias para la capa de acceso a datos (AccesoBD)
 * Se utilizan aserciones de JUnit para validar el comportamiento del sistema.
 */
class LibrosTest {

	/**
	 * Comprueba que un libro existente en la base de datos devuelve true.
	 */
	@Test
	void testAssertTrue() throws Exception {
	    AccesoBD bd = new AccesoBD();

	    // Comprueba existencia de un libro con ID 1
	    boolean existe = bd.existeLibro(1);

	    assertTrue(existe, "El libro debería existir");
	}

	/**
	 * Comprueba que un libro inexistente devuelve false.
	 */
	@Test
	void testAssertFalse() throws Exception {
	    AccesoBD bd = new AccesoBD();

	    // ID negativo no debería existir en la BD
	    boolean existe = bd.existeLibro(-1);

	    assertFalse(existe, "El libro NO debería existir");
	}

	/**
	 * Comprueba que un libro insertado se recupera correctamente
	 * y que su título coincide con el esperado.
	 */
	@Test
	void testAssertEquals() throws Exception {
	    AccesoBD bd = new AccesoBD();

	    int id = 5000;

	    // Si no existe, lo inserta para poder probarlo
	    if (!bd.existeLibro(id)) {
	        bd.insertarLibro(id, "Libro Test", "Autor", "2020-01-01");
	    }

	    // Obtiene todos los libros
	    Map<Integer, Libro> libros = bd.verLibros();

	    // Verifica que el título coincide con el esperado
	    assertEquals("Libro Test",
	            libros.get(id).getTitulo(),
	            "El título debería coincidir");
	}

	/**
	 * Comprueba que acceder a un libro inexistente devuelve null.
	 */
	@Test
	void testAssertNull() throws Exception {
	    AccesoBD bd = new AccesoBD();

	    Map<Integer, Libro> libros = bd.verLibros();

	    // ID inexistente
	    assertNull(libros.get(-1), "No debería existir ese libro");
	}

	/**
	 * Comprueba que la estructura de datos de libros no es null.
	 */
	@Test
	void testAssertNotNull() throws Exception {
	    AccesoBD bd = new AccesoBD();

	    Map<Integer, Libro> libros = bd.verLibros();

	    assertNotNull(libros, "La lista de libros no debe ser null");
	}

	/**
	 * Comprueba que dos referencias apuntan al mismo objeto.
	 */
	@Test
	void testAssertSame() {
	    Libro libro = new Libro("Titulo", "Autor", "2020-01-01");

	    // Ambas variables apuntan al mismo objeto
	    Libro referencia = libro;

	    assertSame(libro, referencia, "Deben ser el mismo objeto");
	}

	/**
	 * Comprueba que dos objetos distintos no son la misma referencia.
	 */
	@Test
	void testAssertNotSame() {
	    Libro libro1 = new Libro("Titulo", "Autor", "2020-01-01");
	    Libro libro2 = new Libro("Titulo", "Autor", "2020-01-01");

	    assertNotSame(libro1, libro2, "NO deben ser el mismo objeto");
	}

	/**
	 * Comprueba que el sistema lanza una excepción
	 * cuando el formato de fecha es incorrecto.
	 */
	@Test
	void testAssertThrows() {
	    AccesoBD bd = new AccesoBD();

	    assertThrows(Exception.class, () -> {
	        bd.insertarLibro(1, "Libro", "Autor", "fecha-mal");
	    });
	}
}