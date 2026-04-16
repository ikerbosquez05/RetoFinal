package dao;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

import interfaces.InterfazUsuario;
import modelo.AccesoBD;

public class UsuarioDAO implements InterfazUsuario {

	private AccesoBD conexion;

	public UsuarioDAO() {
		conexion = new AccesoBD();
	}

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

	@Override
	public void borrarUsuario(int id) throws Exception {

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("DELETE FROM USUARIO WHERE ID_USUARIO=?");

		stmt.setInt(1, id);

		stmt.executeUpdate();

		con.close();
	}

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