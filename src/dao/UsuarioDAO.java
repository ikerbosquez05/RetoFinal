package dao;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

import interfaces.InterfazUsuario;
import modelo.AccesoBD;

/**
 * Clase DAO (Data Access Object) para la gestión de usuarios.
 * <p>
 * Implementa la interfaz {@link InterfazUsuario} y proporciona métodos para
 * realizar operaciones CRUD sobre la tabla USUARIO en la base de datos.
 * </p>
 *
 * Utiliza {@link AccesoBD} para establecer la conexión con la base de datos y
 * {@link PreparedStatement} para ejecutar consultas SQL seguras.
 *
 * @version 1.0
 * @see InterfazUsuario
 * @see AccesoBD
 */
public class UsuarioDAO implements InterfazUsuario {

	/** Objeto encargado de gestionar la conexión con la base de datos. */
	private AccesoBD conexion;

	/**
	 * Constructor de UsuarioDAO.
	 * <p>
	 * Inicializa el objeto de conexión a la base de datos.
	 * </p>
	 */
	public UsuarioDAO() {
		conexion = new AccesoBD();
	}

	/**
	 * Obtiene todos los usuarios almacenados en la base de datos.
	 * <p>
	 * Realiza una consulta a la tabla USUARIO y construye un mapa donde:
	 * <ul>
	 * <li>La clave es el ID del usuario.</li>
	 * <li>El valor es un array con nombre, email y teléfono.</li>
	 * </ul>
	 * </p>
	 *
	 * @return un {@link Map} con todos los usuarios.
	 * @throws Exception si ocurre un error durante la consulta.
	 */
	@Override
	public Map<Integer, String[]> verUsuarios() throws Exception {

		Map<Integer, String[]> usuarios = new TreeMap<>();

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM USUARIO");

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			int id = rs.getInt("ID_USUARIO");
			String nombre = rs.getString("NOMBRE");
			String email = rs.getString("EMAIL");
			int telefono = rs.getInt("TELEFONO");

			usuarios.put(id, new String[] { nombre, email, String.valueOf(telefono) });
		}

		con.close();

		return usuarios;
	}

	/**
	 * Inserta un nuevo usuario en la base de datos.
	 * <p>
	 * Ejecuta una sentencia SQL INSERT con los datos proporcionados.
	 * </p>
	 *
	 * @param id       identificador único del usuario.
	 * @param nombre   nombre del usuario.
	 * @param email    correo electrónico del usuario.
	 * @param telefono número de teléfono del usuario.
	 * @throws Exception si ocurre un error durante la inserción.
	 */
	@Override
	public void insertarUsuario(int id, String nombre, String email, int telefono) throws Exception {

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("INSERT INTO USUARIO VALUES (?,?,?,?)");

		stmt.setInt(1, id);
		stmt.setString(2, nombre);
		stmt.setString(3, email);
		stmt.setInt(4, telefono);

		stmt.executeUpdate();

		con.close();
	}

	/**
	 * Elimina un usuario de la base de datos.
	 * <p>
	 * Ejecuta una sentencia SQL DELETE utilizando el ID del usuario.
	 * </p>
	 *
	 * @param id identificador del usuario a eliminar.
	 * @throws Exception si ocurre un error durante la eliminación.
	 */
	@Override
	public void borrarUsuario(int id) throws Exception {

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("DELETE FROM USUARIO WHERE ID_USUARIO=?");

		stmt.setInt(1, id);

		stmt.executeUpdate();

		con.close();
	}

	/**
	 * Modifica los datos de un usuario existente.
	 * <p>
	 * Ejecuta una sentencia SQL UPDATE para actualizar nombre, email y teléfono de
	 * un usuario identificado por su ID.
	 * </p>
	 *
	 * @param id       identificador del usuario.
	 * @param nombre   nuevo nombre del usuario.
	 * @param email    nuevo correo electrónico.
	 * @param telefono nuevo número de teléfono.
	 * @throws Exception si ocurre un error durante la modificación.
	 */
	@Override
	public void modificarUsuario(int id, String nombre, String email, int telefono) throws Exception {

		Connection con = conexion.conectar();

		PreparedStatement stmt = con
				.prepareStatement("UPDATE USUARIO SET NOMBRE=?, EMAIL=?, TELEFONO=? WHERE ID_USUARIO=?");

		stmt.setString(1, nombre);
		stmt.setString(2, email);
		stmt.setInt(3, telefono);
		stmt.setInt(4, id);

		stmt.executeUpdate();

		con.close();
	}

	/**
	 * Comprueba si un usuario existe en la base de datos.
	 * <p>
	 * Ejecuta una función almacenada {@code ExisteUsuario(?)} que devuelve 1 si el
	 * usuario existe o 0 en caso contrario.
	 * </p>
	 *
	 * @param id identificador del usuario.
	 * @return {@code true} si el usuario existe; {@code false} en caso contrario.
	 * @throws Exception si ocurre un error durante la consulta.
	 */
	public boolean existeUsuario(int id) throws Exception {

		boolean existe = false;

		AccesoBD bd = new AccesoBD();
		Connection con = bd.conectar();

		PreparedStatement ps = con.prepareStatement("SELECT ExisteUsuario(?)");
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			existe = rs.getInt(1) == 1;
		}

		rs.close();
		ps.close();
		con.close();

		return existe;
	}
}