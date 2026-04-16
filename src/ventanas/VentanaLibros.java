package ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import modelo.AccesoBD;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.border.LineBorder;

public class VentanaLibros extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	// Campos de entrada de datos del libro
	private JTextField txtId, txtTitulo, txtAutor, txtAnio;

	// Botones de acciones principales
	private JButton btnAgregar, btnEliminar, btnVolver, btnOrdenarPorTitulo;

	// Tabla para mostrar libros
	private JTable tabla;
	private DefaultTableModel modelo;

	public VentanaLibros() {

		// Configuración básica de la ventana
		setTitle("Gestión de Libros");
		setSize(600, 450);
		setLocationRelativeTo(null);

		// Panel principal (fondo)
		JPanel fondo = new JPanel();
		fondo.setLayout(null);
		fondo.setBackground(new Color(237, 230, 208));
		setContentPane(fondo);

		// Panel tipo "card" donde está la interfaz
		JPanel card = new JPanel();
		card.setLayout(null);
		card.setBounds(50, 40, 500, 340);
		card.setBackground(new Color(244, 239, 230));
		card.setBorder(new LineBorder(null));

		fondo.add(card);

		// Título de la ventana
		JLabel titulo = new JLabel("Book management");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
		titulo.setBounds(170, 10, 200, 30);
		card.add(titulo);

		// Campo ID
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(30, 60, 80, 20);
		card.add(lblId);

		txtId = new JTextField();
		txtId.setBounds(90, 60, 100, 25);
		card.add(txtId);

		// Campo título
		JLabel lblTitulo = new JLabel("Title");
		lblTitulo.setBounds(210, 60, 80, 20);
		card.add(lblTitulo);

		txtTitulo = new JTextField();
		txtTitulo.setBounds(260, 60, 180, 25);
		card.add(txtTitulo);

		// Campo autor
		JLabel lblAutor = new JLabel("Author");
		lblAutor.setBounds(30, 95, 80, 20);
		card.add(lblAutor);

		txtAutor = new JTextField();
		txtAutor.setBounds(90, 95, 180, 25);
		card.add(txtAutor);

		// Campo fecha/año
		JLabel lblAnio = new JLabel("Date (YYYY-MM-DD)");
		lblAnio.setBounds(280, 95, 150, 20);
		card.add(lblAnio);

		txtAnio = new JTextField();
		txtAnio.setBounds(400, 95, 80, 25);
		card.add(txtAnio);

		// Botón agregar libro (creado con método reutilizable)
		btnAgregar = crearBoton("Add Book", 140);

		// Botón eliminar libro seleccionado
		btnEliminar = new JButton("Delete");
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFocusPainted(false);
		btnEliminar.setBorderPainted(false);
		btnEliminar.setBackground(new Color(139, 111, 71));
		btnEliminar.setBounds(257, 153, 200, 30);
		btnEliminar.addActionListener(this);

		// Botón ordenar libros por título
		btnOrdenarPorTitulo = new JButton("Order by title");
		btnOrdenarPorTitulo.setForeground(Color.WHITE);
		btnOrdenarPorTitulo.setFocusPainted(false);
		btnOrdenarPorTitulo.setBorderPainted(false);
		btnOrdenarPorTitulo.setBackground(new Color(139, 111, 71));
		btnOrdenarPorTitulo.setBounds(47, 206, 200, 30);
		btnOrdenarPorTitulo.addActionListener(this);

		// Botón volver a ventana anterior
		btnVolver = new JButton("Go back");
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFocusPainted(false);
		btnVolver.setBorderPainted(false);
		btnVolver.setBackground(new Color(139, 111, 71));
		btnVolver.setBounds(260, 206, 200, 30);
		btnVolver.addActionListener(this);

		// Añadir botones al panel
		card.add(btnAgregar);
		card.add(btnEliminar);
		card.add(btnVolver);
		card.add(btnOrdenarPorTitulo);

		// Modelo de tabla (estructura de columnas)
		modelo = new DefaultTableModel(new String[] { "ID", "Title", "Author", "Date" }, 0);

		// JTable donde se muestran los datos
		tabla = new JTable(modelo);

		// Scroll para la tabla
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(30, 260, 440, 70);

		card.add(scroll);

		// Carga inicial de libros desde la BD
		cargarLibros();
	}

	// Método auxiliar para crear botones con estilo común
	private JButton crearBoton(String texto, int y) {

		JButton b = new JButton(texto);

		b.setBounds(47, 153, 200, 30);
		b.setBackground(new Color(139, 111, 71));
		b.setForeground(Color.WHITE);

		b.setFocusPainted(false);
		b.setBorderPainted(false);

		b.addActionListener(this);

		return b;
	}

	// Carga todos los libros desde la base de datos y los muestra en la tabla
	private void cargarLibros() {

		try {

			// Limpiar tabla antes de recargar
			modelo.setRowCount(0);

			// Conexión con capa de datos
			LibrosITF bd = new AccesoBD();

			// Obtener libros desde BD
			Map<Integer, Libro> libros = bd.verLibros();

			// Volcar datos en la tabla
			for (Map.Entry<Integer, Libro> entry : libros.entrySet()) {

				int id = entry.getKey();
				Libro libro = entry.getValue();

				modelo.addRow(new Object[] {
					id,
					libro.getTitulo(),
					libro.getAutor(),
					libro.getAnio()
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Añadir un libro a la base de datos
	private void agregarLibro() {

		try {
			// Validación de campos vacíos
			if (txtId.getText().isEmpty() || txtTitulo.getText().isEmpty()
					|| txtAutor.getText().isEmpty() || txtAnio.getText().isEmpty()) {

				JOptionPane.showMessageDialog(this, "Fill out all fields");
				return;
			}

			int id;

			// Validación de ID numérico
			try {
				id = Integer.parseInt(txtId.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "The ID have to be a whole number");
				return;
			}

			AccesoBD bd = new AccesoBD();

			// Comprobar si el ID ya existe
			if (bd.existeLibro(id)) {
				JOptionPane.showMessageDialog(this, "Error: ID repeat");
				return;
			}

			// Insertar libro en BD
			try {
				bd.insertarLibro(id, txtTitulo.getText(), txtAutor.getText(), txtAnio.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				return;
			}

			// Recargar tabla y limpiar campos
			cargarLibros();
			limpiarCampos();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Eliminar libro seleccionado en la tabla
	private void eliminarLibro() {

		try {

			int fila = tabla.getSelectedRow();

			// Validar selección
			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Select a book");
				return;
			}

			// Obtener ID del libro seleccionado
			int id = (int) tabla.getValueAt(fila, 0);

			AccesoBD bd = new AccesoBD();
			bd.borrarLibro(id);

			// Recargar tabla
			cargarLibros();
			limpiarCampos();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Cerrar ventana actual
	private void volver() {
		this.dispose();
	}

	// Control de eventos de botones
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAgregar)
			agregarLibro();

		if (e.getSource() == btnEliminar)
			eliminarLibro();

		if (e.getSource() == btnVolver)
			volver();

		if (e.getSource() == btnOrdenarPorTitulo)
			TodosLosLibrosOrdenados1();
	}

	// Limpiar campos del formulario
	private void limpiarCampos() {
		txtId.setText("");
		txtTitulo.setText("");
		txtAutor.setText("");
		txtAnio.setText("");
	}

	// Mostrar libros ordenados por título
	private void TodosLosLibrosOrdenados1() {

		try {

			modelo.setRowCount(0);

			AccesoBD bd = new AccesoBD();
			Map<Integer, Libro> libros = bd.TodosLosLibrosOrdenados();

			for (Map.Entry<Integer, Libro> entry : libros.entrySet()) {

				Libro libro = entry.getValue();

				modelo.addRow(new Object[] {
					entry.getKey(),
					libro.getTitulo(),
					libro.getAutor(),
					libro.getAnio()
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}