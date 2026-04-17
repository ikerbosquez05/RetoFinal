package interfaces;

import java.util.List;
import clases.Categoria;

/**
 * Interfaz que define las operaciones CRUD para la gestión de categorías.
 * <p>
 * Esta interfaz establece los métodos que deben implementar las clases DAO
 * encargadas de gestionar las categorías en la base de datos. Permite realizar
 * operaciones de consulta, inserción y eliminación.
 * </p>
 *
 * Las categorías se representan mediante objetos de la clase {@link Categoria}.
 *
 * @version 1.0
 */
public interface InterfazCategoria {

	/**
	 * Obtiene todas las categorías almacenadas en la base de datos.
	 * <p>
	 * Devuelve una lista de objetos {@link Categoria}, donde cada elemento contiene
	 * la información completa de una categoría.
	 * </p>
	 *
	 * @return una {@link List} de categorías.
	 * @throws Exception si ocurre un error durante la consulta.
	 */
	List<Categoria> listarCategorias() throws Exception;

	/**
	 * Inserta una nueva categoría en la base de datos.
	 * <p>
	 * Recibe un objeto {@link Categoria} con los datos necesarios para su
	 * almacenamiento.
	 * </p>
	 *
	 * @param c objeto categoría a insertar.
	 * @throws Exception si ocurre un error durante la inserción.
	 */
	void insertarCategoria(Categoria c) throws Exception;

	/**
	 * Elimina una categoría de la base de datos.
	 * <p>
	 * La eliminación se realiza mediante el identificador único de la categoría.
	 * </p>
	 *
	 * @param id identificador de la categoría a eliminar.
	 * @throws Exception si ocurre un error durante la eliminación.
	 */
	void borrarCategoria(int id) throws Exception;
}