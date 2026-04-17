package interfaces;

import java.util.List;
import clases.Categoria;

public interface InterfazCategoria {

	List<Categoria> listarCategorias() throws Exception;
	// Método para obtener todas las categorías desde la base de datos.
    // Devuelve una lista de objetos Categoria.

	void insertarCategoria(Categoria c) throws Exception;
	// Método para insertar una nueva categoría en la base de datos.
    // Recibe un objeto Categoria como parámetro.
	
	void borrarCategoria(int id) throws Exception;
	// Método para eliminar una categoría por su ID.
    // No devuelve nada, solo ejecuta la eliminación.
}
