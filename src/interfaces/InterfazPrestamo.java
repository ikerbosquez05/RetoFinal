package interfaces;

import java.util.Map;

public interface InterfazPrestamo {

    Map<Integer,String[]> verPrestamos() throws Exception;

    void insertarPrestamo(int idPrestamo, int idUsuario, int idLibro,
                          String fechaPrestamo, String fechaLimite, String fechaDevolucion) throws Exception;

    void borrarPrestamo(int idPrestamo) throws Exception;

    void modificarPrestamo(int idPrestamo, int idUsuario, int idLibro,
                           String fechaPrestamo, String fechaLimite, String fechaDevolucion) throws Exception;
}