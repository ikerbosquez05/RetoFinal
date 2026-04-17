package dao;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

import interfaces.InterfazPrestamo;
import modelo.AccesoBD;

/**
 * Clase DAO (Data Access Object) para la gestión de préstamos.
 * <p>
 * Implementa la interfaz {@link InterfazPrestamo} y proporciona métodos para
 * realizar operaciones CRUD sobre los préstamos en la base de datos.
 * </p>
 *
 * Gestiona la información distribuida en las tablas PRESTAMO y CONTIENE,
 * incluyendo inserciones, modificaciones y consultas combinadas mediante JOIN.
 *
 * Utiliza transacciones para asegurar la integridad de los datos.
 *
 * @version 1.0
 * @see InterfazPrestamo
 * @see AccesoBD
 */
public class PrestamoDAO implements InterfazPrestamo {

	/** Objeto encargado de gestionar la conexión a la base de datos. */
	private AccesoBD conexion;

	/**
	 * Constructor de PrestamoDAO.
	 * <p>
	 * Inicializa el objeto de conexión a la base de datos.
	 * </p>
	 */
	public PrestamoDAO() {
		conexion = new AccesoBD();
	}

	/**
	 * Obtiene todos los préstamos almacenados en la base de datos.
	 * <p>
	 * Realiza una consulta combinada (JOIN) entre las tablas PRESTAMO y CONTIENE,
	 * devolviendo un mapa donde:
	 * <ul>
	 * <li>La clave es el ID del préstamo.</li>
	 * <li>El valor es un array con usuario, libro y fechas asociadas.</li>
	 * </ul>
	 * </p>
	 *
	 * @return un {@link Map} con todos los préstamos.
	 * @throws Exception si ocurre un error durante la consulta.
	 */
	@Override
	public Map<Integer, String[]> verPrestamos() throws Exception {

		Map<Integer, String[]> prestamos = new TreeMap<>();

		Connection con = conexion.conectar();

		String sql = "SELECT P.ID_PRESTAMO, P.ID_USUARIO, C.ID_LIBRO, "
				+ "P.FECHA_PRESTAMO, P.FECHA_LIMITE, C.FECHA_DEVOLUCION " + "FROM PRESTAMO P "
				+ "LEFT JOIN CONTIENE C ON P.ID_PRESTAMO = C.ID_PRESTAMO";

		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			int id = rs.getInt("ID_PRESTAMO");

			prestamos.put(id,
					new String[] { String.valueOf(rs.getInt("ID_USUARIO")), String.valueOf(rs.getInt("ID_LIBRO")),
							String.valueOf(rs.getDate("FECHA_PRESTAMO")), String.valueOf(rs.getDate("FECHA_LIMITE")),
							String.valueOf(rs.getDate("FECHA_DEVOLUCION")) });
		}

		con.close();

		return prestamos;
	}

	/**
	 * Inserta un nuevo préstamo en la base de datos.
	 * <p>
	 * Realiza dos operaciones dentro de una transacción:
	 * <ul>
	 * <li>Inserta el préstamo en la tabla PRESTAMO.</li>
	 * <li>Inserta o actualiza la relación en la tabla CONTIENE.</li>
	 * </ul>
	 * Si ocurre un error, se realiza rollback.
	 * </p>
	 *
	 * @param idPrestamo      identificador del préstamo.
	 * @param idUsuario       identificador del usuario.
	 * @param idLibro         identificador del libro.
	 * @param fechaPrestamo   fecha de inicio del préstamo.
	 * @param fechaLimite     fecha límite de devolución.
	 * @param fechaDevolucion fecha de devolución (puede ser null).
	 * @throws Exception si ocurre un error durante la operación.
	 */
	@Override
	public void insertarPrestamo(int idPrestamo, int idUsuario, int idLibro, String fechaPrestamo, String fechaLimite,
			String fechaDevolucion) throws Exception {

		Connection con = conexion.conectar();
		con.setAutoCommit(false);

		try {

			String sqlPrestamo = "INSERT INTO PRESTAMO VALUES (?,?,?,?)";
			PreparedStatement ps1 = con.prepareStatement(sqlPrestamo);

			ps1.setInt(1, idPrestamo);
			ps1.setInt(2, idUsuario);
			ps1.setDate(3, Date.valueOf(fechaPrestamo));
			ps1.setDate(4, Date.valueOf(fechaLimite));
			ps1.executeUpdate();

			insertarDevolucion(con, idPrestamo, idLibro, fechaDevolucion);

			con.commit();

		} catch (Exception e) {
			con.rollback();
			throw e;
		}

		con.close();
	}

	/**
	 * Elimina un préstamo de la base de datos.
	 *
	 * @param idPrestamo identificador del préstamo.
	 * @throws Exception si ocurre un error durante la eliminación.
	 */
	@Override
	public void borrarPrestamo(int idPrestamo) throws Exception {

		Connection con = conexion.conectar();

		PreparedStatement ps = con.prepareStatement("DELETE FROM PRESTAMO WHERE ID_PRESTAMO=?");

		ps.setInt(1, idPrestamo);
		ps.executeUpdate();

		con.close();
	}

	/**
	 * Modifica un préstamo existente.
	 * <p>
	 * Actualiza los datos tanto en la tabla PRESTAMO como en CONTIENE, utilizando
	 * una transacción para garantizar consistencia.
	 * </p>
	 *
	 * @param idPrestamo      identificador del préstamo.
	 * @param idUsuario       nuevo usuario.
	 * @param idLibro         nuevo libro.
	 * @param fechaPrestamo   nueva fecha de préstamo.
	 * @param fechaLimite     nueva fecha límite.
	 * @param fechaDevolucion nueva fecha de devolución (puede ser null).
	 * @throws Exception si ocurre un error durante la operación.
	 */
	@Override
	public void modificarPrestamo(int idPrestamo, int idUsuario, int idLibro, String fechaPrestamo, String fechaLimite,
			String fechaDevolucion) throws Exception {

		Connection con = conexion.conectar();
		con.setAutoCommit(false);

		try {

			PreparedStatement ps1 = con.prepareStatement(
					"UPDATE PRESTAMO SET ID_USUARIO=?, FECHA_PRESTAMO=?, FECHA_LIMITE=? WHERE ID_PRESTAMO=?");

			ps1.setInt(1, idUsuario);
			ps1.setDate(2, Date.valueOf(fechaPrestamo));
			ps1.setDate(3, Date.valueOf(fechaLimite));
			ps1.setInt(4, idPrestamo);
			ps1.executeUpdate();

			PreparedStatement ps2 = con
					.prepareStatement("UPDATE CONTIENE SET ID_LIBRO=?, FECHA_DEVOLUCION=? WHERE ID_PRESTAMO=?");

			ps2.setInt(1, idLibro);

			if (fechaDevolucion == null || fechaDevolucion.isEmpty()) {
				ps2.setNull(2, Types.DATE);
			} else {
				ps2.setDate(2, Date.valueOf(fechaDevolucion));
			}

			ps2.setInt(3, idPrestamo);
			ps2.executeUpdate();

			con.commit();

		} catch (Exception e) {
			con.rollback();
			throw e;
		}

		con.close();
	}

	/**
	 * Inserta o actualiza la relación préstamo-libro en la tabla CONTIENE.
	 * <p>
	 * Si el registro ya existe, se actualiza la fecha de devolución utilizando
	 * {@code ON DUPLICATE KEY UPDATE}.
	 * </p>
	 *
	 * @param con        conexión activa (dentro de una transacción).
	 * @param idPrestamo identificador del préstamo.
	 * @param idLibro    identificador del libro.
	 * @param fecha      fecha de devolución (puede ser null).
	 * @throws Exception si ocurre un error durante la operación.
	 */
	private void insertarDevolucion(Connection con, int idPrestamo, int idLibro, String fecha) throws Exception {

		PreparedStatement stmt = con.prepareStatement("INSERT INTO CONTIENE (ID_PRESTAMO, ID_LIBRO, FECHA_DEVOLUCION) "
				+ "VALUES (?, ?, ?) " + "ON DUPLICATE KEY UPDATE FECHA_DEVOLUCION = VALUES(FECHA_DEVOLUCION)");

		stmt.setInt(1, idPrestamo);
		stmt.setInt(2, idLibro);

		if (fecha == null || fecha.isEmpty()) {
			stmt.setNull(3, Types.DATE);
		} else {
			stmt.setDate(3, Date.valueOf(fecha));
		}

		stmt.executeUpdate();
	}
}