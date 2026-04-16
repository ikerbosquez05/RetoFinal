package dao;

import interfaces.InterfazCategoria;
import interfaces.ExportarXML;
import clases.Categoria;
import java.io.FileWriter;
import java.util.List;

public class CategoriaServicio implements ExportarXML {

	private InterfazCategoria dao = new CategoriaDAO();

	public List<Categoria> obtenerCategorias() throws Exception {
		return dao.listarCategorias();
	}

	public void agregarCategoria(int id, String tipo, String descripcion) throws Exception {
		Categoria c = new Categoria(id, tipo, descripcion);
		dao.insertarCategoria(c);
	}

	public void eliminarCategoria(int id) throws Exception {
		dao.borrarCategoria(id);
	}

	@Override
	public void generarXML() throws Exception {

		List<Categoria> lista = dao.listarCategorias();

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<categorias>\n");

		for (Categoria c : lista) {
			sb.append("  <categoria>\n");
			sb.append("    <id>").append(c.getIdCategoria()).append("</id>\n");
			sb.append("    <tipo>").append(c.getTipoCategoria()).append("</tipo>\n");
			sb.append("    <descripcion>").append(c.getDescripcion()).append("</descripcion>\n");
			sb.append("  </categoria>\n");
		}

		sb.append("</categorias>");

		try (FileWriter fw = new FileWriter("categorias.xml")) {
			fw.write(sb.toString());
		}
	}
}