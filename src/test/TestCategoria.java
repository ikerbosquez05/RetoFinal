package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import dao.CategoriaDAO;
import clases.Categoria;

import java.util.List;

/**
 * Clase de pruebas unitarias para la gestión de categorías.
 * <p>
 * Esta clase valida el correcto funcionamiento del DAO de categorías
 * ({@link CategoriaDAO}), comprobando la correcta obtención y estructura de los
 * datos almacenados en la base de datos.
 * </p>
 * 
 * Las pruebas verifican:
 * <ul>
 * <li>Que la lista de categorías no sea nula.</li>
 * <li>Que la lista contenga elementos.</li>
 * <li>Que los datos de cada categoría sean válidos.</li>
 * </ul>
 *
 * @version 1.0
 * @see CategoriaDAO
 * @see Categoria
 */
public class TestCategoria {

	/** DAO utilizado para acceder a los datos de categorías. */
	private CategoriaDAO dao;

	/**
	 * Inicializa el DAO antes de cada prueba.
	 * <p>
	 * Este método se ejecuta automáticamente antes de cada test, asegurando una
	 * instancia limpia del DAO.
	 * </p>
	 */
	@Before
	public void setUp() {
		dao = new CategoriaDAO();
	}

	/**
	 * 1. Verifica que la lista de categorías no sea nula.
	 * <p>
	 * Comprueba que el método {@code listarCategorias()} devuelve una colección
	 * válida.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testListaNoNull() throws Exception {
		List<Categoria> lista = dao.listarCategorias();
		assertNotNull(lista);
	}

	/**
	 * 2. Verifica que la lista de categorías no esté vacía.
	 * <p>
	 * Asegura que existen registros en la base de datos.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testListaNoVacia() throws Exception {
		List<Categoria> lista = dao.listarCategorias();
		assertTrue(lista.size() > 0);
	}

	/**
	 * 3. Comprueba que existe al menos una categoría.
	 * <p>
	 * Similar a la prueba anterior, pero usando una condición mínima explícita (>=
	 * 1).
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testHayCategorias() throws Exception {
		List<Categoria> lista = dao.listarCategorias();
		assertTrue(lista.size() >= 1);
	}

	/**
	 * 4. Verifica que el primer elemento de la lista no sea nulo.
	 * <p>
	 * Asegura que los objetos obtenidos están correctamente instanciados.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testPrimerElementoNoNull() throws Exception {
		Categoria c = dao.listarCategorias().get(0);
		assertNotNull(c);
	}

	/**
	 * 5. Comprueba que el ID de la categoría sea positivo.
	 * <p>
	 * Garantiza que los identificadores son válidos y mayores que cero.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testIdPositivo() throws Exception {
		Categoria c = dao.listarCategorias().get(0);
		assertTrue(c.getIdCategoria() > 0);
	}

	/**
	 * 6. Verifica que el tipo de categoría sea válido.
	 * <p>
	 * Comprueba que el atributo {@code tipoCategoria} no sea nulo, lo que indica
	 * que pertenece a un ENUM válido.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testTipoCategoriaEnum() throws Exception {
		Categoria c = dao.listarCategorias().get(0);
		assertNotNull(c.getTipoCategoria());
	}

	/**
	 * 7. Comprueba que la descripción de la categoría no esté vacía.
	 * <p>
	 * Asegura que el campo de texto contiene información válida.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testDescripcionNoVacia() throws Exception {
		Categoria c = dao.listarCategorias().get(0);
		assertFalse(c.getDescripcion().isEmpty());
	}

	/**
	 * 8. Verifica que el objeto obtenido sea una instancia de Categoria.
	 * <p>
	 * Comprueba el tipo del objeto devuelto por el DAO.
	 * </p>
	 *
	 * @throws Exception si ocurre un error en la consulta.
	 */
	@Test
	public void testInstanciaCategoria() throws Exception {
		Categoria c = dao.listarCategorias().get(0);
		assertTrue(c instanceof Categoria);
	}
}