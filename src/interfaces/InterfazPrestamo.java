package interfaces;

import java.util.Map;

/**
 * Interfaz que define las operaciones CRUD para la gestión de préstamos.
 * <p>
 * Esta interfaz establece los métodos que deben implementar las clases DAO
 * encargadas de gestionar los préstamos en la base de datos. Permite realizar
 * operaciones de consulta, inserción, modificación y eliminación.
 * </p>
 *
 * Cada préstamo se representa mediante un identificador único y un array de
 * datos que incluye información del usuario, libro y fechas asociadas al
 * préstamo.
 *
 * @version 1.0
 */
public interface InterfazPrestamo {

	/**
	 * Obtiene todos los préstamos almacenados en la base de datos.
	 * <p>
	 * Devuelve un mapa donde:
	 * <ul>
	 * <li>La clave ({@code Integer}) es el ID del préstamo.</li>
	 * <li>El valor ({@code String[]}) contiene los datos del préstamo: usuario,
	 * libro, fecha de préstamo, fecha límite y fecha de devolución.</li>
	 * </ul>
	 * </p>
	 *
	 * @return un {@link Map} con todos los préstamos registrados.
	 * @throws Exception si ocurre un error durante la consulta.
	 */
	Map<Integer, String[]> verPrestamos() throws Exception;

	/**
	 * Inserta un nuevo préstamo en la base de datos.
	 * <p>
	 * Recibe los datos necesarios para registrar un préstamo: identificador,
	 * usuario, libro y fechas asociadas.
	 * </p>
	 *
	 * @param idPrestamo      identificador único del préstamo.
	 * @param idUsuario       identificador del usuario que realiza el préstamo.
	 * @param idLibro         identificador del libro prestado.
	 * @param fechaPrestamo   fecha en la que se realiza el préstamo.
	 * @param fechaLimite     fecha límite de devolución.
	 * @param fechaDevolucion fecha en la que se devuelve el libro (puede ser
	 *                        {@code null}).
	 * @throws Exception si ocurre un error durante la inserción.
	 */
	void insertarPrestamo(int idPrestamo, int idUsuario, int idLibro, String fechaPrestamo, String fechaLimite,
			String fechaDevolucion) throws Exception;

	/**
	 * Elimina un préstamo de la base de datos.
	 * <p>
	 * La eliminación se realiza mediante el identificador del préstamo.
	 * </p>
	 *
	 * @param idPrestamo identificador del préstamo a eliminar.
	 * @throws Exception si ocurre un error durante la eliminación.
	 */
	void borrarPrestamo(int idPrestamo) throws Exception;

	/**
	 * Modifica los datos de un préstamo existente.
	 * <p>
	 * Permite actualizar la información del usuario, libro y fechas asociadas a un
	 * préstamo identificado por su ID.
	 * </p>
	 *
	 * @param idPrestamo      identificador del préstamo a modificar.
	 * @param idUsuario       nuevo identificador del usuario.
	 * @param idLibro         nuevo identificador del libro.
	 * @param fechaPrestamo   nueva fecha de préstamo.
	 * @param fechaLimite     nueva fecha límite de devolución.
	 * @param fechaDevolucion nueva fecha de devolución (puede ser {@code null}).
	 * @throws Exception si ocurre un error durante la modificación.
	 */
	void modificarPrestamo(int idPrestamo, int idUsuario, int idLibro, String fechaPrestamo, String fechaLimite,
			String fechaDevolucion) throws Exception;
}