package interfaces;

/**
 * Interfaz que define la funcionalidad de exportación de datos a formato XML.
 * <p>
 * Esta interfaz debe ser implementada por aquellas clases que necesiten generar
 * archivos XML a partir de los datos almacenados en la aplicación.
 * </p>
 *
 * Permite separar la lógica de exportación del resto del sistema, facilitando
 * la reutilización y el mantenimiento del código.
 *
 * @version 1.0
 */
public interface ExportarXML {

	/**
	 * Genera un archivo XML con los datos correspondientes.
	 * <p>
	 * La implementación concreta de este método dependerá de la clase que lo
	 * implemente, pudiendo exportar información como libros, usuarios, préstamos,
	 * etc.
	 * </p>
	 *
	 * @throws Exception si ocurre algún error durante la generación del XML.
	 */
	void generarXML() throws Exception;
}