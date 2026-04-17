package interfaces;

import java.util.Map;

/**
 * Interfaz que define las operaciones CRUD para la gestión de usuarios.
 * <p>
 * Esta interfaz establece los métodos que deben implementar las clases DAO
 * encargadas de interactuar con la base de datos de usuarios. Permite realizar
 * operaciones de consulta, inserción, modificación y eliminación.
 * </p>
 *
 * Cada usuario se representa mediante un identificador único y un array de
 * datos que incluye nombre, email y teléfono.
 *
 * @version 1.0
 */
public interface InterfazUsuario {

	/**
	 * Obtiene todos los usuarios almacenados en la base de datos.
	 * <p>
	 * Devuelve un mapa donde:
	 * <ul>
	 * <li>La clave ({@code Integer}) es el ID del usuario.</li>
	 * <li>El valor ({@code String[]}) contiene los datos del usuario (nombre,
	 * email, teléfono).</li>
	 * </ul>
	 * </p>
	 *
	 * @return un {@link Map} con todos los usuarios registrados.
	 * @throws Exception si ocurre un error durante la consulta.
	 */
	Map<Integer, String[]> verUsuarios() throws Exception;

	/**
	 * Inserta un nuevo usuario en la base de datos.
	 * <p>
	 * Recibe los datos necesarios para crear un usuario: identificador, nombre,
	 * correo electrónico y teléfono.
	 * </p>
	 *
	 * @param id       identificador único del usuario.
	 * @param nombre   nombre del usuario.
	 * @param email    correo electrónico del usuario.
	 * @param telefono número de teléfono del usuario.
	 * @throws Exception si ocurre un error durante la inserción.
	 */
	void insertarUsuario(int id, String nombre, String email, int telefono) throws Exception;

	/**
	 * Elimina un usuario de la base de datos.
	 * <p>
	 * La eliminación se realiza utilizando el identificador único del usuario.
	 * </p>
	 *
	 * @param id identificador del usuario a eliminar.
	 * @throws Exception si ocurre un error durante la eliminación.
	 */
	void borrarUsuario(int id) throws Exception;

	/**
	 * Modifica los datos de un usuario existente.
	 * <p>
	 * Permite actualizar el nombre, email y teléfono de un usuario identificado por
	 * su ID.
	 * </p>
	 *
	 * @param id       identificador del usuario a modificar.
	 * @param nombre   nuevo nombre del usuario.
	 * @param email    nuevo correo electrónico.
	 * @param telefono nuevo número de teléfono.
	 * @throws Exception si ocurre un error durante la modificación.
	 */
	void modificarUsuario(int id, String nombre, String email, int telefono) throws Exception;
}