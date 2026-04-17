package dao;

import modelo.AccesoBD;
import clases.Categoria;
import interfaces.InterfazCategoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para la gestión de categorías.
 * <p>
 * Implementa la interfaz {@link InterfazCategoria} y proporciona métodos para
 * realizar operaciones sobre la tabla CATEGORIA en la base de datos.
 * </p>
 *
 * Permite consultar, insertar y eliminar categorías, representadas mediante
 * objetos de la clase {@link Categoria}.
 *
 * @version 1.0
 * @see InterfazCategoria
 * @see Categoria
 * @see AccesoBD
 */
public class CategoriaDAO implements InterfazCategoria {

	/** Objeto encargado de gestionar la conexión a la base de datos. */
	private AccesoBD conexion;

	/**
	 * Constructor de CategoriaDAO.
	 * <p>
	 * Inicializa el objeto de conexión a la base de datos.
	 * </p>
	 */
	public CategoriaDAO() {
		conexion = new AccesoBD();
	}

	/**
	 * Obtiene todas las categorías almacenadas en la base de datos.
	 * <p>
	 * Ejecuta una consulta sobre la tabla CATEGORIA y construye una lista de
	 * objetos {@link Categoria} con los datos obtenidos.
	 * </p>
	 *
	 * @return una {@link List} de categorías.
	 * @throws Exception si ocurre un error durante la consulta.
	 */
	@Override
	public List<Categoria> listarCategorias() throws Exception {

		List<Categoria> lista = new ArrayList<>();

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM CATEGORIA");

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			Categoria c = new Categoria(rs.getInt("ID_CATEGORIA"), rs.getString("TIPO_CATEGORIA"),
					rs.getString("DESCRIPCION"));

			lista.add(c);
		}

		con.close();

		return lista;
	}

	/**
	 * Inserta una nueva categoría en la base de datos.
	 * <p>
	 * Ejecuta una sentencia SQL INSERT utilizando los datos del objeto
	 * {@link Categoria}.
	 * </p>
	 *
	 * <p>
	 * El tipo de categoría se guarda como texto mediante el método {@code name()}
	 * del ENUM correspondiente.
	 * </p>
	 *
	 * @param c objeto categoría a insertar.
	 * @throws Exception si ocurre un error durante la inserción.
	 */
	@Override
	public void insertarCategoria(Categoria c) throws Exception {

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("INSERT INTO CATEGORIA VALUES (?,?,?)");

		stmt.setInt(1, c.getIdCategoria());
		stmt.setString(2, c.getTipoCategoria().name());
		stmt.setString(3, c.getDescripcion());

		stmt.executeUpdate();

		con.close();
	}

	/**
	 * Elimina una categoría de la base de datos.
	 * <p>
	 * Ejecuta una sentencia SQL DELETE utilizando el identificador de la categoría.
	 * </p>
	 *
	 * @param id identificador de la categoría a eliminar.
	 * @throws Exception si ocurre un error durante la eliminación.
	 */
	@Override
	public void borrarCategoria(int id) throws Exception {

		Connection con = conexion.conectar();

		PreparedStatement stmt = con.prepareStatement("DELETE FROM CATEGORIA WHERE ID_CATEGORIA=?");

		stmt.setInt(1, id);
		stmt.executeUpdate();

		con.close();
	}
}