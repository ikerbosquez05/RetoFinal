package interfaces;

import java.util.Map;

public interface InterfazLibro {

	Map<Integer, String[]> verLibros() throws Exception;

	void insertarLibro(int id, String titulo, String autor, String fecha) throws Exception;

	void borrarLibro(int id) throws Exception;

	void modificarLibro(int id, String titulo, String autor, String fecha) throws Exception;

	Map<Integer, String[]> ordenarPorTitulo() throws Exception;

	boolean existeLibro(int id) throws Exception;
}