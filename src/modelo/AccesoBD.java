package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import ventanas.Excepcion;
import ventanas.Libro;
import ventanas.LibrosITF;

public class AccesoBD implements LibrosITF {

	// Archivo de configuración donde se guardan los datos de conexión a la BD
	private ResourceBundle configFile;

	// Datos de conexión a la base de datos
	private String urlBD;
	private String userBD;
	private String passwordBD;

	// Consultas SQL reutilizables
	final String SQLVERLIBROS = "SELECT * FROM LIBRO";
	final String SQLINSERTARLIBRO = "INSERT INTO LIBRO (ID_LIBRO,TITULO,AUTOR,AÑO) VALUES (?,?,?,?)";
	final String SQLBORRARRELACION = "DELETE FROM CONTIENE WHERE ID_LIBRO=?";
	final String SQLBORRARLIBRO = "DELETE FROM LIBRO WHERE ID_LIBRO=?";

	// Constructor: carga los datos de conexión desde el fichero de configuración
	public AccesoBD() {
		this.configFile = ResourceBundle.getBundle("configClase");
		this.urlBD = this.configFile.getString("Conn");
		this.userBD = this.configFile.getString("DBUser");
		this.passwordBD = this.configFile.getString("DBPass");
	}

	/**
	 * Obtiene todos los libros de la base de datos
	 * y los devuelve en un Map ordenado por ID.
	 */
	@Override
	public Map<Integer, Libro> verLibros() throws Exception {

		// TreeMap ordena automáticamente por clave (ID)
		Map<Integer, Libro> libros = new TreeMap<>();

		// Para cerrar automáticamente conexión, statement y resultset
		try (Connection con = DriverManager.getConnection(urlBD, userBD, passwordBD);
				PreparedStatement stmt = con.prepareStatement(SQLVERLIBROS);
				ResultSet rs = stmt.executeQuery()) {

			// Recorre los resultados de la consulta
			while (rs.next()) {
				int id = rs.getInt("ID_LIBRO");
				String titulo = rs.getString("TITULO");
				String autor = rs.getString("AUTOR");
				String anio = rs.getString("AÑO");

				// Se crea el objeto Libro con los datos obtenidos
				Libro libro = new Libro(titulo, autor, anio);

				// Se guarda en el mapa usando el ID como clave
				libros.put(id, libro);
			}
		}

		return libros;
	}

	/**
	 * Inserta un nuevo libro en la base de datos.
	 * Valida que la fecha tenga formato correcto y no sea futura.
	 */
	public void insertarLibro(int id, String titulo, String autor, String anio) throws Exception {

		LocalDate fecha;

		// Validación del formato de fecha (YYYY-MM-DD)
		try {
			fecha = LocalDate.parse(anio);
		} catch (DateTimeParseException e) {
			throw new Excepcion("Invalid date, should be YYYY-MM-DD");
		}

		// Validación: la fecha no puede ser posterior a hoy
		if (fecha.isAfter(LocalDate.now())) {
			throw new Excepcion("the date couldn't be later that today");
		}

		// Inserción en base de datos
		try (Connection con = DriverManager.getConnection(urlBD, userBD, passwordBD);
				PreparedStatement stmt = con.prepareStatement(SQLINSERTARLIBRO)) {

			stmt.setInt(1, id);
			stmt.setString(2, titulo);
			stmt.setString(3, autor);

			// Conversión de LocalDate a java.sql.Date
			stmt.setDate(4, java.sql.Date.valueOf(fecha));

			stmt.executeUpdate(); // Ejecuta el INSERT
		}
	}

	/**
	 * Borra un libro de la base de datos.
	 * Primero elimina las relaciones en otra tabla (CONTIENE)
	 * para evitar errores de integridad referencial.
	 */
	public void borrarLibro(int id) throws Exception {

		try (Connection con = DriverManager.getConnection(urlBD, userBD, passwordBD)) {

			// Primero borrar relaciones (clave foránea)
			try (PreparedStatement stmt1 = con.prepareStatement(SQLBORRARRELACION)) {
				stmt1.setInt(1, id);
				stmt1.executeUpdate();
			}

			// Después borrar el libro
			try (PreparedStatement stmt2 = con.prepareStatement(SQLBORRARLIBRO)) {
				stmt2.setInt(1, id);
				stmt2.executeUpdate();
			}
		}
	}

	/**
	 * Comprueba si existe un libro con el ID dado.
	 * Devuelve true si existe, false en caso contrario.
	 */
	public boolean existeLibro(int id) throws Exception {

		final String Id_existe = "SELECT COUNT(*) FROM LIBRO WHERE ID_LIBRO = ?";

		try (Connection con = DriverManager.getConnection(urlBD, userBD, passwordBD);
				PreparedStatement stmt = con.prepareStatement(Id_existe)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					// Si el contador es mayor que 0, existe
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	/**
	 * Llama a un procedimiento almacenado que devuelve
	 * todos los libros ordenados.
	 */
	public Map<Integer, Libro> TodosLosLibrosOrdenados() throws Exception {

		// LinkedHashMap mantiene el orden de inserción (el que devuelve la BD)
		Map<Integer, Libro> libros = new java.util.LinkedHashMap<>();

		try (Connection con = DriverManager.getConnection(urlBD, userBD, passwordBD);
				CallableStatement cs = con.prepareCall("{CALL TodosLosLibrosOrdenados()}");
				ResultSet rs = cs.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("ID_LIBRO");
				String titulo = rs.getString("TITULO");
				String autor = rs.getString("AUTOR");
				String anio = rs.getString("AÑO");

				libros.put(id, new Libro(titulo, autor, anio));
			}
		}

		return libros;
	}
}