package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;

import modelo.AccesoBD;

/**
 * Clase DAO (Data Access Object) para la gestión de la relación entre préstamos
 * y libros (tabla CONTIENE).
 * <p>
 * Esta clase permite insertar o actualizar la información relacionada con la
 * devolución de un libro dentro de un préstamo.
 * </p>
 *
 * Utiliza la tabla CONTIENE para registrar:
 * <ul>
 * <li>El préstamo asociado.</li>
 * <li>El libro prestado.</li>
 * <li>La fecha de devolución.</li>
 * </ul>
 *
 * Incluye lógica para evitar duplicados mediante la cláusula
 * {@code ON DUPLICATE KEY UPDATE}.
 *
 * @version 1.0
 * @see AccesoBD
 */
public class ContieneDAO {

	/** Objeto encargado de gestionar la conexión a la base de datos. */
	private AccesoBD conexion;

	/**
	 * Constructor de ContieneDAO.
	 * <p>
	 * Inicializa el objeto de conexión a la base de datos.
	 * </p>
	 */
	public ContieneDAO() {
		conexion = new AccesoBD();
	}

	/**
	 * Inserta o actualiza la fecha de devolución de un préstamo.
	 * <p>
	 * Realiza una inserción en la tabla CONTIENE con los datos proporcionados. Si
	 * ya existe un registro con la misma clave, se actualiza la fecha de
	 * devolución.
	 * </p>
	 *
	 * <p>
	 * Comportamiento:
	 * <ul>
	 * <li>Si {@code fecha} es null o vacía, se inserta como valor NULL.</li>
	 * <li>Si {@code fecha} contiene valor, se convierte a {@link Date}.</li>
	 * </ul>
	 * </p>
	 *
	 * @param idPrestamo identificador del préstamo.
	 * @param idLibro    identificador del libro.
	 * @param fecha      fecha de devolución (puede ser null o vacía).
	 * @throws Exception si ocurre un error durante la operación.
	 */
	public void insertarDevolucion(int idPrestamo, int idLibro, String fecha) throws Exception {

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("INSERT INTO CONTIENE (ID_PRESTAMO, ID_LIBRO, FECHA_DEVOLUCION) "
				+ "VALUES (?, ?, ?) " + "ON DUPLICATE KEY UPDATE FECHA_DEVOLUCION = VALUES(FECHA_DEVOLUCION)");

		stmt.setInt(1, idPrestamo);
		stmt.setInt(2, idLibro);

		// Gestión de fecha (null o válida)
		if (fecha == null || fecha.trim().isEmpty()) {
			stmt.setNull(3, Types.DATE);
		} else {
			stmt.setDate(3, Date.valueOf(fecha));
		}

		stmt.executeUpdate();

		con.close();
	}
}