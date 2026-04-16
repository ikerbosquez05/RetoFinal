package interfaces;

import java.util.List;
import clases.Categoria;

public interface InterfazCategoria {

	List<Categoria> listarCategorias() throws Exception;

	void insertarCategoria(Categoria c) throws Exception;

	void borrarCategoria(int id) throws Exception;
}