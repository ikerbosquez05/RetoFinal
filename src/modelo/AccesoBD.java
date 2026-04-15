package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class AccesoBD {

	private ResourceBundle configFile;
	private String urlBD;
	private String userBD;
	private String passwordBD;

	public AccesoBD() {

		this.configFile = ResourceBundle.getBundle("configClase");
		this.urlBD = this.configFile.getString("Conn");
		this.userBD = this.configFile.getString("DBUser");
		this.passwordBD = this.configFile.getString("DBPass");

	}

	public Connection conectar() throws Exception {

		return DriverManager.getConnection(urlBD, userBD, passwordBD);
	}
}