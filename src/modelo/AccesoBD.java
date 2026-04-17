package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

/**
 * Clase encargada de gestionar la conexión con la base de datos.
 * <p>
 * Esta clase obtiene los parámetros de conexión (URL, usuario y contraseña)
 * desde un archivo de configuración externo mediante {@link ResourceBundle}.
 * Proporciona un método para establecer una conexión con la base de datos
 * utilizando {@link DriverManager}.
 * </p>
 *
 * El archivo de configuración esperado es {@code configClase.properties} y debe
 * contener las siguientes claves:
 * <ul>
 * <li>{@code Conn} – URL de conexión a la base de datos.</li>
 * <li>{@code DBUser} – Usuario de la base de datos.</li>
 * <li>{@code DBPass} – Contraseña de la base de datos.</li>
 * </ul>
 *
 * @version 1.0
 * @see Connection
 * @see DriverManager
 * @see ResourceBundle
 */
public class AccesoBD {

	/** Archivo de configuración que contiene los parámetros de conexión. */
	private ResourceBundle configFile;

	/** URL de conexión a la base de datos. */
	private String urlBD;

	/** Usuario de la base de datos. */
	private String userBD;

	/** Contraseña de la base de datos. */
	private String passwordBD;

	/**
	 * Constructor de la clase AccesoBD.
	 * <p>
	 * Carga el archivo de configuración {@code configClase.properties} y extrae los
	 * valores necesarios para la conexión a la base de datos: URL, usuario y
	 * contraseña.
	 * </p>
	 */
	public AccesoBD() {

		// Carga el archivo de configuración
		this.configFile = ResourceBundle.getBundle("configClase");

		// Obtiene los parámetros de conexión
		this.urlBD = this.configFile.getString("Conn");
		this.userBD = this.configFile.getString("DBUser");
		this.passwordBD = this.configFile.getString("DBPass");
	}

	/**
	 * Establece una conexión con la base de datos.
	 * <p>
	 * Utiliza {@link DriverManager#getConnection(String, String, String)} con los
	 * parámetros previamente cargados desde el archivo de configuración.
	 * </p>
	 *
	 * @return un objeto {@link Connection} activo hacia la base de datos.
	 * @throws Exception si ocurre un error durante la conexión.
	 */
	public Connection conectar() throws Exception {

		return DriverManager.getConnection(urlBD, userBD, passwordBD);
	}
}