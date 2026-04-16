package dao;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

import interfaces.InterfazPrestamo;
import modelo.AccesoBD;

public class PrestamoDAO implements InterfazPrestamo {

    private AccesoBD conexion;

    public PrestamoDAO() {
        conexion = new AccesoBD();
    }

    @Override
    public Map<Integer, String[]> verPrestamos() throws Exception {

        Map<Integer, String[]> prestamos = new TreeMap<>();

        Connection con = conexion.conectar();

        String sql = "SELECT P.ID_PRESTAMO, P.ID_USUARIO, C.ID_LIBRO, " +
                     "P.FECHA_PRESTAMO, P.FECHA_LIMITE, C.FECHA_DEVOLUCION " +
                     "FROM PRESTAMO P " +
                     "LEFT JOIN CONTIENE C ON P.ID_PRESTAMO = C.ID_PRESTAMO";

        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            int id = rs.getInt("ID_PRESTAMO");

            prestamos.put(id, new String[]{
                    String.valueOf(rs.getInt("ID_USUARIO")),
                    String.valueOf(rs.getInt("ID_LIBRO")),
                    String.valueOf(rs.getDate("FECHA_PRESTAMO")),
                    String.valueOf(rs.getDate("FECHA_LIMITE")),
                    String.valueOf(rs.getDate("FECHA_DEVOLUCION"))
            });
        }

        con.close();

        return prestamos;
    }

    @Override
    public void insertarPrestamo(int idPrestamo, int idUsuario, int idLibro,
                                 String fechaPrestamo, String fechaLimite, String fechaDevolucion) throws Exception {

        Connection con = conexion.conectar();
        con.setAutoCommit(false);

        try {

            String sqlPrestamo = "INSERT INTO PRESTAMO VALUES (?,?,?,?)";
            PreparedStatement ps1 = con.prepareStatement(sqlPrestamo);

            ps1.setInt(1, idPrestamo);
            ps1.setInt(2, idUsuario);
            ps1.setDate(3, Date.valueOf(fechaPrestamo));
            ps1.setDate(4, Date.valueOf(fechaLimite));
            ps1.executeUpdate();

            insertarDevolucion(con, idPrestamo, idLibro, fechaDevolucion);

            con.commit();

        } catch (Exception e) {
            con.rollback();
            throw e;
        }

        con.close();
    }

    @Override
    public void borrarPrestamo(int idPrestamo) throws Exception {

        Connection con = conexion.conectar();

        PreparedStatement ps = con.prepareStatement(
            "DELETE FROM PRESTAMO WHERE ID_PRESTAMO=?"
        );

        ps.setInt(1, idPrestamo);
        ps.executeUpdate();

        con.close();
    }

    @Override
    public void modificarPrestamo(int idPrestamo, int idUsuario, int idLibro,
                                  String fechaPrestamo, String fechaLimite, String fechaDevolucion) throws Exception {

        Connection con = conexion.conectar();
        con.setAutoCommit(false);

        try {

            PreparedStatement ps1 = con.prepareStatement(
                "UPDATE PRESTAMO SET ID_USUARIO=?, FECHA_PRESTAMO=?, FECHA_LIMITE=? WHERE ID_PRESTAMO=?"
            );

            ps1.setInt(1, idUsuario);
            ps1.setDate(2, Date.valueOf(fechaPrestamo));
            ps1.setDate(3, Date.valueOf(fechaLimite));
            ps1.setInt(4, idPrestamo);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(
                "UPDATE CONTIENE SET ID_LIBRO=?, FECHA_DEVOLUCION=? WHERE ID_PRESTAMO=?"
            );

            ps2.setInt(1, idLibro);

            if (fechaDevolucion == null || fechaDevolucion.isEmpty()) {
                ps2.setNull(2, Types.DATE);
            } else {
                ps2.setDate(2, Date.valueOf(fechaDevolucion));
            }

            ps2.setInt(3, idPrestamo);
            ps2.executeUpdate();

            con.commit();

        } catch (Exception e) {
            con.rollback();
            throw e;
        }

        con.close();
    }

    // lógica de contiene integrada
    private void insertarDevolucion(Connection con, int idPrestamo, int idLibro, String fecha) throws Exception {

        PreparedStatement stmt = con.prepareStatement(
            "INSERT INTO CONTIENE (ID_PRESTAMO, ID_LIBRO, FECHA_DEVOLUCION) " +
            "VALUES (?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE FECHA_DEVOLUCION = VALUES(FECHA_DEVOLUCION)"
        );

        stmt.setInt(1, idPrestamo);
        stmt.setInt(2, idLibro);

        if (fecha == null || fecha.isEmpty()) {
            stmt.setNull(3, Types.DATE);
        } else {
            stmt.setDate(3, Date.valueOf(fecha));
        }

        stmt.executeUpdate();
    }
}