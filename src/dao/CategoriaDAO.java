package dao;

import modelo.AccesoBD;
import clases.Categoria;
import interfaces.InterfazCategoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements InterfazCategoria {

    private AccesoBD conexion;

    public CategoriaDAO() {
        conexion = new AccesoBD();
    }
// Se crea una lista vacía donde se guardarán las categorías obtenidas de la BD.
    @Override
    public List<Categoria> listarCategorias() throws Exception {

        List<Categoria> lista = new ArrayList<>();

        Connection con = conexion.conectar(); // Se abre la conexión a la base de datos.

        PreparedStatement stmt = con.prepareStatement(
            "SELECT * FROM CATEGORIA"
        );  // Se prepara la consulta SQL para obtener todas las categorías.

        ResultSet rs = stmt.executeQuery();  // Se ejecuta la consulta y se obtiene un ResultSet con los resultados.

        while (rs.next()) {

            Categoria c = new Categoria(
                rs.getInt("ID_CATEGORIA"),
                rs.getString("TIPO_CATEGORIA"),
                rs.getString("DESCRIPCION")
            );

            lista.add(c);
            // Se recorre cada fila del resultado y se crea un objeto Categoria con los datos.
           // Cada categoría se añade a la lista.
        }

        con.close();// Se cierra la conexión a la BD.

        return lista; // Se devuelve la lista completa de categorías.
    }

    @Override
    public void insertarCategoria(Categoria c) throws Exception { // Se abre la conexión a la BD.

        Connection con = conexion.conectar();

        PreparedStatement stmt = con.prepareStatement(
            "INSERT INTO CATEGORIA VALUES (?,?,?)"
        ); // Se abre la conexión a la BD.

        stmt.setInt(1, c.getIdCategoria());
        stmt.setString(2, c.getTipoCategoria().name());
        stmt.setString(3, c.getDescripcion());
        // Se asignan los valores a la sentencia SQL.
        // .name() convierte el enum TipoCategoria a texto.
        
        stmt.executeUpdate(); // Se ejecuta la inserción en la base de datos.

        con.close(); // Se cierra la conexión.
    }

    @Override
    public void borrarCategoria(int id) throws Exception { 

        Connection con = conexion.conectar();  // Se abre la conexión.

        PreparedStatement stmt = con.prepareStatement(
            "DELETE FROM CATEGORIA WHERE ID_CATEGORIA=?"
        ); // Se prepara la sentencia SQL para borrar una categoría por su ID.

        stmt.setInt(1, id);    // Se asigna el ID recibido al parámetro de la consulta.
        stmt.executeUpdate();  // Se ejecuta la eliminación.

        con.close(); // Se cierra la conexión.
    }
}
