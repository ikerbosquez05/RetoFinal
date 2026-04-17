package test;

import static org.junit.Assert.*;
import org.junit.Test;
import dao.UsuarioDAO;

/**
 * Clase de pruebas unitarias para la gestión de usuarios.
 * <p>
 * Esta clase verifica el correcto funcionamiento de las operaciones básicas del
 * DAO de usuarios ({@link UsuarioDAO}), centrándose principalmente en:
 * <ul>
 * <li>Inserción de usuarios en la base de datos.</li>
 * <li>Comprobación de existencia de usuarios.</li>
 * <li>Eliminación de usuarios.</li>
 * </ul>
 * Se utilizan aserciones de JUnit para validar el comportamiento esperado en
 * cada operación.
 * </p>
 *
 * @version 1.0
 * @see UsuarioDAO
 */
public class TestUsuario {

	/**
	 * Prueba completa del ciclo de vida de un usuario en la base de datos.
	 * <p>
	 * Este test realiza una secuencia de operaciones que incluyen:
	 * <ol>
	 * <li>Inserción de un usuario.</li>
	 * <li>Verificación de su existencia.</li>
	 * <li>Comprobación de un usuario inexistente.</li>
	 * <li>Inserción de un segundo usuario.</li>
	 * <li>Eliminación del segundo usuario.</li>
	 * <li>Verificación de persistencia del primer usuario.</li>
	 * <li>Eliminación del primer usuario.</li>
	 * <li>Verificación final de inexistencia.</li>
	 * </ol>
	 * Cada paso se valida mediante aserciones de JUnit.
	 * </p>
	 *
	 * @throws Exception si ocurre algún error durante las operaciones de base de
	 *                   datos.
	 */
	@Test
	public void testUsuarioCompleto() throws Exception {

		// Instancia del DAO para acceder a la base de datos de usuarios
		UsuarioDAO dao = new UsuarioDAO();

		// Inserta un usuario de prueba con ID 400
		dao.insertarUsuario(400, "Test", "test@test.com", 123456789);

		// 1. Verifica que el usuario existe (debe ser true)
		assertTrue(dao.existeUsuario(400));

		// 2. Verifica explícitamente que devuelve true
		assertEquals(true, dao.existeUsuario(400));

		// 3. Verifica que un usuario inexistente devuelve false
		assertFalse(dao.existeUsuario(9999));

		// Inserta un segundo usuario con ID 401
		dao.insertarUsuario(401, "Otro", "otro@test.com", 987654321);

		// 4. Comprueba que el segundo usuario existe
		assertTrue(dao.existeUsuario(401));

		// Elimina el segundo usuario
		dao.borrarUsuario(401);

		// 5. Verifica que el usuario eliminado ya no existe
		assertFalse(dao.existeUsuario(401));

		// 6. Comprueba que el primer usuario sigue existiendo
		assertTrue(dao.existeUsuario(400));

		// Elimina el primer usuario
		dao.borrarUsuario(400);

		// 7. Verifica que el usuario 400 ha sido eliminado
		assertFalse(dao.existeUsuario(400));

		// 8. Verificación final usando assertEquals
		assertEquals(false, dao.existeUsuario(400));
	}
}