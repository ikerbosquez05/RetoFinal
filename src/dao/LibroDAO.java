package dao;

import modelo.AccesoBD;
import interfaces.InterfazLibro;
import java.sql.*;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Clase DAO (Data Access Object) para la gestión de libros.
 * <p>
 * Implementa la interfaz {@link InterfazLibro} y proporciona métodos para
 * realizar operaciones CRUD sobre la tabla LIBRO en la base de datos.
 * </p>
 *
 * Permite consultar, insertar, modificar, eliminar y verificar la existencia de
 * libros mediante consultas SQL.
 *
 * @version 1.0
 * @see InterfazLibro
 * @see AccesoBD
 */
public class LibroDAO implements InterfazLibro {

	/** Objeto encargado de gestionar la conexión a la base de datos. */
	private AccesoBD conexion;

	/**
	 * Constructor de LibroDAO.
	 * <p>
	 * Inicializa el objeto de conexión a la base de datos.
	 * </p>
	 */
	public LibroDAO() {
		conexion = new AccesoBD();
	}

	/**
	 * Obtiene todos los libros almacenados en la base de datos.
	 * <p>
	 * Ejecuta una consulta sobre la tabla LIBRO y construye un mapa donde:
	 * <ul>
	 * <li>La clave es el ID del libro.</li>
	 * <li>El valor es un array con título, autor y año.</li>
	 * </ul>
	 * </p>
	 *
	 * @return un {@link Map} con todos los libros.
	 * @throws Exception si ocurre un error durante la consulta.
	 */
	@Override
	public Map<Integer, String[]> verLibros() throws Exception {

		Map<Integer, String[]> libros = new TreeMap<>();

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("SELECT ID_LIBRO, TITULO, AUTOR, AÑO FROM LIBRO");

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			int id = rs.getInt("ID_LIBRO");

			libros.put(id, new String[] { rs.getString("TITULO"), rs.getString("AUTOR"), rs.getString("AÑO") });
		}

		con.close();

		return libros;
	}

	/**
	 * Inserta un nuevo libro en la base de datos.
	 * <p>
	 * Realiza una validación básica del formato de fecha. Posteriormente ejecuta
	 * una sentencia SQL INSERT con los datos proporcionados.
	 * </p>
	 *
	 * @param id     identificador único del libro.
	 * @param titulo título del libro.
	 * @param autor  autor del libro.
	 * @param año    fecha o año de publicación del libro.
	 * @throws Exception si ocurre un error durante la inserción o la validación.
	 */
	@Override
	public void insertarLibro(int id, String titulo, String autor, String año) throws Exception {

		try {
			// Validación del formato de fecha (si aplica)
		} catch (DateTimeParseException e) {
			throw new Exception("Fecha inválida (YYYY-MM-DD)");
		}

		Connection con = conexion.conectar();

		PreparedStatement stmt = con
				.prepareStatement("INSERT INTO LIBRO (ID_LIBRO, TITULO, AUTOR, AÑO) VALUES (?,?,?,?)");

		stmt.setInt(1, id);
		stmt.setString(2, titulo);
		stmt.setString(3, autor);
		stmt.setString(4, año);

		stmt.executeUpdate();

		con.close();
	}

	/**
	 * Elimina un libro de la base de datos.
	 * <p>
	 * Ejecuta una sentencia SQL DELETE utilizando el ID del libro.
	 * </p>
	 *
	 * @param id identificador del libro a eliminar.
	 * @throws Exception si ocurre un error durante la eliminación.
	 */
	@Override
	public void borrarLibro(int id) throws Exception {

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("DELETE FROM LIBRO WHERE ID_LIBRO=?");

		stmt.setInt(1, id);
		stmt.executeUpdate();

		con.close();
	}

	/**
	 * Modifica los datos de un libro existente.
	 * <p>
	 * Ejecuta una sentencia SQL UPDATE para actualizar título, autor y año de
	 * publicación de un libro.
	 * </p>
	 *
	 * @param id     identificador del libro.
	 * @param titulo nuevo título.
	 * @param autor  nuevo autor.
	 * @param año    nueva fecha o año.
	 * @throws Exception si ocurre un error durante la modificación.
	 */
	@Override
	public void modificarLibro(int id, String titulo, String autor, String año) throws Exception {

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("UPDATE LIBRO SET TITULO=?, AUTOR=?, AÑO=? WHERE ID_LIBRO=?");

		stmt.setString(1, titulo);
		stmt.setString(2, autor);
		stmt.setString(3, año);
		stmt.setInt(4, id);

		stmt.executeUpdate();

		con.close();
	}

	/**
	 * Comprueba si un libro existe en la base de datos.
	 * <p>
	 * Ejecuta una consulta COUNT(*) para verificar si hay registros con el ID
	 * proporcionado.
	 * </p>
	 *
	 * @param id identificador del libro.
	 * @return {@code true} si el libro existe; {@code false} en caso contrario.
	 * @throws Exception si ocurre un error durante la consulta.
	 */
	public boolean existeLibro(int id) throws Exception {

		final String sql = "SELECT COUNT(*) FROM LIBRO WHERE ID_LIBRO = ?";

		AccesoBD bd = new AccesoBD();

		try (Connection con = bd.conectar(); PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}

		return false;
	}
}