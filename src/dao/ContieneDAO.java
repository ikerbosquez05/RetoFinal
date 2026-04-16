package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;

import modelo.AccesoBD;

public class ContieneDAO {

    private AccesoBD conexion;

    public ContieneDAO() {
        conexion = new AccesoBD();
    }

    public void insertarDevolucion(int idPrestamo, int idLibro, String fecha) throws Exception {

        Connection con = conexion.conectar();

        PreparedStatement stmt = con.prepareStatement(
            "INSERT INTO CONTIENE (ID_PRESTAMO, ID_LIBRO, FECHA_DEVOLUCION) " +
            "VALUES (?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE FECHA_DEVOLUCION = VALUES(FECHA_DEVOLUCION)"
        );

        stmt.setInt(1, idPrestamo);
        stmt.setInt(2, idLibro);

        if (fecha == null || fecha.trim().isEmpty()) {
            stmt.setNull(3, Types.DATE);
        } else {
            stmt.setDate(3, Date.valueOf(fecha));
        }

        stmt.executeUpdate();

        con.close();
    }
}