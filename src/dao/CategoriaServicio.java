package dao;

import interfaces.InterfazCategoria;
import interfaces.ExportarXML;
import clases.Categoria;
import java.io.FileWriter;
import java.util.List;

// Esta clase pertenece a la capa de SERVICIO, no a la BD directamente.
// Se encarga de la lógica de negocio relacionada con categorías.

public class CategoriaServicio implements ExportarXML {

	private InterfazCategoria dao = new CategoriaDAO();   // Se usa la interfaz InterfazCategoria para acceder al DAO real.
    // Esto permite cambiar la implementación sin modificar el servicio.


	 // Obtiene todas las categorías desde la base de datos.
    // El servicio solo delega la operación al DAO.
	public List<Categoria> obtenerCategorias() throws Exception {
		return dao.listarCategorias();
	}


	// Crea un objeto Categoria y lo envía al DAO para insertarlo en la BD.
    // Aquí podría haber validaciones adicionales si se quisieran añadir.
	public void agregarCategoria(int id, String tipo, String descripcion) throws Exception {
		Categoria c = new Categoria(id, tipo, descripcion);
		dao.insertarCategoria(c);
	}
	// Elimina una categoría por su ID llamando al DAO.
	public void eliminarCategoria(int id) throws Exception {
		dao.borrarCategoria(id);
	}

	// Implementación del método de la interfaz ExportarXML.
    // Genera un archivo XML con todas las categorías almacenadas en la BD.
	@Override
	public void generarXML() throws Exception {
		// 1. Obtener la lista de categorías desde la BD.
		List<Categoria> lista = dao.listarCategorias();

		// 2. Construir el contenido del archivo XML.
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<categorias>\n");

		// 3. Recorrer cada categoría y convertirla en etiquetas XML.
		for (Categoria c : lista) {
			sb.append("  <categoria>\n");
			sb.append("    <id>").append(c.getIdCategoria()).append("</id>\n");
			sb.append("    <tipo>").append(c.getTipoCategoria()).append("</tipo>\n");
			sb.append("    <descripcion>").append(c.getDescripcion()).append("</descripcion>\n");
			sb.append("  </categoria>\n");
		}

		sb.append("</categorias>");

		// 4. Guardar el XML en un archivo físico.
        // try-with-resources asegura que el archivo se cierre correctamente.
		try (FileWriter fw = new FileWriter("categorias.xml")) {
			fw.write(sb.toString());
		}
	}
}
