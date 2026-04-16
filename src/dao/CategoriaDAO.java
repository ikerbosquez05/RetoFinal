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

    @Override
    public List<Categoria> listarCategorias() throws Exception {

        List<Categoria> lista = new ArrayList<>();

        Connection con = conexion.conectar();

        PreparedStatement stmt = con.prepareStatement(
            "SELECT * FROM CATEGORIA"
        );

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Categoria c = new Categoria(
                rs.getInt("ID_CATEGORIA"),
                rs.getString("TIPO_CATEGORIA"),
                rs.getString("DESCRIPCION")
            );

            lista.add(c);
        }

        con.close();

        return lista;
    }

    @Override
    public void insertarCategoria(Categoria c) throws Exception {

        Connection con = conexion.conectar();

        PreparedStatement stmt = con.prepareStatement(
            "INSERT INTO CATEGORIA VALUES (?,?,?)"
        );

        stmt.setInt(1, c.getIdCategoria());
        stmt.setString(2, c.getTipoCategoria().name());
        stmt.setString(3, c.getDescripcion());

        stmt.executeUpdate();

        con.close();
    }

    @Override
    public void borrarCategoria(int id) throws Exception {

        Connection con = conexion.conectar();

        PreparedStatement stmt = con.prepareStatement(
            "DELETE FROM CATEGORIA WHERE ID_CATEGORIA=?"
        );

        stmt.setInt(1, id);
        stmt.executeUpdate();

        con.close();
    }
}