package ventanas;

import java.util.Map;

public interface LibrosITF {
    Map<Integer, Libro> verLibros() throws Exception;
    
    void insertarLibro(int id, String titulo, String autor, String anio) throws Exception;

    void borrarLibro(int id) throws Exception;
    
    boolean existeLibro(int id) throws Exception;
    
    Map<Integer, Libro> TodosLosLibrosOrdenados() throws Exception;
}