package test;

import static org.junit.Assert.*;
import org.junit.Test;
import dao.UsuarioDAO;

public class TestUsuario {

	@Test
	public void testUsuarioCompleto() throws Exception {

		UsuarioDAO dao = new UsuarioDAO();

		dao.insertarUsuario(400, "Test", "test@test.com", 123456789);

		assertTrue(dao.existeUsuario(400)); // 1
		assertEquals(true, dao.existeUsuario(400)); // 2

		assertFalse(dao.existeUsuario(9999)); // 3

		dao.insertarUsuario(401, "Otro", "otro@test.com", 987654321);
		assertTrue(dao.existeUsuario(401)); // 4

		dao.borrarUsuario(401);
		assertFalse(dao.existeUsuario(401)); // 5

		assertTrue(dao.existeUsuario(400)); // 6

		dao.borrarUsuario(400);
		assertFalse(dao.existeUsuario(400)); // 7

		assertEquals(false, dao.existeUsuario(400)); // 8
	}
}