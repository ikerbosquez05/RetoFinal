package dao;

import modelo.AccesoBD;
import interfaces.InterfazLibro;
import java.sql.*;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.TreeMap;

public class LibroDAO implements InterfazLibro {

	private AccesoBD conexion;

	public LibroDAO() {
		conexion = new AccesoBD();
	}

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

	@Override
	public void insertarLibro(int id, String titulo, String autor, String año) throws Exception {

		try {
		} catch (DateTimeParseException e) {
			throw new Exception("Fecha inválida (YYYY-MM-DD)");
		}

		Connection con = conexion.conectar();

		PreparedStatement stmt = con
				.prepareStatement("INSERT INTO LIBRO (ID_LIBRO, TITULO, AUTOR, AÑO) VALUES (?,?,?,?)");

		stmt.setInt(1, id);
		stmt.setString(2, titulo);
		stmt.setString(3, autor);
		stmt.setString(4, año); // 🔥 AQUÍ

		stmt.executeUpdate();

		con.close();
	}

	@Override
	public void borrarLibro(int id) throws Exception {

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("DELETE FROM LIBRO WHERE ID_LIBRO=?");

		stmt.setInt(1, id);
		stmt.executeUpdate();

		con.close();
	}

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

	@Override
	public Map<Integer, String[]> ordenarPorTitulo() throws Exception {

		Map<Integer, String[]> libros = new TreeMap<>();

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("SELECT ID_LIBRO, TITULO, AUTOR, AÑO FROM LIBRO ORDER BY TITULO");

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			int id = rs.getInt("ID_LIBRO");

			libros.put(id, new String[] { rs.getString("TITULO"), rs.getString("AUTOR"), rs.getString("AÑO") // 🔥 AQUÍ
			});
		}

		con.close();

		return libros;
	}

	public boolean existeLibro(int id) throws Exception {

		final String Id_existe = "SELECT COUNT(*) FROM LIBRO WHERE ID_LIBRO = ?";

		AccesoBD bd = new AccesoBD();

		try (Connection con = bd.conectar(); PreparedStatement stmt = con.prepareStatement(Id_existe)) {

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