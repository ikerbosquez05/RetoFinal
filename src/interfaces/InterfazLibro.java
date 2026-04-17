package interfaces;

import java.util.Map;

/**
 * Interfaz que define las operaciones CRUD para la gestión de libros.
 * <p>
 * Esta interfaz establece los métodos que deben implementar las clases DAO
 * encargadas de gestionar los libros en la base de datos. Permite realizar
 * operaciones de consulta, inserción, modificación, eliminación y verificación
 * de existencia.
 * </p>
 *
 * Cada libro se representa mediante un identificador único y un array de datos
 * que incluye título, autor y fecha de publicación.
 *
 * @version 1.0
 */
public interface InterfazLibro {

	/**
	 * Obtiene todos los libros almacenados en la base de datos.
	 * <p>
	 * Devuelve un mapa donde:
	 * <ul>
	 * <li>La clave ({@code Integer}) es el ID del libro.</li>
	 * <li>El valor ({@code String[]}) contiene los datos del libro: título, autor y
	 * fecha.</li>
	 * </ul>
	 * </p>
	 *
	 * @return un {@link Map} con todos los libros registrados.
	 * @throws Exception si ocurre un error durante la consulta.
	 */
	Map<Integer, String[]> verLibros() throws Exception;

	/**
	 * Inserta un nuevo libro en la base de datos.
	 * <p>
	 * Recibe los datos necesarios para registrar un libro: identificador, título,
	 * autor y fecha de publicación.
	 * </p>
	 *
	 * @param id     identificador único del libro.
	 * @param titulo título del libro.
	 * @param autor  autor del libro.
	 * @param fecha  fecha de publicación del libro.
	 * @throws Exception si ocurre un error durante la inserción.
	 */
	void insertarLibro(int id, String titulo, String autor, String fecha) throws Exception;

	/**
	 * Elimina un libro de la base de datos.
	 * <p>
	 * La eliminación se realiza utilizando el identificador único del libro.
	 * </p>
	 *
	 * @param id identificador del libro a eliminar.
	 * @throws Exception si ocurre un error durante la eliminación.
	 */
	void borrarLibro(int id) throws Exception;

	/**
	 * Modifica los datos de un libro existente.
	 * <p>
	 * Permite actualizar el título, autor y fecha de un libro identificado por su
	 * ID.
	 * </p>
	 *
	 * @param id     identificador del libro a modificar.
	 * @param titulo nuevo título del libro.
	 * @param autor  nuevo autor del libro.
	 * @param fecha  nueva fecha de publicación.
	 * @throws Exception si ocurre un error durante la modificación.
	 */
	void modificarLibro(int id, String titulo, String autor, String fecha) throws Exception;

	/**
	 * Comprueba si un libro existe en la base de datos.
	 * <p>
	 * Permite verificar la existencia de un libro mediante su identificador, siendo
	 * útil para evitar duplicados o validar operaciones.
	 * </p>
	 *
	 * @param id identificador del libro.
	 * @return {@code true} si el libro existe; {@code false} en caso contrario.
	 * @throws Exception si ocurre un error durante la consulta.
	 */
	boolean existeLibro(int id) throws Exception;
}